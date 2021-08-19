# JMH benchmarks for OpenTelemetry library

This repository contains a set of benchmarks to assess the performance of the OpenTelemetry instrumentation library against the OpenTracing library. 

List of current tests:

| Test                                            | No Instrumentation | Jaeger Trace Exporter | Otlp Trace Exporter | 
| ----------------------------------------------- | ------------------ | ----------- | ----------- | 
| Throughput, one span, one tag, one log message  |          ✓         |      ✓      |       ✓     |      
| Time, one span, one tag, one log message        |          ✓         |      ✓      |       ✓     |     

## Code structure

Each benchmark contains common packages to the same purpose: 

```
opentelemetry-benchmark-spring-cloud
│   
│   
└───io.opentelemetry.benchmark
│   │
│   └───config
│   │   ## Classes to initialize the different tracers
│   │
│   └───main
│   │   ## The class to be executed by JMH 
│   │
│   └───petclinic
│   │   ## Depending on the test, there is one package which contains the example application to be executed by JMH. 
│   │      In this example for opentelemetru for spring cloud, the official petclinic application is used for the tests.
│   │    
│   Benchmark*.java 
│   ## The different JMH benchmark tests are located in this package
│   │
```
Some tests additionally use the OpenTelemetry javaagent for automatic instrumentation. 

## Properties

This properties can be overrided by system properties:

```properties
benchmark.warmup.iterations=5
benchmark.test.iterations=5
benchmark.test.forks=1
benchmark.test.threads=1
benchmark.global.testclassregexpattern=.*Benchmark.*
benchmark.global.resultfileprefix=jmh-

```

## Running it
Run the following command (inside on a specific project)

```
mvn clean install
java -jar target/benchmarks.jar
```

## Feedback

Performance tests are tricky and we are sure that some things might have done better. We welcome your constructive comments and we appreciate PRs.

## Results
We use [JMH-visualizer](https://github.com/jzillmann/jmh-visualizer) to present the benchmark results. Thanks [jzillmann](https://github.com/jzillmann) 
for this amazing tool!. 

The results of different scenarios are located in each project:
- [opentelemetry-benchmark-simple-java](https://github.com/harshita19244/opentelemetry-java-benchmarks/tree/main/opentelemetry-benchmark-simple-java)
- [opentelemetry-benchmark-spring-boot](https://github.com/harshita19244/opentelemetry-java-benchmarks/tree/main/opentelemetry-benchmark-spring-boot)
- [opentelemetry-benchmark-java-servlet-filter](https://github.com/harshita19244/opentelemetry-java-benchmarks/tree/main/opentelemetry-benchmark-java-servlet-filter)
- [opentelemetry-benchmark-spring-cloud](https://github.com/harshita19244/opentelemetry-java-benchmarks/tree/main/opentelemetry-benchmark-spring-cloud)
- [opentelemetry-benchmark-java-jaxrs](https://github.com/harshita19244/opentelemetry-java-benchmarks/tree/main/opentelemetry-benchmark-jaxrs)
- [opentelemetry-benchmark-java-jdbc](https://github.com/harshita19244/opentelemetry-java-benchmarks/tree/main/opentelemetry-benchmark-jdbc)
