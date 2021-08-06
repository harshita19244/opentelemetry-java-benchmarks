package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import io.opentelemetry.benchmark.course.model.entities.Course;
import java.util.List;


public class BenchmarkCourseManagementThroughput extends BenchmarkCourseManagementBase {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public List<Course> TracerInstrumentation(StateVariablesInstrumented state) {
        return getAllCourses(state);
    }

}


