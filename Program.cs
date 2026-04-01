printf '%s' "$RESPONSE" | jq '.data | keys'

JWK_URI=$(printf '%s' "$RESPONSE" | jq -r '.data["security.jwt.jwkSetUri"] // empty')

echo "DEBUG VALUE: [$JWK_URI]"  
