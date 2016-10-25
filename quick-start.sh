#!/bin/bash
if [ $# -lt 1 ]; then
	echo "Usage:"
	echo "  $0 MEETUP_KEY [MEETUP_GROUP]"
	exit -1;
fi
MEETUP_KEY=$1
GROUP_NAME=${2:-Torun-JUG}

BACKEND_SHA=2.0-develop
FRONTEND_SHA=2.0-develop
cd xenia-api
git checkout $BACKEND_SHA

trap 'kill -TERM $PID1 $PID2' TERM INT

mvn -DMEETUP_KEY=$MEETUP_KEY -DMEETUP_GROUP_URL_NAME=$GROUP_NAME spring-boot:run &
PID1=$!

cd ../xenia-ng
git checkout $FRONTEND_SHA
npm start &
PID2=$!

wait $PID1
trap - TERM INT
wait $PID1

wait $PID2
wait $PID2
