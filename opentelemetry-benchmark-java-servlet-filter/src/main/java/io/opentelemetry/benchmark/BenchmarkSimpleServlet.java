package io.opentelemetry.benchmark;

import io.opentelemetry.benchmark.util.ResultsFileNameBuilder;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;

import java.io.File;
import java.io.IOException;

public class BenchmarkSimpleServlet extends BenchmarkSimpleServletBase{
    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testABSimpleRequest(StateVariablesInstrumentation state) throws Exception {
        String outputFile = ResultsFileNameBuilder.buildResultsFileName("ab-instrumented-", ResultFormatType.CSV);
        runABTest(outputFile);
    }

    private void runABTest(String outputFile) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(
                "ab",
                "-g",
                outputFile,
                "-l",
                "-r",
                "-n",
                "50000",
                "-c",
                "10",
                "-k",
                "-H",
                "\"Accept-Encoding: gzip, deflate\"",
                "http://127.0.0.1:9090/jmh-examples-java-servlet-filter");

        pb.directory(new File("."));
        Process p = pb.start();
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
