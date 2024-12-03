package org.dahoppe.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Day1 {

    private static final Logger log = LoggerFactory.getLogger(Day1.class);

    public record Input(List<Integer> left, List<Integer> right) {}

    public static Input readInputFromResource(String filename) {
        String rawInput = Utilities.readInput(filename);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        String[] lines = rawInput.split("\\r\\n");
        log.info("Reading {} lines of input", lines.length);

        for (String line: lines) {
            String[] numbers = line.split("   ");
            assert numbers.length == 2;
            left.add(Integer.parseInt(numbers[0]));
            right.add(Integer.parseInt(numbers[1]));
        }

        return new Input(left, right);
    }

    public static int solveA(Input input) {
        List<Integer> sortedLeft = input.left().stream().sorted().toList();
        List<Integer> sortedRight = input.right().stream().sorted().toList();
        int total = 0;
        for (int i=0; i<sortedLeft.size(); i++) {
            total += Math.abs(sortedLeft.get(i) - sortedRight.get(i));
        }
        return total;
    }

    public static long solveB(Input input) {
        Map<Integer, Long> numberOfOccurencesInRightList = input.right().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return input.left().stream()
                .mapToLong(number -> number * numberOfOccurencesInRightList.getOrDefault(number, 0L))
                .sum();
    }

}