selected worker: concourse-customer-worker-30
+ echo Validating Vault connectivity...
Validating Vault connectivity...
+ echo VAULT_ADDR=https://vault-cc-dt.duke-energy.com
VAULT_ADDR=https://vault-cc-dt.duke-energy.com
+ echo VAULT_TOKEN length=95
VAULT_TOKEN length=95
+ [ -z https://vault-cc-dt.duke-energy.com ]
+ [ -z hvs.CAESICapXQpltcGstqXjf5yU44TkWJG3n6AKYPHDgKgPbc5wGh4KHGh2cy5WU1JRbFduRnViTUZZVXA2bDkyOG9mc2w ]
+ echo Calling Vault health endpoint...
Calling Vault health endpoint...
+ curl -s -o /dev/null -w %{http_code} -H X-Vault-Token: hvs.CAESICapXQpltcGstqXjf5yU44TkWJG3n6AKYPHDgKgPbc5wGh4KHGh2cy5WU1JRbFduRnViTUZZVXA2bDkyOG9mc2w https://vault-cc-dt.duke-energy.com/v1/sys/health
+ STATUS=429
+ echo HTTP Status: 429
HTTP Status: 429
+ echo Failing stage intentionally after Vault check
Failing stage intentionally after Vault check
+ exit 1
