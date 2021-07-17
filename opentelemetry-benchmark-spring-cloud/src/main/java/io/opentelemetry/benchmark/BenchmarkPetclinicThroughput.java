package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import io.opentelemetry.benchmark.petclinic.owner.Owner;

public class BenchmarkPetclinicThroughput extends BenchmarkPetclinicBase {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Owner noInstrumentation(StateVariablesNoInstrumentation state) {
        return findPetOwnerById(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Owner noopTracer(StateVariablesNoopTracer state) {
        return findPetOwnerById(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Owner jaegerTracer(StateVariablesJaeger state) {
        return findPetOwnerById(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Owner OtlpTracer(StateVariablesOtlp state) {
        return findPetOwnerById(state);
    }



}


