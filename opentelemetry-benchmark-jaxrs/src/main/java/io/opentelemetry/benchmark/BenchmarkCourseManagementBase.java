package io.opentelemetry.benchmark;

import io.opentelemetry.benchmark.course.CourseManagementApplication;
import io.opentelemetry.benchmark.course.resources.CourseResource;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class BenchmarkCourseManagementBase {

    public static final String HOST = "localhost";
    public static final String PORT = "8080";

    public String getAllCourses(StateVariables state)  {
        String r = null;
        try {
            r = Unirest.get("http://" + HOST + ":" + PORT + "/rest/course/").asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return r;
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
            System.setProperty("tracerresolver.disabled", Boolean.TRUE.toString());
            initApplication();
        }
    }

}


