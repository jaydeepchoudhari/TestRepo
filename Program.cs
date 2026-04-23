name: Apigee Proxy Sync

on:
  workflow_dispatch:

permissions:
  contents: read
  id-token: write

jobs:
  apigee-sync-proxy:
    runs-on: ${{ vars.RUNNERSET_DEFAULT }}
    permissions:
      contents: read
      id-token: write
    steps:
      - name: Checkout Repository
        uses: actions/checkout@v6
      - name: Vault Fetch private key
        uses: dukeenergy-corp/duke-actions/composite/vault-fetch@master
        with:
          secrets: |
            formula/bitbucket-repo-private-key | GH_PRIVATE_KEY
      - name: Sync proxy
        uses: dukeenergy-corp/duke-actions/composite/apigee-sync@master
        with:
          api-type: sharedflow
          api-name: formula-common-resources
          api-env: dev-10
          parent-env: dev
          api-name-ver: 
