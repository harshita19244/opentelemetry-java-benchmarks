package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

public class BenchmarkStringConcatenationThroughput extends BenchmarkStringConcatenationBase {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public String jaegerTracer(StateVariables state){
        return doJaegerTracer(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public String OtlpTracer(StateVariables state){
        return doOtlpTracer(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public String noInstrumentation(StateVariables state) {
        return doNoInstrumentation(state);
    }
}
