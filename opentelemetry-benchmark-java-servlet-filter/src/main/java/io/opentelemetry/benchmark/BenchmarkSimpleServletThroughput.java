package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

public class BenchmarkSimpleServletThroughput extends BenchmarkSimpleServletBase {

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
    public String jaegerTracer(StateVariablesJaegerWithoutMetricFilters state)
            throws Exception {
        return super.testSimpleRequest(state);
    }

    //Otlp
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public String OtlpTracer(StateVariablesOtlpWithoutMetricFilters state)
            throws Exception {
        return super.testSimpleRequest(state);
    }
}
