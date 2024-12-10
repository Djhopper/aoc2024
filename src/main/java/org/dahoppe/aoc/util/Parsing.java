package org.dahoppe.aoc.util;

import java.util.Arrays;
import java.util.List;
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

    public static List<Integer> treatAsUnseparatedIntegers(String input) {
        return input.chars()
                .mapToObj(character -> (char) character)
                .map(character -> Character.toString(character))
                .map(Integer::parseInt)
                .toList();
    }

}
