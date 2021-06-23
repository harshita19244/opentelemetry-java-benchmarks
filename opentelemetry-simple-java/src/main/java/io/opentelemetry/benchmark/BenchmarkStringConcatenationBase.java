package io.opentelemetry.benchmark;


import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.Span;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import io.opentelemetry.benchmark.config.TracerConfiguration;
public class BenchmarkStringConcatenationBase {

    public static final String TRACER = "tracer";

    /*
    Sometimes you way want to initialize some variables that your benchmark code needs,
    but which you do not want to be part of the code your benchmark measures.
    Such variables are called "state" variables.
    */

    @State(Scope.Thread)
    public static class StateVariables {
        String a = "Hello ";
        String b = "world";
        int i = 0; // the iteration number

        //initialising the Tracers
        //https://github.com/jaegertracing/jaeger-client-java/tree/master/jaeger-client
        Tracer jaegertrace = TracerConfiguration.getTracer("jaeger");
        
        //Opentelemetry Protocol
        Tracer otlptrace = TracerConfiguration.getTracer("otlp");

        
        //Tells JMH that this method should be called to clean up ("tear down") the state
        //object after the benchmark has been execute
        @TearDown(Level.Iteration)
        public void doTearDown() {
            System.out.println("Do TearDown");
        }

        
    }

    public String doJaegerTracer(StateVariables state){
        Span span = state.jaegertrace.spanBuilder("TestStringConcatenationStringBuilder").startSpan();
        span.setAttribute("Tag",getLogMessage(state));
        String message = getLogMessage(state);
        return message;
    }

    public String doOtlpTracer(StateVariables state){
        Span span = state.otlptrace.spanBuilder("TestStringConcatenationStringBuilder").startSpan();
        span.setAttribute("Tag", getLogMessage(state));
        String message = getLogMessage(state);
        return message;
    }

    public String getLogMessage(StateVariables state) {
        state.i++;
        return new StringBuilder().append(state.a).append(state.b).append(state.i).toString();
    }

    public String doNoInstrumentation(StateVariables state) {
        return getLogMessage(state);
    }
    
}
