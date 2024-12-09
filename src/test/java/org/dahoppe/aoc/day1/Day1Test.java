package org.dahoppe.aoc.day1;

import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    private static final String EXAMPLE_INPUT = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
            """;
    private static final String PUZZLE_INPUT = Files.read("1.txt");

    @Test
    void testParseExampleInput() {
        assertThat(Day1.parseInput(EXAMPLE_INPUT)).isEqualTo(new Day1.Input(
                List.of(3, 4, 2, 1, 3, 3),
                List.of(4, 3, 5, 3, 9, 3)
        ));
    }

    @Test
    void testParsePuzzleInput() {
        assertThat(Day1.parseInput(PUZZLE_INPUT)).satisfies(input -> {
            assertThat(input.left()).hasSize(1000);
            assertThat(input.right()).hasSize(1000);
        });
    }

    @Test
    void testSolveAExample() {
        assertThat(Day1.solveA(Day1.parseInput(EXAMPLE_INPUT))).isEqualTo(11);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day1.solveA(Day1.parseInput(PUZZLE_INPUT))).isEqualTo(2086478);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day1.solveB(Day1.parseInput(EXAMPLE_INPUT))).isEqualTo(31);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day1.solveB(Day1.parseInput(PUZZLE_INPUT))).isEqualTo(24941624);
    }

}