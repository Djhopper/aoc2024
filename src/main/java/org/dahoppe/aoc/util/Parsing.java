package org.dahoppe.aoc.util;

import java.util.Arrays;
import java.util.stream.Stream;

public class Parsing {

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
