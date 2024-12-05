package org.dahoppe.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);

    public static String readInput(String filename) {
        String path = "/input/" + filename;
        try {
            return new String(Utilities.class.getResourceAsStream(path).readAllBytes());
        } catch (IOException e) {
            log.info("Failed to read input from {}", path, e);
            throw new RuntimeException(e);
        }
    }

    public static Stream<String> splitOnNewLine(String stringToSplit) {
        return split(stringToSplit, "\\r?\\n");
    }

    public static Stream<String> splitOnDoubleNewLine(String stringToSplit) {
        return split(stringToSplit, "(\\r?\\n){2}");
    }

    public static Stream<String> split(String stringToSplit, String regexToSplitOn) {
        return Arrays.stream(stringToSplit.split(regexToSplitOn));
    }

}
