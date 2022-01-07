#!/bin/bash

set -e

cd $(dirname $0)
r=$(pwd)
echo "$r"

echo "Stopping financial service..."
cd "$r"/financial-monitor

if [ -f ./pid.file ]
then
    PID=$(cat ./pid.file)
    if ps -p "$PID" > /dev/null
    then
        mvn spring-boot:stop
        kill "$PID"
    fi
    rm pid.file
else
    echo "No PID file was found. The service was probably not running"
fi

echo "Stopping vacation service..."
cd "$r"/vacation-monitor
if [ -f ./pid.file ]
then
    PID=$(cat ./pid.file)
    if ps -p "$PID" > /dev/null
    then
        mvn spring-boot:stop
        kill "$PID"
    fi
    rm pid.file
else
    echo "No PID file was found. The service was probably not running"
fi

cd "$r"/gateway
echo "Stopping gateway..."
if [ -f ./pid.file ]
then
    PID=$(cat ./pid.file)
    if ps -p "$PID" > /dev/null
    then
        mvn spring-boot:stop
        kill "$PID"
    fi
    rm pid.file
else
    echo "No PID file was found. The service was probably not running"
fi

cd "$r"/discovery-service
echo "Stopping discovery service..."
if [ -f ./pid.file ]
then
    PID=$(cat ./pid.file)
    if ps -p "$PID" > /dev/null
    then
        mvn spring-boot:stop
        kill "$PID"
    fi
    rm pid.file
else
    echo "No PID file was found. The service was probably not running"
fi

# Client
# cd $r/client
# npm install
# echo "Starting Angular Client..."
# npm start
