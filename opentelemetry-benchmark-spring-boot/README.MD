
## Description

These tests use an example spring boot application containing a basic billing process. The application is 
initialized for each test iteration in the [`StateVariables`](), 
and using profiles, the right tracer is injected in [`TracerConfiguration`]() 
   
These tests measure the process of issuing an invoice in a non-instrumented and instrumented scenario respectively. For the instrumented scenario, we use two different trace exporters : Jaeger and Otlp.
The tests are performing the operation calling to the Spring services directly.

The application is executed as follows:
- Create an invoice

In this step, in the [`TracedInvoiceService`](), a span is created for the corresponding tracer. Attributes such as customer's email and tax id are set with the corresponding values obtained from services.

- Add line items

In this step, the invoice is retrieved from the repository by id, and the line item is added to it. 

- Issue an invoice

This step involves computing the invoice taxes and notifying the customer. It also includes setting the invoice date, due date, and changing the invoice state. 

In the step of computing taxes, in [`TracedTaxService`]() a span is created.
the taxId is retrieved from the span baggage and is set as a tag to the span and the name of the step is logged. The currency, total and the total of the invoice are setting as tags of the span too.

The step of notifying the customer in [`NotificationService`]() creates a span and, and set the recipient address as a tag and the tax id (retrieved from the baggage) as a tag too.  

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
```

## Results

The latest results are located [here]().
Graphs are constructed based on raw results located in the ``results`` folder.

## SampleTime metrics

- X axis: represents each execution result.
- Y axis: represents how long time it takes for the benchmark method to execute.

![BenchmarkStringConcatenationSampleTime-5](results-imgs/BenchmarkStringConcatenationSampleTime5.png)

![BenchmarkStringConcatenationSampleTime-6](results-imgs/BenchmarkStringConcatenationSampleTime6.png)

## Throughput metrics

- X axis: represents each execution result.
- Y axis: represents of number of operations per second  (the number of times per second the benchmark method could be executed).

![BenchmarkStringConcatenationThroughput-5](results-imgs/BenchmarkStringConcatenationThroughput5.png)

![BenchmarkStringConcatenationThroughput-6](results-imgs/BenchmarkStringConcatenationThroughput6.png)


## Environment
The tests were executed in a personal notebook with these characteristics:

- Model Name: Lenovo IdeaPad 530S-15IKB
- Processor Name:	Intel Core i5 8th gen
- Processor Speed:	1.8 GHz
- Number of Processors:	8
- Number of Cores: 4
- L1 Cache: 256 KB
- L2 Cache:	1.0 MB
- L3 Cache:	6.0 MB
- Memory:	8 GB

