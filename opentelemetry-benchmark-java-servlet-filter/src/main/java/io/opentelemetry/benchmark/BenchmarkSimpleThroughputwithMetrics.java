package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

public class BenchmarkSimpleThroughputwithMetrics extends BenchmarkSimpleServletBase{
    
    //Not instrumented
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public String noInstrumentation(StateVariablesNoInstrumentation state)
            throws Exception {
        return super.testSimpleRequest(state);
    }

    //Jaeger
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public String jaegerTracer(StateVariablesJaegerWithMetricFilters state)
            throws Exception {
        return super.testSimpleRequest(state);
    }

    //Otlp
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public String OtlpTracer(StateVariablesOtlpWithMetricFilters state)
            throws Exception {
        return super.testSimpleRequest(state);
    }
}
