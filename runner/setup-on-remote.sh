#!/bin/bash
REMOTE_HOST=${REMOTE_HOST:-145.40.68.181}
SETUP_OPTS=${SETUP_OPTS:-""}

while getopts f: flag
do
    case "${flag}" in
        f) SETUP_OPTS="-f ";;
    esac
done

echo "📋 Copying scripts to the remote host"
scp ./runner.sh "root@${REMOTE_HOST}:/tmp/runner.sh" > /tmp/scp-runner.logs 2>&1
if [ $? != 0 ]; then
    echo "🛑 Failed to copy the runner script"
    cat /tmp/scp-runner.logs
    exit 1
fi

scp ./setup.sh "root@${REMOTE_HOST}:/tmp/setup.sh" > /tmp/scp-setup.logs 2>&1
if [ $? != 0 ]; then
    echo "🛑 Failed to copy the setup script"
    cat /tmp/scp-setup.logs
    exit 1
fi

ssh root@${REMOTE_HOST} "/tmp/setup.sh ${SETUP_OPTS}"
