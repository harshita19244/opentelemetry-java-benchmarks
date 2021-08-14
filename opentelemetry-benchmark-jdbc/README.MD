
## Description
These tests use a sample [spring boot application](https://github.com/tachuela700/course-management) containing a basic course management process.

The application is initialized for each test iteration in the [`BenchmarkCourseManagementBase`](src/main/java/io/opentracing/contrib/benchmarks/BenchmarkCourseManagementBase.java), 
and using profiles, the right tracer is injected, see [`TracerConfiguration`](src/main/java/io/opentracing/contrib/benchmarks/config/TracerConfiguration.java).

The different tests measure the process of getting the list of all courses in a not instrumentation scenario and instrumented with different tracers. 
The tests are performing the operation calling to the Spring services directly.

In the application properties, the url and driver class name are modified to enable the tracing:

```properties
### Define H2 Datasource config
spring.datasource.platform=h2
spring.datasource.url=jdbc:tracing:h2:mem:app_db;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.data.jpa.repositories.enabled=false
```

## Dependencies

This project uses this [Opentelemetry dependency](https://github.com/open-telemetry/opentelemetry-java-instrumentation).
The javaagent serves as a tool to inject tracers and instrument the application.The project additionally requires a running Jaeger tracer. The simplest way to start the Jaeger all-in-one is to use the pre-built image published to DockerHub (a single command line).

```docker
  docker run -d --name jaeger \
  -e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14268:14268 \
  -p 14250:14250 \
  -p 9411:9411 \
  jaegertracing/all-in-one:1.24


```
It also requires a running the OpenTelemetry collector to account for the OTLP trace exporter. The collector can be started using the pre-built image published on DockerHub.

```docker
docker run --publish 4317:4317 otel/opentelemetry-collector
```

## Results

The latest results are located [here]().
Graphs are constructed based on raw results located in the ``results`` folder.

### SampleTime metrics

- X axis: represents each execution result.
- Y axis: represents how long time it takes for the benchmark method to execute.

### Throughput metrics

- X axis: represents each execution result.
- Y axis: represents of number of operations per second  (the number of times per second the benchmark method could be executed).

### OTLP Trace Exporter Instrumentation metrics
![BenchmarkOTLP](results-imgs/BareMetalRun1Otlp.png)

### Jaeger Trace Exporter Instrumentation metrics
![BenchmarkJaeger](results-imgs/BareMetalRun1Jaeger.png)


## Environment
The tests were executed in a Linux Bare Metal machine with the following characteristics: 

- OS: CentOS 8
- RAM: 32 GB
- Disk: 1 x 240GB SSD
- Processor: 1 x Intel(R) Xeon(R) CPU E3-1578L v5 @ 2.00GHz

