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
        file: ((application-build-branch))/ci/pipeline-prod.yml
        vars: { env-config: ((env-config)) }
        var_files:
          - ((application-build-branch))/ci/config/parameters-common.yml
          - ((application-build-branch))/ci/config/parameters-((env-config)).yml

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
