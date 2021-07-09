package io.opentelemetry.benchmark.listeners;

import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.benchmark.config.TracerConfiguration;

//import io.opentelemetry.sdk.GlobalTracer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TracingServletContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Tracer tracer = TracerConfiguration.getTracer();
       // GlobalTracer.register(tracer);
    }
}