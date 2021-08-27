#!/bin/bash
WORK_DIR=${WORK_DIR:-/src}
AUTO_AGENT=${AUTO_AGENT:${WORK_DIR}/opentelemetry-javaagent-all.jar}
RESULTS_DIR=${RESULTS_DIR:-/results}

export JAVA_HOME=/usr/lib/jvm/jre-openjdk

echo "🏃 Starting Jaeger"
/tmp/jaeger-1.25.0-linux-amd64/jaeger-all-in-one --memory.max-traces 1_000_000 >"${RESULTS_DIR}/jaeger-${test}.log" 2>&1 &
pid=$!
if [ $? != 0 ]; then
    echo "🛑 Failed to start Jaeger"
    exit 1
fi

for test in spring-boot simple-java spring-cloud; do
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

for test in jdbc jaxrs java-servlet-filter; do
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
    java -javaagent:"${AUTO_AGENT}" \
        -Dotel.resource.attributes=service.name="benchmark" \
        -Dotel.traces.exporter=jaeger -Dotel.metrics.exporter=jaeger \
        -Dotel.exporter.jaeger.endpoint=http://localhost:14250/ \
        -jar target/benchmarks.jar >"${RESULTS_DIR}/benchmark-${test}-jaeger.log" 2>&1

    java -javaagent:"${AUTO_AGENT}" \
        -Dotel.resource.attributes=service.name="benchmark" \
        -Dotel.traces.exporter=otlp \
        -Dotel.metrics.exporter=otlp \
        -Dotel.exporter.jaeger.endpoint=http://localhost:4317/ \
        -jar target/benchmarks.jar >"${RESULTS_DIR}/benchmark-${test}-otlp.log" 2>&1

    java -javaagent:"${AUTO_AGENT}" \
        -Dotel.resource.attributes=service.name="benchmark" \
        -Dotel.metrics.exporter=none \
        -jar target/benchmarks.jar >"${RESULTS_DIR}/benchmark-${test}-notracer.log" 2>&1

    if [ $? != 0 ]; then
        echo "🛑 Failed to run the test '${test}'. Skipping next iterations."
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
kill "${pid}"
wait ${pid}
