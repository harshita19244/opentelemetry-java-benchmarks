#!/bin/bash
WORK_DIR=${WORK_DIR:-/src}
RESULTS_DIR=${RESULTS_DIR:-/results}
AUTO_AGENT=${AUTO_AGENT:-/C:/Users/USer/IdeaProjects/opentelemetry-benchmarks/opentelemetry-javaagent-all.jar}
SERVICE_NAME="benchmarks"

export JAVA_HOME=/usr/lib/jvm/jre-openjdk

for test in simple-java spring-boot spring-cloud jaxrs jdbc java-servlet-filter; do
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

    for test in simple-java spring-boot spring-cloud; do
        echo "🏃 ${test} - Running 5 times"
        for i in {1..5}; do
            java -jar target/benchmarks.jar > "${RESULTS_DIR}/benchmark-${test}-${i}.log" 2>&1
            if [ $? != 0 ]; then
                echo "🛑 Failed to run the test '${test}', iteration ${i}. Skipping next iterations."
                break
            fi
            echo "✔️ ${test} - Iteration ${i} done. Waiting about 1 minute for the machine to settle down."
            sleep 1m
        done
    done

    for test in jdbc jaxrs java-servlet-filter; do
        echo "🏃 ${test} - Running 5 times"
        for i in {1..5}; do
            java -javaagent:"${AUTO_AGENT}" \
            -Dotel.resource.attributes=service.name="${SERVICE_NAME}" \
            -Dotel.traces.exporter=jaeger \
            -Dotel.metrics.exporter=jaeger \
            -Dotel.exporter.jaeger.endpoint=http://localhost:14250/ \
            -jar target/benchmarks.jar > "${RESULTS_DIR}/benchmark-${test}-${i}.log" 2>&1

            java -javaagent:"${AUTO_AGENT}" \
            -Dotel.resource.attributes=service.name="${SERVICE_NAME}" \
            -Dotel.traces.exporter=otlp \
            -Dotel.metrics.exporter=otlp \
            -Dotel.exporter.jaeger.endpoint=http://localhost:4317/ \
            -jar target/benchmarks.jar > "${RESULTS_DIR}/benchmark-${test}-${i}.log" 2>&1

            java -javaagent:"${AUTO_AGENT}" \
            -Dotel.resource.attributes=service.name="${SERVICE_NAME}" \
            -Dotel.metrics.exporter=none \
            -jar target/benchmarks.jar > /dev/null 2>&1

            if [ $? != 0 ]; then
                echo "🛑 Failed to run the test '${test}', iteration ${i}. Skipping next iterations."
                break
            fi
            echo "✔️ ${test} - Iteration ${i} done. Waiting about 1 minute for the machine to settle down."
            sleep 1m
        done
    done

    echo "✅ ${test} - Done!"
done
