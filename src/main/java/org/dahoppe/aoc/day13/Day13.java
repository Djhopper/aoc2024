package org.dahoppe.aoc.day13;

import org.dahoppe.aoc.util.Parsing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day13 {

    private static final Logger log = LoggerFactory.getLogger(Day13.class);

    private static final String NEW_LINE_REGEX = "\\r?\\n";
    private static final Pattern INPUT_PATTERN = Pattern.compile(
            "Button A: X\\+(?<ax>[0-9]+), Y\\+(?<ay>[0-9]+)" +
                    NEW_LINE_REGEX +
                    "Button B: X\\+(?<bx>[0-9]+), Y\\+(?<by>[0-9]+)" +
                    NEW_LINE_REGEX +
                    "Prize: X=(?<cx>[0-9]+), Y=(?<cy>[0-9]+)");

    static List<Machine> parseInput(String input) {
        return Parsing.splitOnDoubleNewLine(input)
                .map(section -> {
                    Matcher matcher = INPUT_PATTERN.matcher(section);
                    if (matcher.find()) {
                        return new Machine(
                                new Vec2(matcher.group("ax"), matcher.group("ay")),
                                new Vec2(matcher.group("bx"), matcher.group("by")),
                                new Vec2(matcher.group("cx"), matcher.group("cy")));
                    } else {
                        throw new IllegalStateException();
                    }
                })
                .toList();
    }

    static Optional<Long> tokensToWin(Machine machine) {
        log.info("Machine {}", machine);
        Mat2 matrix = new Mat2(
                machine.buttonA().x(),
                machine.buttonB().x(),
                machine.buttonA().y(),
                machine.buttonB().y()
        );
        long determinant = matrix.determinant();
        if (determinant == 0) {
            log.info("Matrix {} has no inverse", matrix);
            return Optional.empty();
        }

        Mat2 inverseTimesDeterminant = matrix.inverseTimesDeterminant();

        Vec2 solutionTimesDeterminant = inverseTimesDeterminant.mul(machine.prize());

        if (solutionTimesDeterminant.x() % determinant != 0 || solutionTimesDeterminant.y() % determinant != 0) {
            log.info("{} isn't divisible by {}", solutionTimesDeterminant, determinant);
            return Optional.empty();
        }

        Vec2 solution = new Vec2(solutionTimesDeterminant.x() / determinant, solutionTimesDeterminant.y() / determinant);
        log.info("Solution is {}", solution);
        return Optional.of(solution.x() * 3 + solution.y());
    }

    static long solveA(List<Machine> machines) {
        return machines.stream()
                .flatMap(machine -> tokensToWin(machine).stream())
                .mapToLong(i -> i)
                .sum();
    }

    static long solveB(List<Machine> machines) {
        return solveA(machines.stream()
                .map(machine -> new Machine(
                        machine.buttonA(),
                        machine.buttonB(),
                        new Vec2(machine.prize().x() + 10000000000000L, machine.prize().y() + 10000000000000L))
                )
                .toList());
    }

    record Machine(Vec2 buttonA, Vec2 buttonB, Vec2 prize) { }

    record Vec2(long x, long y) {
        Vec2(String x, String y) {
            this(Long.parseLong(x), Long.parseLong(y));
        }
    }

    record Mat2(long a, long b, long c, long d) {
        long determinant() {
            return a * d - b * c;
        }
        Mat2 inverseTimesDeterminant() {
            return new Mat2(d, -b, -c, a );
        }

        Vec2 mul(Vec2 vec) {
            return new Vec2(
                    a * vec.x() + b * vec.y(),
                    c * vec.x() + d * vec.y()
            );
        }
    }

}
