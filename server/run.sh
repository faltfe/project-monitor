#!/bin/bash

set -e

cd `dirname $0`
r=$(pwd)
echo "$r"


cd "$r"/discovery-service
echo "Starting discovery service..."
mvn -q clean spring-boot:run & echo $! > ./pid.file &

cd "$r"/gateway
echo "Starting gateway..."
mvn -q clean spring-boot:run & echo $! > ./pid.file &

echo "Starting financial service..."
cd "$r"/financial-monitor
mvn -q clean spring-boot:run & echo $! > ./pid.file &

echo "Starting vacation service..."
cd "$r"/vacation-monitor
mvn -q clean spring-boot:run & echo $! > ./pid.file &

# Client
# cd $r/client
# npm install
# echo "Starting Angular Client..."
# npm start
