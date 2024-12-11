package org.dahoppe.aoc.day11;

import org.dahoppe.aoc.util.Parsing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11Old {

    private static final Logger log = LoggerFactory.getLogger(Day11Old.class);

    static List<Long> parseInput(String input) {
        return Parsing.treatAsSpaceSeparatedLongs(input).toList();
    }

    static long solveA(List<Long> input) {
        return calculateSizeAfterBlinks(input, 25);
    }

    // Way too slow
    static long solveB(List<Long> input) {
        return calculateSizeAfterBlinks(input, 75);
    }

    static long calculateSizeAfterBlinks(List<Long> input, int numberOfBlinks) {
        LinkedList<Long> stones = new LinkedList<>(input);
        IntStream.range(0, numberOfBlinks).forEach(i -> {
            log.info("Executing blink {} on {} stones", i + 1, stones.size());
            blink(stones);
        });
        log.info("After {} steps:               {}", numberOfBlinks, stones.stream()
                .sorted()
                .collect(Collectors.groupingBy(stone -> stone, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getValue() == 1 ? entry.getKey().toString() : entry.getKey() + "x" + entry.getValue())
                .collect(Collectors.joining(" ")));
        return stones.size();
    }

    private static void blink(LinkedList<Long> stones) {
        ListIterator<Long> iterator = stones.listIterator(stones.size());
        while (iterator.hasPrevious()) {
            long currentStone = iterator.previous();
            if (currentStone == 0) {
                iterator.set(1L);
                continue;
            }

            String stoneAsString = String.valueOf(currentStone);
            if (stoneAsString.length() % 2 == 0) {
                long left = Long.parseLong(stoneAsString.substring(0, stoneAsString.length() / 2));
                long right = Long.parseLong(stoneAsString.substring(stoneAsString.length() / 2));
                iterator.set(left);
                iterator.add(right);
                iterator.previous();
                continue;
            }

            iterator.set(currentStone * 2024);
        }
    }

}
