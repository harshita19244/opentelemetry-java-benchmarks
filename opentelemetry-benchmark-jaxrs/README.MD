
## Description

These tests use sample Jax-rs with a spring boot application containing a basic course management process. This application exposes a JAX-RS endpoint for the CourseResource. The tests consist of client rest calls .using unirest to get a list of all the courses in a non instrumented scenario and instrumented with different tracers. 

## Dependencies

This project uses this [Opentelemetry dependency](https://github.com/open-telemetry/opentelemetry-java-instrumentation):


## Results

The latest results are located [here]().
Graphs are constructed based on raw results located in the ``results`` folder.

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

