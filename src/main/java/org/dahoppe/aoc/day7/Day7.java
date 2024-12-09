package org.dahoppe.aoc.day7;

import org.dahoppe.aoc.util.Parsing;

import java.util.List;

class Day7 {

    record Equation(long testValue, List<Long> numbers) {
        Equation withoutFirstElement() {
            return new Equation(testValue(), numbers().subList(1, numbers().size()));
        }
        long firstElement() {
            return numbers().getFirst();
        }
    }

    static List<Equation> parseEquations(String input) {
        return Parsing.splitOnNewLine(input)
                .map(line -> {
                    String[] parts = line.split(": ");
                    assert parts.length == 2;
                    long testValue = Long.parseLong(parts[0]);
                    List<Long> numbers = Parsing.split(parts[1], " ").map(Long::parseLong).toList();
                    return new Equation(testValue, numbers);
                })
                .toList();
    }

    static long solveA(List<Equation> equations) {
        return equations.stream()
                .filter(Day7::canBeValid)
                .mapToLong(Equation::testValue)
                .sum();
    }

    private static boolean canBeValid(Equation equation) {
        return canBeValid(equation.withoutFirstElement(), equation.firstElement());
    }

    private static boolean canBeValid(Equation equation, long total) {
        if (equation.numbers().isEmpty()) {
            return total == equation.testValue();
        }

        return canBeValid(equation.withoutFirstElement(), total * equation.firstElement())
                || canBeValid(equation.withoutFirstElement(), total + equation.firstElement());
    }

    static long solveB(List<Equation> equations) {
        return equations.stream()
                .filter(Day7::canBeValidB)
                .mapToLong(Equation::testValue)
                .sum();
    }

    private static boolean canBeValidB(Equation equation) {
        return canBeValidB(equation.withoutFirstElement(), equation.firstElement());
    }

    private static boolean canBeValidB(Equation equation, long total) {
        if (equation.numbers().isEmpty()) {
            return total == equation.testValue();
        }

        return canBeValidB(equation.withoutFirstElement(), total * equation.firstElement())
                || canBeValidB(equation.withoutFirstElement(), total + equation.firstElement())
                || canBeValidB(equation.withoutFirstElement(), concatLongs(total, equation.firstElement()));
    }

    private static long concatLongs(long total, long firstElement) {
        return Long.parseLong("%d%d".formatted(total, firstElement));
    }

}
