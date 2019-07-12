#!/bin/bash
if [ $# -lt 1 ]; then
	echo "Usage:"
	echo "  $0 MEETUP_KEY [MEETUP_GROUP]"
	exit -1;
fi
MEETUP_KEY=$1
GROUP_NAME=${2:-Torun-JUG}

cd xenia-api

trap 'kill -TERM $PID1 $PID2' TERM INT

mvn -DMEETUP_KEY=$MEETUP_KEY -DMEETUP_GROUP_URL_NAME=$GROUP_NAME spring-boot:run &
PID1=$!

cd ../xenia-ng

npm start &
PID2=$!

wait $PID1
trap - TERM INT
wait $PID1

wait $PID2
wait $PID2
