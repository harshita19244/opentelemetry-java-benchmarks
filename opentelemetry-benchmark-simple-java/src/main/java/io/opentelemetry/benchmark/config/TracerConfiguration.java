package io.opentelemetry.benchmark.config;

import io.opentelemetry.api.trace.Tracer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.autoconfigure.OpenTelemetrySdkAutoConfiguration;
import java.util.concurrent.TimeUnit;

public class TracerConfiguration {

    public static Tracer getTracer(String tracername) {
        switch(tracername){
            case "jaeger":
                return initJaeger("localhost", 14250);
               
                
            case "otlp":
                return initOtlp("localhost",9411);
                
                
        }
        return null;  
        
    }

    private static Tracer initJaeger(String jaegerHostName, int jaegerPort) {
        // Create a channel towards Jaeger end point
        ManagedChannel jaegerChannel = ManagedChannelBuilder.forAddress(jaegerHostName, jaegerPort).usePlaintext().build();
        // Export traces to Jaeger
        JaegerGrpcSpanExporter jaegerExporter = 
        JaegerGrpcSpanExporter.builder()
            .setChannel(jaegerChannel)
            .setTimeout(30, TimeUnit.SECONDS)
            .build();
        
        // Set to process the spans by the Jaeger Exporter
        SdkTracerProvider tracerProvider =
        SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(jaegerExporter))
            .setResource(Resource.getDefault())
            .build();
        OpenTelemetrySdk openTelemetry = OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).build();
        
        // it's always a good idea to shut down the SDK cleanly at JVM exit.
    
        Runtime.getRuntime().addShutdownHook(new Thread(tracerProvider::close));
        Tracer tracer = openTelemetry.getTracer("io.opentelemetry.JaegerTracer");
        return tracer;
    }

    private static Tracer initOtlp(String ip, int port){
        OtlpGrpcSpanExporter spanExporter = OtlpGrpcSpanExporter.builder().setTimeout(2, TimeUnit.SECONDS).build();
        BatchSpanProcessor spanProcessor =
            BatchSpanProcessor.builder(spanExporter)
            .setScheduleDelay(100, TimeUnit.MILLISECONDS)
            .build();

        SdkTracerProvider tracerProvider =
            SdkTracerProvider.builder()
            .addSpanProcessor(spanProcessor)
            .setResource(OpenTelemetrySdkAutoConfiguration.getResource())
            .build();
        OpenTelemetrySdk openTelemetry = OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();

        Runtime.getRuntime().addShutdownHook(new Thread(tracerProvider::close));
        Tracer tracer = openTelemetry.getTracer("io.opentelemetry.OtlpTracer");
        return tracer;

    }

    
    
}
