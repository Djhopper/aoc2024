package org.dahoppe.aoc.day5;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static org.dahoppe.aoc.util.Parsing.*;

class Day5 {

    record Rule(int mustOccurFirst, int mustOccurSecond) {}

    record Input(List<Rule> rules, List<List<Integer>> updates) {}

    static Input parseInput(String input) {
        List<String> sections = splitOnDoubleNewLine(input).toList();
        assert sections.size() == 2;
        List<Rule> rules = splitOnNewLine(sections.getFirst())
                .map(unparsedRule -> {
                    String[] ints = unparsedRule.split("\\|");
                    assert ints.length == 2;
                    return new Rule(Integer.parseInt(ints[0]), Integer.parseInt(ints[1]));
                })
                .toList();

        List<List<Integer>> updates = splitOnNewLine(sections.getLast())
                .map(update -> split(update, ",")
                        .map(Integer::parseInt)
                        .toList())
                .toList();

        return new Input(rules, updates);
    }

    static int solveA(Input input) {
        return input.updates().stream()
                .filter(isCorrectlyOrdered(input.rules()))
                .mapToInt(Day5::getMiddleElement)
                .sum();
    }

    static int solveB(Input input) {
        Comparator<Integer> comparator = comparatorFromRules(new HashSet<>(input.rules()));
        return input.updates().stream()
                .filter(not(isCorrectlyOrdered(input.rules())))
                .map(update -> update.stream().sorted(comparator).toList())
                .mapToInt(Day5::getMiddleElement)
                .sum();
    }

    private static Predicate<List<Integer>> isCorrectlyOrdered(List<Rule> rules) {
        return update -> rules.stream().allMatch(rule -> obeysRule(update, rule));
    }

    private static boolean obeysRule(List<Integer> update, Rule rule) {
        if (!update.contains(rule.mustOccurFirst()) || !update.contains(rule.mustOccurSecond())) {
            return true;
        }

        return update.indexOf(rule.mustOccurFirst()) < update.indexOf(rule.mustOccurSecond());
    }

    private static int getMiddleElement(List<Integer> update) {
        return update.get((update.size() - 1) / 2);
    }

    private static Comparator<Integer> comparatorFromRules(Set<Rule> rules) {
        return (a, b) -> {
            if (rules.contains(new Rule(a, b))) {
                return -1;
            } else if (rules.contains(new Rule(b, a))) {
                return 1;
            } else {
                return 0;
            }
        };
    }

}
