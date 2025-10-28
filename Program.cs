#!/bin/bash
duke-install awscli 2.2.4

# Exit on error
set -e

function prep_web_files {

# unzip files and move things around in preparation for deploy
echo "unzipping $1 from build stage..."
for zip in $(find $1/ -maxdepth 1 -type f -name "*.zip");do
    unzip -d $1/ "$zip" || exit 1
    rm -v "$zip" || exit 1
done
mv $1/build/* $1/
rm -r $1/build $1/ci $1/url $1/version

# make associative array out of replacement values from vault
declare -A replacement_values
while IFS="=" read -r key value
do
    replacement_values[$key]="$value"
done < <(jq -r 'to_entries|map("\(.key)=\(.value)")|.[]' replacement_values/vault_data.json)

# loop thru the array and swap out value for key in application code
for key in "${!replacement_values[@]}"
do
    insert_env_variable $key ${replacement_values[$key]}
done

}

function insert_env_variable {
echo "replacing $1 with $2"
# sed takes anything as a separator as long as it's not in the value
# so if you're using a value with ">" in it you'll need to change
# eg change s>$1>$2>g in the 3 lines below to s*$1*$2*g
find "build-artifacts/" -maxdepth 4 -type f -name "*.js" -exec sed -r -i "s>$1>$2>g" "{}" \;
find "build-artifacts/" -maxdepth 4 -type f -name "*.json" -exec sed -r -i "s>$1>$2>g" "{}" \;
find "build-artifacts/" -maxdepth 4 -type f -name "*.ts" -exec sed -r -i "s>$1>$2>g" "{}" \;
}

# invoke the stuff declared above
prep_web_files build-artifacts


export AWS_REGION=${region}
echo "Region set to: ${region}"

echo "Setting AWS access tokens"
export AWS_ACCESS_KEY_ID=`cat secrets/access_key`
export AWS_SECRET_ACCESS_KEY=`cat secrets/secret_key`
export AWS_SESSION_TOKEN=`cat secrets/security_token`
echo "Successfully set AWS access tokens"


echo "Deleting contents of s3 bucket"
aws s3 rm --recursive s3://${bucket_name} > /dev/null

echo "uploading new files to s3"
aws s3 cp build-artifacts s3://${bucket_name}/ --recursive > /dev/null
