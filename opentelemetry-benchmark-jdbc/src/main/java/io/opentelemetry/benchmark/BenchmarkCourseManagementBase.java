package io.opentelemetry.benchmark;

import io.opentelemetry.benchmark.course.CourseManagementApplication;
import io.opentelemetry.benchmark.course.model.entities.Course;
import io.opentelemetry.benchmark.course.model.services.CourseService;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.List;

public class BenchmarkCourseManagementBase {

    public List<Course> getAllCourses(StateVariables state) {
        return state.service.findAll();
    }

    @State(Scope.Benchmark)
    public static class StateVariables {
        public CourseService service;
        public ConfigurableApplicationContext c;

        @TearDown(Level.Iteration)
        public void shutdownContext() {
            c.close();
        }

        public void initApplication() {
            c = SpringApplication.run(CourseManagementApplication.class);
            service = c.getBean(CourseService.class);
        }
    }

    public static class StateVariablesInstrumented extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {
            initApplication();
        }
    }

}


