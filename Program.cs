eks-inframod-params: &eks-inframod-params
  PRODUCT_NAME: ((product-name))
  APPLICATION_NAME: ((application-name))
  APPLICATION_BUILD_ENV: ((application-build-env))
  APPLICATION_DEPLOY_ENV: ((application-deploy-env))
  MANIFEST: ((application-deploy-manifest))
  BRANCH: ((application-build-branch))
  CODE_SCAN_USERNAME: ((checkmarx-scan-username))
  CODE_SCAN_PASSWORD: ((checkmarx-scan-password))
  VAULT_ADDR: ((vault-url-cc))
  VAULT_TOKEN: ((vault-token-cc-rw))
  EKS_CREDENTIALS: ((eks-credentials))
  NEW_VAULT_ADDR: ((vault-url))

docker-build-args: &docker-build-args
  BUILD_ARG_PRODUCT_NAME: ((product-name))
  BUILD_ARG_APPLICATION_NAME: ((application-name))
  BUILD_ARG_APPLICATION_BUILD_ENV: ((application-build-env))

resources:
  - name: ((application-build-branch))
    type: git
    icon: git
    source:
      uri: ((git-uri))
      branch: ((application-build-branch))
      skip_ssl_verification: true
      username: ((github-readonly-username))
      password: ((github-readonly-password))
  - name: nexus
    type: registry-image
    icon: docker
    check_every: never
    source:
      repository: nexus-docker-cne-local.ci.duke-energy.app/duke/((product-name))/((application-name))
      username: ((nexus-write-username))
      password: ((nexus-write-password))
  - name: compliance-trigger
    type: time
    icon: clock-outline
    source:
      interval: 720h
  - name: repo-task-app-scan
    type: git
    icon: cloud-lock
    check_every: 30m
    source:
      uri: ((repo-task-app-scan-uri))
      branch: ((repo-task-app-scan-version))
      skip_ssl_verification: true
  - name: repo-task-eks-inframod
    type: git
    icon: cloud-lock
    check_every: 30m
    source:
      uri: ((repo-task-app-eks-inframod-uri))
      branch: ((repo-task-app-eks-inframod-version))
      skip_ssl_verification: true
  - name: build-image
    type: registry-image
    check_every: 10m
    icon: docker
    source:
      repository: ((docker-base-image-host))/((docker-base-image-path))
      tag: ((docker-base-image-tag))
  - name: build-artifacts
    type: s3
    icon: nas
    source:
      endpoint: ((s3-onprem-endpoint))
      disable_ssl: true
      skip_ssl_verification: true
      bucket: ((s3-onprem-bucket))
      regexp: ((product-name))/((application-name))/((application-build-env))/(.*).zip
      access_key_id: ((s3-onprem-access-key))
      secret_access_key: ((s3-onprem-access-secret))

