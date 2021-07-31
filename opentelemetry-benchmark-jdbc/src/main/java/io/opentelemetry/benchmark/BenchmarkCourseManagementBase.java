package io.opentelemetry.benchmark;

import io.opentelemetry.benchmark.course.CourseManagementApplication;
import io.opentelemetry.benchmark.course.resources.CourseResource;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javax.ws.rs.core.Response;

public class BenchmarkCourseManagementBase {

    public Response getAllCourses(StateVariables state) {
        return state.course.getAll();
    }

    @State(Scope.Benchmark)
    public static class StateVariables {
        public CourseResource course;
        public ConfigurableApplicationContext c;

        @TearDown(Level.Iteration)
        public void shutdownContext() {
            c.close();
        }

        public void initApplication() {
            c = SpringApplication.run(CourseManagementApplication.class);
            course = c.getBean(CourseResource.class);
        }
    }

    public static class StateVariablesInstrumented extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {
            initApplication();
        }
    }

}


