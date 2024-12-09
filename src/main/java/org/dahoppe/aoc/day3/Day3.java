package org.dahoppe.aoc.day3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Day3 {

    private static final Logger log = LoggerFactory.getLogger(Day3.class);

    private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((?<left>\\d+),(?<right>\\d+)\\)");
    private static final Pattern DO_PATTERN = Pattern.compile("do\\(\\)");
    private static final Pattern DONT_PATTERN = Pattern.compile("don't\\(\\)");
    private static final Pattern OVERALL_PATTERN = Pattern.compile(
            "(%s)|(%s)|(%s)".formatted(MUL_PATTERN.pattern(), DO_PATTERN.pattern(), DONT_PATTERN.pattern()));

    static List<Instruction> parseInput(String input) {
        List<Instruction> parsed = new ArrayList<>();

        Matcher matcher = OVERALL_PATTERN.matcher(input);
        while (matcher.find()) {
            String matched = matcher.group();
            Matcher mulMatcher = MUL_PATTERN.matcher(matched);
            if (mulMatcher.find()) {
                parsed.add(new Mul(
                        Integer.parseInt(mulMatcher.group("left")),
                        Integer.parseInt(mulMatcher.group("right"))
                ));
            } else if (DO_PATTERN.matcher(matched).find()) {
                parsed.add(new Do());
            } else if (DONT_PATTERN.matcher(matched).find()) {
                parsed.add(new Dont());
            } else {
                log.error("Match {} doesn't match any of the individual patterns", matched);
                throw new RuntimeException("Panic!");
            }

        }

        return parsed;
    }

    static int solveA(List<Instruction> input) {
        return input.stream()
                .filter(instruction -> instruction instanceof Mul)
                .map(instruction -> (Mul) instruction)
                .mapToInt(mul -> mul.left() * mul.right())
                .sum();
    }

    static int solveB(List<Instruction> input) {
        boolean enabled = true;
        int total = 0;
        for (Instruction instruction : input) {
            switch (instruction) {
                case Mul mul:
                    if (enabled) {
                        total += mul.left() * mul.right();
                    }
                    break;
                case Do ignored:
                    enabled = true;
                    break;
                case Dont ignored:
                    enabled = false;
                    break;
            }
        }
        return total;
    }

    static abstract sealed class Instruction permits Mul, Do, Dont  {}

    static final class Mul extends Instruction {

        private final int right;
        private final int left;

        Mul(int left, int right) {
            this.left = left;
            this.right = right;
        }

        int left() {
            return left;
        }

        int right() {
            return right;
        }

        @Override
        public String toString() {
            return "Mul(%d, %d)".formatted(left, right);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Mul mul = (Mul) o;
            return right == mul.right && left == mul.left;
        }

        @Override
        public int hashCode() {
            return Objects.hash(right, left);
        }
    }

    static final class Do extends Instruction {
        @Override
        public String toString() {
            return "Do()";
        }

        @Override
        public boolean equals(Object o) {
            return o != null && getClass() == o.getClass();
        }
    }

    static final class Dont extends Instruction {
        @Override
        public String toString() {
            return "Dont()";
        }

        @Override
        public boolean equals(Object o) {
            return o != null && getClass() == o.getClass();
        }
    }

}