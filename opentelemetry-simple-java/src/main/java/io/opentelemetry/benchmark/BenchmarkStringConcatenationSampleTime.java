package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

public class BenchmarkStringConcatenationSampleTime extends BenchmarkStringConcatenationBase {

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public String jaegerTracer(StateVariables state){
        return doJaegerTracer(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public String OtlpTracer(StateVariables state){
        return doOtlpTracer(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public String noInstrumentation(StateVariables state) {
        return doNoInstrumentation(state);
    }


}


