package io.opentelemetry.benchmark.main;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {

        ResultFormatType resultsFileOutputType = ResultFormatType.JSON;
        String resultFilePrefix = "jmh-";

        Options opt = new OptionsBuilder()
                .include(".*Benchmark")
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .threads(1)
                .resultFormat(resultsFileOutputType)
                .result(buildResultsFileName(resultFilePrefix, resultsFileOutputType))
                .jvmArgs("-server", "-Xms2048m", "-Xmx2048m")
                .build();
        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }

    private static String buildResultsFileName(String resultFilePrefix, ResultFormatType resultType) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

        String suffix;
        switch (resultType) {
            case CSV:
                suffix = ".csv";
                break;
            case SCSV:
                // Semi-colon separated values
                suffix = ".scsv";
                break;
            case LATEX:
                suffix = ".tex";
                break;
            case JSON:
            default:
                suffix = ".json";
                break;

        }

        return String.format("results/%s%s%s", resultFilePrefix, date.format(formatter), suffix);
    }
}
