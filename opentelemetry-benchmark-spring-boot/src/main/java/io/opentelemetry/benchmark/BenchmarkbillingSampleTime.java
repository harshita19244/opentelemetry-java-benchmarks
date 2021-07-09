package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import io.opentelemetry.benchmark.billingapp.model.Invoice;


public class BenchmarkbillingSampleTime extends BillingApplicationBase {
    
    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public Invoice noInstrumentation(StateVariablesNoInstrumentation state) {
        return doBenchmarkBillingNoInstrumentation(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public Invoice OtlpTracer(StateVariablesOtlp state) {
        return doBenchmarkBillingOtlpTracer(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public Invoice jaegerTracer(StateVariablesJaeger state) {
        return doBenchmarkBillingJaegerTracer(state);
    } 
}
