#!/bin/bash
# AUTO_AGENT=${AUTO_AGENT:${WORK_DIR}/opentelemetry-javaagent-all.jar}
WORK_DIR=${WORK_DIR:-/src}
RESULTS_DIR=${RESULTS_DIR:-/results}


export JAVA_HOME=/usr/lib/jvm/jre-openjdk

for test in java-jdbc; do
    cd "${WORK_DIR}/opentracing-benchmark-${test}"
    if [ $? != 0 ]; then
        echo "🛑 Failed to find the location for the test '${test}'"
        continue
    fi

    echo "🩹 ${test} - Manually patching pom.xml to build with Java 11"
    sed 's/<java.version>14/<java.version>11/gi' -i pom.xml
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

    echo "🏃 ${test} - Running 5 times"
        java -jar target/benchmarks.jar > "${RESULTS_DIR}/benchmark-${test}-${i}.log" 2>&1
        if [ $? != 0 ]; then
            echo "🛑 Failed to run the test '${test}', iteration ${i}. Skipping next iterations."
            break
        fi

        echo "✔️ ${test} - Iteration ${i} done. Waiting about 1 minute for the machine to settle down."
        sleep 1m
    

    echo "✅ ${test} - Done!"
done



