#!/bin/bash
WORK_DIR=${WORK_DIR:-/src}
RESULTS_DIR=${RESULTS_DIR:-/results}

force=false

while getopts f: flag
do
    case "${flag}" in
        f) force=true;;
    esac
done

echo "🏗️ Preparing the perf runner host"
dnf install -y git maven java-11-openjdk-devel > /tmp/setup.log 2>&1
if [ $? != 0 ]; then
    echo "🛑 Failed to install required packages"
    cat /tmp/setup.log
    exit 1
fi

if [ ! -d ${WORK_DIR} ]; then
    echo "📦 Cloning the perf tests repo"
    git clone https://github.com/harshita19244/opentelemetry-java-benchmarks.git ${WORK_DIR} > /tmp/setup-clone.log 2>&1
    if [ $? != 0 ]; then
        echo "🛑 Failed to clone the perf tests repo"
        cat /tmp/setup-clone.log
        exit 1
    fi
fi

echo "🔧 Configuring results directory"
if [ -d ${RESULTS_DIR} ]; then
    if [ "$force" = true ]; then
        echo "🛑 Results directory exists. To prevent data loss, this execution is being aborted. Use the flag '-f' to force the removal of old results."
        exit 1
    fi

    rm -rf ${RESULTS_DIR}
    if [ $? != 0 ]; then
        echo "🛑 Failed to remove the pre-existing results directory '${RESULTS_DIR}'"
        exit 1
    fi
fi

mkdir -p ${RESULTS_DIR}
if [ $? != 0 ]; then
    echo "🛑 Failed to create the results directory '${RESULTS_DIR}'"
    exit 1
fi


echo "🔧 Configuring Java"
alternatives --set javac /usr/lib/jvm/java-11-openjdk-11.0.12.0.7-0.el8_4.x86_64/bin/javac
if [ $? != 0 ]; then
    echo "🛑 Failed to set javac to Java 11"
    exit 1
fi

alternatives --set java /usr/lib/jvm/java-11-openjdk-11.0.12.0.7-0.el8_4.x86_64/bin/java
if [ $? != 0 ]; then
    echo "🛑 Failed to set java to Java 11"
    exit 1
fi
