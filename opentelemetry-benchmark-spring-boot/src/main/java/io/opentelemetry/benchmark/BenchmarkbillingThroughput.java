package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import io.opentelemetry.benchmark.billingapp.model.Invoice;


public class BenchmarkbillingThroughput extends BillingApplicationBase {

    // @Benchmark
    // @BenchmarkMode(Mode.Throughput)
    // public Invoice noInstrumentation(StateVariablesNoInstrumentation state) {
    //     return doBenchmarkBillingNoInstrumentation(state);
    // }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Invoice OtlpTracer(StateVariablesOtlp state) {
        return doBenchmarkBillingOtlpTracer(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Invoice jaegerTracer(StateVariablesJaeger state) {
        return doBenchmarkBillingJaegerTracer(state);
    } 
}
