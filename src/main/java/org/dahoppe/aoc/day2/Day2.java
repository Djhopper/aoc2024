package org.dahoppe.aoc.day2;

import org.dahoppe.aoc.util.Parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class Day2 {

    static List<List<Integer>> parseInput(String rawInput) {
        return Parsing.splitOnNewLine(rawInput)
                .map(line -> Arrays.stream(line.split(" "))
                        .map(Integer::parseInt)
                        .toList())
                .toList();
    }

    static boolean isSafe(List<Integer> report) {
        List<Integer> differences = new ArrayList<>();
        for (int i=0; i < report.size() - 1; i++) {
            differences.add(report.get(i + 1) - report.get(i));
        }

        return differences.stream().allMatch(x -> -3 <= x && x <= -1)
            || differences.stream().allMatch(x -> 1 <= x && x <= 3);
    }

    static boolean isSafeGivenDampener(List<Integer> report) {
        if (isSafe(report)) {
            return true;
        }

        for (int i=0; i<report.size(); i++) {
            List<Integer> reportWithoutLevel = new ArrayList<>(report);
            reportWithoutLevel.remove(i);
            if (isSafe(reportWithoutLevel)) {
                return true;
            }
        }

        return false;
    }

    static long solveA(List<List<Integer>> input) {
        return input.stream()
                .filter(Day2::isSafe)
                .count();
    }

    static long solveB(List<List<Integer>> input) {
        return input.stream()
                .filter(Day2::isSafeGivenDampener)
                .count();
    }

}