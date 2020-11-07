#!/usr/bin/env bash
set -m

# Inspired by https://gist.github.com/rajagp/8d05314d85fcbf169ee39a671077a566

/entrypoint.sh couchbase-server &

sleep 2

echo "Waiting for CouchBase service to start up so we can provision it ";

until $(curl --output /dev/null --silent --head --fail http://127.0.0.1:8091/pools); do
  printf '.'
  sleep 1
  done;


 # Setup initial cluster/ Initialize Node (services index and query needed for cache.clear())
couchbase-cli cluster-init -c 127.0.0.1 \
 --cluster-username admin \
 --cluster-password admin1 \
 --services data,index,query \
 --cluster-ramsize 512 \
 --update-notifications 0 \
 --index-storage-setting memopt # for in memory only

# Setup Administrator username and password
curl -v http://127.0.0.1:8091/settings/web -d port=8091 -d username=admin -d password=admin1

sleep 2

# Create bucket: demoDockerBucket
couchbase-cli bucket-create -c 127.0.0.1:8091 \
  --username admin \
  --password admin1 \
  --bucket demoDockerBucket \
  --bucket-type ephemeral \
  --bucket-ramsize 256

sleep 2

# Create user for demoDockerBucket (write data & create index)
couchbase-cli user-manage -c 127.0.0.1:8091 \
  -u admin \
  -p admin1 \
  --set --rbac-username demoUser --rbac-password demoPass \
  --rbac-name "Demo Docker Test User" \
  --roles data_writer[demoDockerBucket],query_manage_index[demoDockerBucket],query_delete[demoDockerBucket],query_select[demoDockerBucket] \
  --auth-domain local

sleep 2

fg 1