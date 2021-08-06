#!/bin/bash
WORK_DIR=${WORK_DIR:-/src}
RESULTS_DIR=${RESULTS_DIR:-/results}

export JAVA_HOME=/usr/lib/jvm/jre-openjdk

for test in simple-java jdbc; do
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

    echo "✅ ${test} - Done!"
done
