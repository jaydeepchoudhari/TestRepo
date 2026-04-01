#!/usr/bin/env bash

set -euo pipefail

echo "=== Vault Test Script ==="

# Validate env variables
if [[ -z "${VAULT_ADDR:-}" || -z "${VAULT_TOKEN:-}" ]]; then
  echo "ERROR: VAULT_ADDR or VAULT_TOKEN not set"
  exit 1
fi

echo "VAULT_ADDR=$VAULT_ADDR"
echo "VAULT_TOKEN length=${#VAULT_TOKEN}"

# ---- Step 1: Health Check ----
echo "Checking Vault health..."

STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -H "X-Vault-Token: $VAULT_TOKEN" \
  "$VAULT_ADDR/v1/sys/health")

echo "HTTP Status: $STATUS"

if [[ "$STATUS" != "200" && "$STATUS" != "429" ]]; then
  echo "Vault not reachable"
  exit 1
fi

echo "Vault reachable"

# ---- Step 2: Fetch Secret ----
SECRET_PATH="concourse/formula/<secret-name>"   # 👈 update this

echo "Fetching secret from: $SECRET_PATH"

RESPONSE=$(curl -s \
  -H "X-Vault-Token: $VAULT_TOKEN" \
  "$VAULT_ADDR/v1/$SECRET_PATH")

# Debug (optional)
# echo "$RESPONSE" | jq .

# ---- Step 3: Extract value ----
JWK_URI=$(echo "$RESPONSE" | jq -r '.data["security.jwt.jwkSetUri"]')

# ---- Step 4: Validate ----
if [[ "$JWK_URI" == "null" || -z "$JWK_URI" ]]; then
  echo "ERROR: security.jwt.jwkSetUri not found"
  exit 1
fi

# ---- Step 5: Print ----
echo "✅ security.jwt.jwkSetUri: $JWK_URI"
echo "Length: ${#JWK_URI}"

echo "=== SUCCESS ==="
