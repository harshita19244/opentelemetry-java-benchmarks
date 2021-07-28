package io.opentelemetry.benchmark.config;

import io.opentelemetry.benchmark.course.resources.CourseResource;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/resource")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(CourseResource.class);
        register(LoggingFeature.class);
    }
}
