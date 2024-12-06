package org.dahoppe.aoc;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day5Test {

    private static final String EXAMPLE_INPUT = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13
            
            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
            """;
    private static final String PUZZLE_INPUT = Utilities.readInput("5.txt");
    private static final Logger log = LoggerFactory.getLogger(Day5Test.class);

    @Test
    void testParseExample() {
        assertThat(Day5.parseInput(EXAMPLE_INPUT)).satisfies(input -> {
            assertThat(input.rules()).hasSize(21);
            assertThat(input.updates()).hasSize(6);
        });
    }

    @Test
    void testParsePuzzle() {
        assertThat(Day5.parseInput(PUZZLE_INPUT)).satisfies(input -> {
            assertThat(input.rules()).hasSize(1176);
            assertThat(input.updates()).hasSize(174);
        });
    }

    @Test
    void testSolveAExample() {
        assertThat(Day5.solveA(Day5.parseInput(EXAMPLE_INPUT))).isEqualTo(143);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day5.solveA(Day5.parseInput(PUZZLE_INPUT))).isEqualTo(5166);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day5.solveB(Day5.parseInput(EXAMPLE_INPUT))).isEqualTo(123);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day5.solveB(Day5.parseInput(PUZZLE_INPUT))).isEqualTo(4679);
    }

}