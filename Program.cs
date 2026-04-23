name: Apigee Proxy Deploy

on:
  workflow_dispatch:
    inputs:
      api_org:
        description: "The organization in the Apigee Hybrid environment you're targeting, e.g. de-apigee-dt"
        required: true
        type: choice
        options:
          - de-apigee-dt
          - de-apigee-qa
          - de-apigee-prod
      api_env:
        description: "Name of the apigee hybrid env used for syncing. Ex: dev-2"
        required: true
        type: choice
        options:
          - dev-10
          - qa-10
          - prod-10
      parent_env:
        description: >-
          Name of the general environment in apigee hybrid. This would be something like "dev" or "qa"
          instead of "dev-2" or "qa-10". This is necessary for grabbing secrets from vault that were migrated from the concourse vault for the apigee credentials.
        required: true
        type: choice
        options:
          - dev
          - qa
          - prod

permissions:
  contents: read
  id-token: write

jobs:
  deploy-proxy:
    uses: dukeenergy-corp/duke-actions/.github/workflows/apigee-proxy-deploy.yaml@master
    with:
      api-org: ${{ github.event.inputs.api_org }}
      api-env: ${{ github.event.inputs.api_env }}
      api-name: formula-common-resources
      parent-env: ${{ github.event.inputs.parent_env }}
