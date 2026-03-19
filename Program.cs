curl --header "X-Vault-Token: hvs.CAESICapXQpltcGstqXjf5yU44TkWJG3n6AKYPHDgKgPbc5wGh4KHGh2cy5WU1JRbFduRnViTUZZVXA2bDkyOG9mc2w" --request GET https://vault-cc-dt.duke-energy.com/concourse/data/formula/database > response.json

jq -r ".data.data.database.url, .data.data.database.user" response.json


jq: parse error: Invalid numeric literal at line 1, column 3
