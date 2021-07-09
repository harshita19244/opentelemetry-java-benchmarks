package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

public class BenchmarkSimpleSampleTimewithMetrics extends BenchmarkSimpleServletBase{
    
     //Not instrumented
     @Benchmark
     @BenchmarkMode(Mode.SampleTime)
     public String noInstrumentation(StateVariablesNoInstrumentation state)
             throws Exception {
         return super.testSimpleRequest(state);
     }
 
     //Jaeger
     @Benchmark
     @BenchmarkMode(Mode.SampleTime)
     public String jaegerTracer(StateVariablesJaegerWithMetricFilters state)
             throws Exception {
         return super.testSimpleRequest(state);
     }
 
     //OTLP
     @Benchmark
     @BenchmarkMode(Mode.SampleTime)
     public String OtlpTracer(StateVariablesOtlpWithMetricFilters state)
             throws Exception {
         return super.testSimpleRequest(state);
     }
}
