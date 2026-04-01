JWK_URI=$(printf '%s' "$SECRET_RESPONSE" | jq -r '.data["security.jwt.jwkSetUri"]')

echo "security.jwt.jwkSetUri: $JWK_URI"
echo "Length: ${#JWK_URI}"
