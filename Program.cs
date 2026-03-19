curl -s \
  -H "X-Vault-Token: $VAULT_TOKEN" \
  $VAULT_ADDR/v1/concourse/data/formula/<secret-name> \
  | jq -r '.data.data.database.url, .data.data.database.user'
