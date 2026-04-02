CONFIG_FILE=$(find src -type f -name "config.dev.json" | head -n 1)

if [ -z "$CONFIG_FILE" ]; then
  echo "ERROR: config.dev.json not found under src/"
  exit 1
fi

echo "Found config file: $CONFIG_FILE"

PLACEHOLDER="__JWK_URI__"

echo "Searching for placeholder: $PLACEHOLDER"

MATCH=$(grep -n "$PLACEHOLDER" "$CONFIG_FILE" || true)

if [ -z "$MATCH" ]; then
  echo "Placeholder not found"
else
  echo "Placeholder found at:"
  echo "$MATCH"
fi  
