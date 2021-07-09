package io.opentelemetry.benchmark.util;

import org.openjdk.jmh.results.format.ResultFormatType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultsFileNameBuilder {

    public static String buildResultsFileName(String resultFilePrefix,
                                              ResultFormatType resultType) {
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
