package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

public class BenchmarkSimpleServletSampleTime extends BenchmarkSimpleServletBase {

    //Not instrumented
    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public String Instrumentation(StateVariablesInstrumentation state) throws Exception {
        return super.testSimpleRequest(state);
    }

}


