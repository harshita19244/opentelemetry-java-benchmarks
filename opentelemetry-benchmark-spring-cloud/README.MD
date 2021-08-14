
## Description

These tests use the petclinic sample spring-based application. The application is initialized for each test iteration in the BenchmarkPetclinicBase, and using profiles, the right tracer is injected in TracerConfiguration.

The different tests measure the process of finding a pet owner by id in a non instrumentation scenario and when instrumented with different tracers. The tests are performing the operation calling to the Spring services directly. 

## Dependencies

This project uses this [Opentelemetry dependency](https://github.com/open-telemetry/opentelemetry-java):

```xml
  <project>
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>io.opentelemetry</groupId>
        <artifactId>opentelemetry-bom</artifactId>
        <version>1.1.0</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>io.opentelemetry</groupId>
      <artifactId>opentelemetry-api</artifactId>
    </dependency>
  </dependencies>
</project>

```

Additionally, it also uses the following tracer dependencies

```xml
        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-exporter-jaeger</artifactId>
        </dependency>
        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-exporter-otlp</artifactId>
        </dependency>
        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-extension-noop-api</artifactId> 
            <version>1.3.0-alpha</version>   
        </dependency>
        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-sdk</artifactId>
        </dependency>
```

## Results

The latest results are located [here]().
Graphs are constructed based on raw results located in the ``results`` folder.

## SampleTime metrics

- X axis: represents each execution result.
- Y axis: represents how long time it takes for the benchmark method to execute.

![BenchmarkStringConcatenationSampleTime-5](results-imgs/)


## Throughput metrics

- X axis: represents each execution result.
- Y axis: represents of number of operations per second  (the number of times per second the benchmark method could be executed).

![BenchmarkStringConcatenationThroughput-5](results-imgs/)

## Environment
The tests were executed in a Linux Bare Metal machine with the following characteristics: 

- OS: CentOS 8
- RAM: 32 GB
- Disk: 1 x 240GB SSD
- Processor: 1 x Intel(R) Xeon(R) CPU E3-1578L v5 @ 2.00GHz

