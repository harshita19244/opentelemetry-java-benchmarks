package io.opentelemetry.benchmark;

import com.mashape.unirest.http.Unirest;
import io.opentelemetry.benchmark.servlets.ServletExample;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.*;
import org.openjdk.jmh.annotations.*;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

public class BenchmarkSimpleServletBase {

    public static final int PORT = 9090;
    public static final String HOST = "localhost";

    private static Undertow buildUndertowServer(Boolean tracerFilter, Boolean metricFilters) throws ServletException {

        DeploymentInfo deploymentInfo = Servlets.deployment()
                .setClassLoader(BenchmarkSimpleServletBase.class.getClassLoader())
                .setContextPath("/ui").setDeploymentName("jmh-examples-java-servlet-filter.war")
                .addServlet(createServletInfo("/*", "ServletExample", ServletExample.class));


        DeploymentManager manager = Servlets.defaultContainer().addDeployment(deploymentInfo);
        manager.deploy();

        PathHandler path = Handlers.path(Handlers.resource(
                new ClassPathResourceManager(BenchmarkSimpleServletBase.class.getClassLoader(), "webapp")))
                .addExactPath("/", Handlers.redirect("/jmh-examples-java-servlet-filter"))
                .addPrefixPath("/jmh-examples-java-servlet-filter", manager.start());

        return Undertow.builder().addHttpListener(PORT, HOST).setHandler(path).build();
    }

    private static ServletInfo createServletInfo(String mapping, String servletName, Class<? extends Servlet> servlet) {
        ServletInfo servletInfo = Servlets.servlet(servletName, servlet)
                .setAsyncSupported(true)
                .setLoadOnStartup(Integer.valueOf(1))
                .addMapping(mapping);

        return servletInfo;
    }

    public String testSimpleRequest(StateVariablesBase state)
            throws Exception {
        String r = Unirest.get("http://" + HOST + ":" + PORT + "/").asString().getBody();
        return r;
    }

    @State(Scope.Thread)
    public static class StateVariablesBase {
        protected Undertow server;

        @TearDown(Level.Trial)
        public void doTearDown() {
            this.server.stop();
        }
    }

    @State(Scope.Thread)
    public static class StateVariablesInstrumentation extends StateVariablesBase {

        @Setup(Level.Trial)
        public void setup() {
            try {
                server = buildUndertowServer(Boolean.TRUE, Boolean.TRUE);
                server.start();

            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
