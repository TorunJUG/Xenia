#!/bin/bash
if [ $# -lt 1 ]; then
	echo "Usage:"
	echo "  $0 OAUTH_CLIENT_ID OAUTH_CLIENT_SECRET [MEETUP_GROUP]"
	exit -1;
fi
OAUTH_CLIENT_ID=$1
OAUTH_CLIENT_SECRET=$2
GROUP_NAME=${3:-Torun-JUG}

cd xenia-api

trap 'kill -TERM $PID1 $PID2' TERM INT

mvn spring-boot:run -Dspring-boot.run.arguments=--oauth.clientId=$OAUTH_CLIENT_ID,--oauth.clientSecret=$OAUTH_CLIENT_SECRET,--meetup.groupUrlName=$GROUP_NAME &
PID1=$!

cd ../xenia-ng

npm start &
PID2=$!

wait $PID1
trap - TERM INT
wait $PID1

wait $PID2
wait $PID2
