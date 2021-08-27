#!/bin/bash
REMOTE_HOST=${REMOTE_HOST:-147.75.80.139}
REMOTE_RESULTS_DIR=${REMOTE_RESULTS_DIR:-/results}
RESULTS_DIR=${RESULTS_DIR:-./results-$(date -u +%s)}

ssh root@${REMOTE_HOST} "/tmp/runner.sh"

echo "🎣 Retrieving results into ${RESULTS_DIR}"
scp -r root@${REMOTE_HOST}:${REMOTE_RESULTS_DIR} ${RESULTS_DIR} > /tmp/scp-results.log 2>&1
if [ $? != 0 ]; then
    echo "🛑 Failed to retrieve results"
    cat /tmp/scp-results.log
    exit 1
fi
