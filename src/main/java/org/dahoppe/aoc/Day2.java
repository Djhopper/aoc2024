package org.dahoppe.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day2 {

    public static List<List<Integer>> readInputFromResource(String filename) {
        String rawInput = Utilities.readInput(filename);

        return Arrays.stream(rawInput.split("\\r\\n"))
                .map(line -> Arrays.stream(line.split(" "))
                        .map(Integer::parseInt)
                        .toList())
                .toList();
    }

    public static boolean isSafe(List<Integer> report) {
        List<Integer> differences = new ArrayList<>();
        for (int i=0; i < report.size() - 1; i++) {
            differences.add(report.get(i + 1) - report.get(i));
        }

        return differences.stream().allMatch(x -> -3 <= x && x <= -1)
            || differences.stream().allMatch(x -> 1 <= x && x <= 3);
    }

    public static boolean isSafeGivenDampener(List<Integer> report) {
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

    public static long solveA(List<List<Integer>> input) {
        return input.stream()
                .filter(Day2::isSafe)
                .count();
    }

    public static long solveB(List<List<Integer>> input) {
        return input.stream()
                .filter(Day2::isSafeGivenDampener)
                .count();
    }

}