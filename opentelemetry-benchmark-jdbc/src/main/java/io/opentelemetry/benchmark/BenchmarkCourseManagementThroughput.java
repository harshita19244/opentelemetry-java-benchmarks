package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import javax.ws.rs.core.Response;
public class BenchmarkCourseManagementThroughput extends BenchmarkCourseManagementBase {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Response TracerInstrumentation(StateVariablesInstrumented state) {
        return getAllCourses(state);
    }

}


