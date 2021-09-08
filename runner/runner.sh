#!/bin/bash
WORK_DIR=${WORK_DIR:-/src}
AUTO_AGENT=${AUTO_AGENT:${WORK_DIR}/opentelemetry-javaagent-all.jar}
RESULTS_DIR=${RESULTS_DIR:-/results}

export JAVA_HOME=/usr/lib/jvm/jre-openjdk

echo "🏃 Starting Jaeger"
/tmp/jaeger-1.25.0-linux-amd64/jaeger-all-in-one --memory.max-traces 10 >"${RESULTS_DIR}/jaeger-${test}.log" 2>&1 &pid=$!
if [ $? != 0 ]; then
    echo "🛑 Failed to start Jaeger"
    exit 1
fi

echo "🏃 Starting Collector"
/tmp/opentelemetry-collector-0.34.0-linux-amd64/otel-col --config ./tmp/otel-config.yaml >"${RESULTS_DIR}/otlp-${test}.log" 2>&1 &pid2=$!
if [ $? != 0 ]; then
    echo "🛑 Failed to start Collector"
    exit 1
fi

for test in simple-java; do
    cd "${WORK_DIR}/opentelemetry-benchmark-${test}"
    if [ $? != 0 ]; then
        echo "🛑 Failed to find the location for the test '${test}'"
        continue
    fi

    echo "🩹 ${test} - Manually patching pom.xml to build with Java 11"
    sed 's/<javac.target>14/<javac.target>11/gi' -i pom.xml
    if [ $? != 0 ]; then
        echo "🛑 Failed to patch the test '${test}'"
        continue
    fi

    echo "⚒️ ${test} - Building"
    mvn clean install -l "${RESULTS_DIR}/build-${test}.log"
    if [ $? != 0 ]; then
        echo "🛑 Failed to build the test '${test}'"
        continue
    fi

    echo "🏃 Running ${test}"
    java -jar target/benchmarks.jar >"${RESULTS_DIR}/benchmark-${test}.log" 2>&1
    if [ $? != 0 ]; then
        echo "🛑 Failed to run the test '${test}'."
        break
    fi

    echo "✔️ ${test} done. Waiting about 1 minute for the machine to settle down."
    sleep 1m

    echo "✅ ${test} - Done!"
done


echo "🎣 Retrieving Jaeger metrics"
curl -so "${RESULTS_DIR}/jaeger-metrics.log" http://localhost:14269/metrics
if [ $? != 0 ]; then
    echo "🛑 Failed to retrieve the Jaeger metrics."
    exit 1
fi

echo "🏃 Stopping Jaeger"
kill -9 ${pid}
echo "🏃 Stopping Collector"
kill -9 ${pid2}
