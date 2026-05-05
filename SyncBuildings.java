# yaml-language-server: $schema=https://json.schemastore.org/github-workflow.json

name: Instamod Build and Deploy
on:
  workflow_call:
    inputs:
      application_name:
        description: "Name for your application -- this will be represented throughout the deployment."
        type: string
        required: true
      namespace:
        description: "Kubernetes namespace to deploy into. Only add this if it differs from the product name."
        type: string
        required: false
        default: "${{ github.event.repository.custom_properties.appcat-product }}"
      product:
        description: "Product to deploy to."
        type: string
        required: false
        default: "${{ github.event.repository.custom_properties.appcat-product }}"
      values-files:
        description: "The path to the values file(s) to use for the helm deployment, newline delimited."
        type: string
        required: false
        default: .github/app-manifests/values.yaml
      dockerfile-path:
        description: "Path to the Dockerfile to build"
        type: string
        required: false
        default: .github/Dockerfile
      operation:
        description: "The operation to perform: build, deploy, rollback, undeploy."
        type: string
        required: false
        default: "build"
      environment:
        description: "The environment that this targets -- e.g. sbx, dev, tst, qa, prod, etc."
        type: string
        required: false
        default: ""
      rollback_commit:
        description: "Commit SHA to rollback to (only used when operation is 'rollback')"
        required: false
        type: string
        default: ""
      eks-cluster:
        description: "The EKS cluster to deploy to (instamod, diamanti, inframod)."
        type: string
        required: false
        default: "instamod"
      docker-build-runner:
        description: "The runner set to use for the docker build step (only change if default fails)"
        type: string
        required: false
        default: ${{ vars.RUNNERSET_DEFAULT }}

