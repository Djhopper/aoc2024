package org.dahoppe.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;
import static org.dahoppe.aoc.Utilities.*;

public class Day5 {

    private static final Logger log = LoggerFactory.getLogger(Day5.class);

    public record Rule(int mustOccurFirst, int mustOccurSecond) {}

    public record Input(List<Rule> rules, List<List<Integer>> updates) {}

    public static Input parseInput(String input) {
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

    public static int solveA(Input input) {
        return input.updates().stream()
                .filter(update -> isCorrectlyOrdered(update, input.rules()))
                .mapToInt(Day5::getMiddleElement)
                .sum();
    }

    private static boolean isCorrectlyOrdered(List<Integer> update, List<Rule> rules) {
        return rules.stream().allMatch(rule -> obeysRule(update, rule));
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

    public static Map<Integer, Set<Integer>> generateOccursBeforeMap(List<Rule> inputRules) {
        Map<Integer, Set<Integer>> occursBeforeMap = inputRules.stream()
                .collect(Collectors.groupingBy(
                        Rule::mustOccurFirst,
                        Collectors.mapping(Rule::mustOccurSecond, Collectors.toSet()))
                );
        AtomicBoolean madeProgress = new AtomicBoolean(true);
        while (madeProgress.get()) {
            madeProgress.set(false);
            Set<Rule> toAdd = new HashSet<>();
            // Apply transitivity - a < b & b < c => a < c
            occursBeforeMap.forEach((a, bs) -> bs
                    .forEach(b ->
                            occursBeforeMap.getOrDefault(b, new HashSet<>()).forEach(c -> {
                                if (!bs.contains(c)) {
                                    madeProgress.set(true);
                                    toAdd.add(new Rule(a, c));
                                }
                            })
                    ));
            toAdd.forEach(rule -> occursBeforeMap.get(rule.mustOccurFirst()).add(rule.mustOccurSecond()));
        }

        return occursBeforeMap;
    }

    private static Comparator<Integer> comparatorFromOccursBeforeMap(Map<Integer, Set<Integer>> occursBeforeMap) {
        return (a, b) -> {
            if (occursBeforeMap.getOrDefault(a, Set.of()).contains(b)) {
                return -1;
            } else if ((occursBeforeMap.getOrDefault(b, Set.of()).contains(a))) {
                return 1;
            } else {
                return 0;
            }
        };
    }

    public static int solveB(Input input) {
        return input.updates().stream()
                .filter(not(update -> isCorrectlyOrdered(update, input.rules())))
                .map(update -> {
                    // Only consider relevant rules -
                    // Can't calculate a single total ordering because the rules only give us total orderings for
                    // subsets of the set of numbers, not for all of them.
                    List<Rule> relevantRules = input.rules().stream()
                            .filter(rule -> update.contains(rule.mustOccurFirst()) && update.contains(rule.mustOccurSecond()))
                            .toList();
                    Comparator<Integer> comparator = comparatorFromOccursBeforeMap(generateOccursBeforeMap(relevantRules));
                    return update.stream().sorted(comparator).toList();
                })
                .peek(update -> {
                    if (!isCorrectlyOrdered(update, input.rules())) {
                        log.info("Uh oh");
                    }
                })
                .mapToInt(Day5::getMiddleElement)
                .sum();
    }

}
