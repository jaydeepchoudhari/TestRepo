      - task: task-validate-vault
        timeout: 3m
        image: build-image
        config:
          platform: linux
          inputs: []
          params:
            VAULT_ADDR: ((vault-url-cc))
            VAULT_TOKEN: ((vault-token-cc-rw))
          run:
            path: sh
            args:
              - -exc
              - |
                echo "Validating Vault connectivity..."

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

                # 👉 Update path & key as per your Vault KV v2 setup
                SECRET_RESPONSE=$(curl -s \
                  -H "X-Vault-Token: $VAULT_TOKEN" \
                  $VAULT_ADDR/v1/concourse/data/formula/database)

                echo "Raw response received (hidden for security)"

                # Extract key (example: configKey)
                DB_URL=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.database.url')
		DB_USER=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.database.user')

                # Validate
		if [ "$DB_URL" = "null" ] || [ -z "$DB_URL" ]; then
		  echo "database.url not found"
		  exit 1
		fi

		if [ "$DB_USER" = "null" ] || [ -z "$DB_USER" ]; then
		  echo "database.user not found"
		  exit 1
		fi

		# Print only lengths (safe)
		echo "database.url length: ${#DB_URL}"
		echo "database.user length: ${#DB_USER}"

                echo "Failing intentionally after validation"
                exit 1