jobs:
  determine-environment:
    runs-on: ${{ vars.RUNNERSET_DEFAULT }}
    outputs:
      environment: ${{ steps.set-env.outputs.environment }}
      branch: ${{ steps.set-env.outputs.branch }}
      operation: ${{ steps.set-env.outputs.operation }}
    steps:
      - name: Determine environment and branch
        id: set-env
        env:
          OPERATION: ${{ inputs.operation }}
          BRANCH: ${{ github.ref_name }}
          ENVIRONMENT: ${{ inputs.environment }}
        run: |
          # Determine environment from branch if not provided
          if [ -z "$ENVIRONMENT" ]; then
            case "$BRANCH" in
              develop)
                ENVIRONMENT="dev"
                ;;
              release)
                ENVIRONMENT="qa"
                ;;
              master)
                ENVIRONMENT="prod"
                ;;
              *)
                echo "Error: Unable to determine environment from branch '$BRANCH'"
                exit 1
                ;;
            esac
          fi

          echo "environment=$ENVIRONMENT" >> $GITHUB_OUTPUT
          echo "branch=$BRANCH" >> $GITHUB_OUTPUT
          echo "operation=$OPERATION" >> $GITHUB_OUTPUT

          echo "Environment: $ENVIRONMENT"
          echo "Branch: $BRANCH"
          echo "Operation: $OPERATION"
  nexusiq-scan:
    needs: determine-environment
    if: ${{ needs.determine-environment.outputs.operation == 'build' || needs.determine-environment.outputs.operation == 'deploy' }}
    runs-on: ${{ vars.RUNNERSET_DEFAULT }}
    permissions:
      actions: read
      contents: read
      id-token: write
      security-events: write
    steps:
      - uses: actions/checkout@v6
        with:
          ref: ${{ github.event_name == 'pull_request' && github.event.pull_request.head.sha || needs.determine-environment.outputs.branch }}
      - uses: dukeenergy-corp/duke-actions/composite/nexusiq-scan@master
        with:
          environment: "${{ needs.determine-environment.outputs.environment }}"
  docker-build:
    needs: [determine-environment, nexusiq-scan]
    if: ${{ needs.determine-environment.outputs.operation == 'build' || needs.determine-environment.outputs.operation == 'deploy' }}
    runs-on: ${{ inputs.docker-build-runner }}
    outputs:
      image-repository: ${{ steps.parse.outputs.repository }}
      image-tag: ${{ steps.parse.outputs.tag }}
    steps:
      - uses: actions/checkout@v6
        with:
          ref: ${{ github.event_name == 'pull_request' && github.event.pull_request.head.sha || needs.determine-environment.outputs.branch }}
      - id: build
        uses: dukeenergy-corp/duke-actions/composite/docker-build@master
        with:
          file: ${{ inputs.dockerfile-path }}
          push: ${{ github.event_name == 'pull_request' && 'false' || 'true' }}
          build-args: |
            PRODUCT_NAME=${{ inputs.product || github.event.repository.custom_properties.appcat-product }}
            APPLICATION_NAME=${{ inputs.application_name || github.event.repository.name }}
            APPLICATION_BUILD_ENV=${{ needs.determine-environment.outputs.environment }}
      - name: Parse image repository and tag
        id: parse
        run: |
          IMAGE_FULL=$(echo "${DOCKER_METADATA_OUTPUT_JSON}" | jq -r '.tags[0]')
          IMAGE_REPO=$(cut -d':' -f1 <<<"$IMAGE_FULL")
          IMAGE_TAG=$(cut -d':' -f2 <<<"$IMAGE_FULL")

          echo "repository=$IMAGE_REPO" >> $GITHUB_OUTPUT
          echo "tag=$IMAGE_TAG" >> $GITHUB_OUTPUT

          echo "Parsed repository: $IMAGE_REPO"
          echo "Parsed tag: $IMAGE_TAG"
  deploy:
    needs: [determine-environment, docker-build]
    if: ${{ needs.determine-environment.outputs.operation == 'deploy' }}
    environment: ${{ needs.determine-environment.outputs.environment }}
    runs-on: ${{ vars.RUNNERSET_DEFAULT }}
    permissions:
      actions: read
      contents: read
      id-token: write
      security-events: write
    steps:
      - uses: actions/checkout@v6
        with:
          ref: ${{ needs.determine-environment.outputs.branch }}
      - uses: dukeenergy-corp/duke-actions/composite/vault-login@master
      - name: Initialize Config Server
        id: config-server-init
        shell: bash
        env:
          VALUES_FILES: ${{ inputs.values-files }}
          PRODUCT: ${{ inputs.product }}
          APPLICATION_NAME: ${{ inputs.application_name }}
          ENVIRONMENT: ${{ needs.determine-environment.outputs.environment }}
        run: |
          function get_token() {
            vault read "products/data/${PRODUCT}/instamod/${ENVIRONMENT}/${APPLICATION_NAME}/vault-app-token" 2> /dev/null || echo ""
          }

          # check if they've enabled config-server.
          NEEDS_CONFIG_SERVER=false
          while read -r FILE; do
            [[ -z "$FILE" ]] && continue
            if yq -e '.config-server-compat.enabled == true' "$FILE" > /dev/null 2>&1; then
              NEEDS_CONFIG_SERVER=true
              break
            fi
          done <<< "$VALUES_FILES"

          echo "needs_config_server=$NEEDS_CONFIG_SERVER" >> $GITHUB_OUTPUT

          if [ "$NEEDS_CONFIG_SERVER" = false ]; then
            echo "Config Server not enabled in any values file; skipping initialization."
            exit 0
          fi

          echo "Config Server enabled; checking Vault token."
          EXISTING_TOKEN=$(get_token)
          if [ -z "$EXISTING_TOKEN" ] || [ "$EXISTING_TOKEN" == "generate-me" ]; then
            echo "No valid Vault token found; generating placeholder value."
            vault write "products/data/${PRODUCT}/instamod/${ENVIRONMENT}/${APPLICATION_NAME}/vault-app-token" value="generate-me"
          else
            echo "Valid Vault token already exists; no action needed."
            exit 0
          fi

          # check every 10 seconds for up to 10 minutes for the token to be replaced.
          echo "Waiting for Vault token to be replaced..."
          for i in {1..60}; do
            sleep 10
            CURRENT_TOKEN=$(get_token)
            if [ -n "$CURRENT_TOKEN" ] && [ "$CURRENT_TOKEN" != "generate-me" ]; then
              echo "Vault token has been replaced."
              exit 0
            fi
            echo "Vault token not replaced yet; checking again..."
          done
      - name: Fetch vault app token
        if: ${{ steps.config-server-init.outputs.needs_config_server == 'true' }}
        uses: dukeenergy-corp/duke-actions/composite/vault-fetch@master
        with:
          secrets: |
            ${{ inputs.product }}/instamod/${{ needs.determine-environment.outputs.environment }}/${{ inputs.application_name }}/vault-app-token value | VAULT_TOKEN;
      - name: Fetch k8s credentials
        uses: dukeenergy-corp/duke-actions/composite/vault-fetch@master
        with:
          secrets: |
            github-global/data/eks-credentials/${{ needs.determine-environment.outputs.environment }}/${{ inputs.eks-cluster }} kube_config | KUBE_CONFIG;
            github-global/data/eks-credentials/${{ needs.determine-environment.outputs.environment }}/${{ inputs.eks-cluster }} ingress_class | INGRESS_CLASS;
            github-global/data/eks-credentials/${{ needs.determine-environment.outputs.environment }}/${{ inputs.eks-cluster }} ingress_base_domain | INGRESS_BASE_DOMAIN;
            github-global/data/eks-credentials/${{ needs.determine-environment.outputs.environment }}/${{ inputs.eks-cluster }} vault_server | VAULT_SERVER;
      - name: Run helm-deploy composite
        uses: dukeenergy-corp/duke-actions/composite/helm-deploy@master
        with:
          kube-config: ${{ env.KUBE_CONFIG }}
          helm-repo-url: "https://nexus.ci.duke-energy.app/repository/duke-cne-helm-local/"
          release-name: "${{ needs.determine-environment.outputs.environment }}-${{ inputs.application_name || github.event.repository.name }}"
          chart-name: "pcf-app-compat"
          atomic: "false"
          namespace: ${{ inputs.namespace }}
          values-files: ${{ inputs.values-files }}
          vars: |
            global.applicationName=${{ inputs.application_name || github.event.repository.name }}
            global.applicationDeployEnv=${{ needs.determine-environment.outputs.environment }}
            global.productName=${{ inputs.product || github.event.repository.custom_properties.appcat-product }}
            global.ingress.className=${{ env.INGRESS_CLASS }}
            global.ingress.baseDomain=${{ env.INGRESS_BASE_DOMAIN }}
            image.repository=${{ needs.docker-build.outputs.image-repository }}
            image.tag=${{ needs.docker-build.outputs.image-tag }}
            config-server-compat.vault.server=${{ env.VAULT_SERVER }}
            config-server-compat.vault.token=${{ env.VAULT_TOKEN }}

  rollback:
    needs: determine-environment
    if: ${{ needs.determine-environment.outputs.operation == 'rollback' }}
    environment: ${{ needs.determine-environment.outputs.environment }}
    runs-on: ${{ vars.RUNNERSET_DEFAULT }}
    permissions:
      actions: read
      contents: read
      id-token: write
      security-events: write
    env:
      ROLLBACK_COMMIT: ${{ inputs.rollback_commit }}
    steps:
      - uses: actions/checkout@v6
        with:
          ref: ${{ needs.determine-environment.outputs.branch }}
      - name: fetch eks-credentials
        uses: dukeenergy-corp/duke-actions/composite/vault-fetch@master
        with:
          secrets: |
            github-global/data/eks-credentials/${{ needs.determine-environment.outputs.environment }}/${{ inputs.eks-cluster }} kube_config | KUBE_CONFIG;
      - uses: dukeenergy-corp/duke-actions/composite/helm-rollback@master
        with:
          kube-config: ${{ env.KUBE_CONFIG }}
          release-name: "${{ needs.determine-environment.outputs.environment }}-${{ inputs.application_name || github.event.repository.name }}"
          namespace: ${{ inputs.namespace }}
          rollback_commit: ${{ env.ROLLBACK_COMMIT }}

  undeploy:
    needs: determine-environment
    if: ${{ needs.determine-environment.outputs.operation == 'undeploy' }}
    environment: ${{ needs.determine-environment.outputs.environment }}
    runs-on: ${{ vars.RUNNERSET_DEFAULT }}
    permissions:
      actions: read
      contents: read
      id-token: write
      security-events: write
    steps:
      - uses: actions/checkout@v6
        with:
          ref: ${{ needs.determine-environment.outputs.branch }}
      - name: fetch eks-credentials
        uses: dukeenergy-corp/duke-actions/composite/vault-fetch@master
        with:
          secrets: |
            github-global/data/eks-credentials/${{ needs.determine-environment.outputs.environment }}/${{ inputs.eks-cluster }} kube_config | KUBE_CONFIG;
      - uses: dukeenergy-corp/duke-actions/composite/helm-undeploy@master
        with:
          kube-config: ${{ env.KUBE_CONFIG }}
          release-name: "${{ needs.determine-environment.outputs.environment }}-${{ inputs.application_name || github.event.repository.name }}"
          namespace: ${{ inputs.namespace }}
