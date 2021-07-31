package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

public class BenchmarkSimpleServletThroughput extends BenchmarkSimpleServletBase {

    //Not instrumented
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public String Instrumentation(StateVariablesInstrumentation state) throws Exception {
        return super.testSimpleRequest(state);
    }

}