jobs:
  - name: job-update-pipeline
    public: false
    plan:
      - get: ((application-build-branch))
        params: { depth: 1 }
        trigger: true
      - set_pipeline: self
        file: ((application-build-branch))/ci/pipeline.yml
        vars: { env-config: ((env-config)) }
        var_files:
          - ((application-build-branch))/ci/config/parameters-common.yml
          - ((application-build-branch))/ci/config/parameters-((env-config)).yml

  - name: job-source-scan
    public: false
    serial: true
    plan:
      - in_parallel:
          fail_fast: true
          steps:
            - get: build-image
            - get: compliance-trigger
              trigger: true
            - get: ((application-build-branch))
              params: { depth: 1 }
              trigger: true
              passed: [job-update-pipeline]
            - get: repo-task-app-scan
              params: { depth: 1 }
      - task: task-app-scan
        timeout: 1h
        image: build-image
        input_mapping:
          source-repo: ((application-build-branch))
          repo-task-app-scan: repo-task-app-scan
        config:
          platform: linux
          inputs: [{ name: source-repo }, { name: repo-task-app-scan }]
          outputs: [{ name: artifacts }]
          params:
            application_name: ((application-name))
            build_env: ((application-build-env))
            build_type: ((app-build-type))
            branch: ((application-build-branch))
            code_scan_token: ((sonarqube-scan-token))
            code_scan_user: ((checkmarx-scan-username))
            code_scan_pass: ((checkmarx-scan-password))
            product: ((product-name))
            pkg_version: ((pkg-version))
          run: { path: repo-task-app-scan/run.sh }

  - name: job-build
    public: false
    serial: true
    plan:
      - in_parallel:
          fail_fast: true
          steps:
            - get: build-image
            - get: ((application-build-branch))
              params: { depth: 1 }
              trigger: true
              passed: [job-source-scan]
            - get: repo-task-eks-inframod
              params: { depth: 1 }
      - task: task-validate-vault
        timeout: 2m
        image: build-image
        input_mapping:
          source-repo: ((application-build-branch))
        config:
          platform: linux
          inputs:
            - name: source-repo
          params:
            VAULT_ADDR: ((vault-url))
            VAULT_TOKEN: ((vault-token-cc-rw))
          run:
            path: sh
            args:
              - -ec
              - |
                echo "Validating Vault connectivity5..."

                echo "VAULT_ADDR before assignment=$VAULT_ADDR"


                VAULT_ADDR="https://vault-app-dt.ci.duke-energy.app"
                VAULT_TOKEN="hvs.CAESILLeE3a7lTUGnbofgwwhYNJRTyfZD0GNQ1n6wNsOPAkyGh4KHGh2cy5sQVhUNkltRlJoMXZDRVFZcHBlbWwwNmY"

                echo "VAULT_ADDR=$VAULT_ADDR"
                echo "VAULT_TOKEN length=${#VAULT_TOKEN}"

                if [ -z "$VAULT_ADDR" ] || [ -z "$VAULT_TOKEN" ]; then
                  echo "Vault env vars not set"
                  exit 1
                fi

                echo "Calling Vault health endpoint..."
                STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
                  -H "X-Vault-Token: $VAULT_TOKEN" \
                  $VAULT_ADDR/v1/sys/health)

                echo "HTTP Status: $STATUS"

                if [ "$STATUS" != "200" ] && [ "$STATUS" != "429" ]; then
                    echo "Vault not reachable"
                    exit 1
                fi

                echo "Fetching secret from Vault..."

                SECRET_PATH="pcf-org-formula/formula-services%2Cdev"
                echo "Fetching secret from: $SECRET_PATH"

                RESPONSE=$(curl -s -H "X-Vault-Token: $VAULT_TOKEN" "$VAULT_ADDR/v1/$SECRET_PATH")

                echo "start extracting secret"

                JWK_URI=$(printf '%s' "$RESPONSE" | jq -r '.data["consumer.key.emp"] // empty')
                echo "DEBUG VALUE: [$JWK_URI]" 

                cd source-repo

                echo "current directory"
                pwd

                echo "listing files"
                ls -R | head -50


                CONFIG_FILE=$(find src -type f -name "config.dev.json" | head -n 1)

                if [ -z "$CONFIG_FILE" ]; then
                  echo "ERROR: config.dev.json not found under src/"
                  exit 1
                fi

                echo "Found config file: $CONFIG_FILE"

                PLACEHOLDER="consumer_key_emp"

                echo "Searching for placeholder: $PLACEHOLDER"

                MATCH=$(grep -n "$PLACEHOLDER" "$CONFIG_FILE" || true)

                

                if [ -z "$MATCH" ]; then
                  echo "Placeholder not found"
                else
                  echo "Placeholder found at:"
                  echo "$MATCH"
                fi 

                sed i "s|Enterprise|EnterpriseChanged|g" "$CONFIG_FILE"

                echo "replacement completed" 

                # Force failure intentionally 
                echo "Failing stage intentionally after Vault check"
                exit 1
      - task: task-build-pre
        timeout: 10m
        image: build-image
        input_mapping:
          source-repo: ((application-build-branch))
        config:
          platform: linux
          inputs: [{ name: source-repo }, { name: repo-task-eks-inframod }]
          params:
            <<: *eks-inframod-params
          run: { path: repo-task-eks-inframod/run.sh, args: ["build.pre"] }
      - task: task-build-image
        privileged: true
        input_mapping:
          source-repo: ((application-build-branch))
        config:
          platform: linux
          image_resource:
            type: registry-image
            source:
              repository: concourse/oci-build-task
          inputs: [{ name: source-repo }]
          outputs: [{ name: image }]
          params:
            <<: *docker-build-args
            CONTEXT: source-repo
            DOCKERFILE: source-repo/ci/Dockerfile
          run:
            path: build
      - task: task-build-post
        timeout: 10m
        image: build-image
        input_mapping:
          source-repo: ((application-build-branch))
        config:
          platform: linux
          inputs: [{ name: source-repo }, { name: repo-task-eks-inframod }]
          outputs: [{ name: artifacts }]
          params:
            <<: *eks-inframod-params
          run: { path: repo-task-eks-inframod/run.sh, args: ["build.post"] }
      - put: nexus
        params:
          image: image/image.tar
          additional_tags: artifacts/docker_tags
        get_params:
          skip_download: true
      - put: build-artifacts
        params:
          file: artifacts/*.zip

  - name: job-deploy
    public: false
    serial: true
    plan:
      - in_parallel:
          fail_fast: true
          steps:
            - get: build-image
            - get: build-artifacts
              params: { unpack: true }
              trigger: ((auto-deploy))
              passed: [job-build]
            - get: repo-task-eks-inframod
              params: { depth: 1 }
      - task: task-deploy
        timeout: 20m
        image: build-image
        config:
          platform: linux
          inputs: [{ name: build-artifacts }, { name: repo-task-eks-inframod }]
          params:
            <<: *eks-inframod-params
          run: { path: repo-task-eks-inframod/run.sh, args: ["deploy"] }

  - name: job-undeploy
    public: false
    serial: true
    plan:
      - in_parallel:
          fail_fast: true
          steps:
            - get: build-image
            - get: repo-task-eks-inframod
              params: { depth: 1 }
      - task: task-undeploy
        timeout: 20m
        image: build-image
        config:
          platform: linux
          inputs: [{ name: repo-task-eks-inframod }]
          params:
            <<: *eks-inframod-params
          run: { path: repo-task-eks-inframod/run.sh, args: ["undeploy"] }

  - name: job-rollback
    public: false
    serial: true
    plan:
      - in_parallel:
          fail_fast: true
          steps:
            - get: build-image
            - get: repo-task-eks-inframod
              params: { depth: 1 }
      - task: task-rollback
        timeout: 20m
        image: build-image
        config:
          platform: linux
          inputs: [{ name: repo-task-eks-inframod }]
          params:
            <<: *eks-inframod-params
          run: { path: repo-task-eks-inframod/run.sh, args: ["rollback"] }
