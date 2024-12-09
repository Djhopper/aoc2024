package org.dahoppe.aoc.day1;

import org.dahoppe.aoc.util.Parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


class Day1 {

    record Input(List<Integer> left, List<Integer> right) {}

    static Input parseInput(String rawInput) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        Parsing.splitOnNewLine(rawInput)
                .forEach(line -> {
                    String[] numbers = line.split("   ");
                    assert numbers.length == 2;
                    left.add(Integer.parseInt(numbers[0]));
                    right.add(Integer.parseInt(numbers[1]));
                });

        return new Input(left, right);
    }

    static int solveA(Input input) {
        List<Integer> sortedLeft = input.left().stream().sorted().toList();
        List<Integer> sortedRight = input.right().stream().sorted().toList();
        int total = 0;
        for (int i=0; i<sortedLeft.size(); i++) {
            total += Math.abs(sortedLeft.get(i) - sortedRight.get(i));
        }
        return total;
    }

    static long solveB(Input input) {
        Map<Integer, Long> numberOfOccurencesInRightList = input.right().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return input.left().stream()
                .mapToLong(number -> number * numberOfOccurencesInRightList.getOrDefault(number, 0L))
                .sum();
    }

}