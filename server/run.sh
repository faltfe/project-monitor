#!/bin/bash

set -e

cd `dirname $0`
r=`pwd`
echo $r

# Eureka
cd $r/discovery-service
echo "Starting Eureka Service..."
mvn -q clean spring-boot:run &

# Eureka
cd $r/gateway
echo "Starting Gateway Service..."
mvn -q clean spring-boot:run &

# Beer Service
echo "Starting Financial Service..."
cd $r/financial-monitor
mvn -q clean spring-boot:run &

# Edge Service
echo "Starting Vacation Service..."
cd $r/vacation-monitor
mvn -q clean spring-boot:run &

# Client
# cd $r/client
# npm install
# echo "Starting Angular Client..."
# npm start
