package org.dahoppe.aoc.day2;

import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    private static final String EXAMPLE_INPUT = """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
            """;
    private static final String PUZZLE_INPUT = Files.read("2.txt");

    @Test
    void testParseExampleInput() {
        assertThat(Day2.parseInput(EXAMPLE_INPUT)).isEqualTo(List.of(
                List.of(7, 6, 4, 2, 1),
                List.of(1, 2, 7, 8, 9),
                List.of(9, 7, 6, 2, 1),
                List.of(1, 3, 2, 4, 5),
                List.of(8, 6, 4, 4, 1),
                List.of(1, 3, 6, 7, 9)
        ));
    }

    @Test
    void testParsePuzzleInput() {
        assertThat(Day2.parseInput(PUZZLE_INPUT))
                .hasSize(1000)
                .allSatisfy(report -> assertThat(report).isNotEmpty());
    }

    @Test
    void testSolveAExample() {
        assertThat(Day2.solveA(Day2.parseInput(EXAMPLE_INPUT))).isEqualTo(2);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day2.solveA(Day2.parseInput(PUZZLE_INPUT))).isEqualTo(242);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day2.solveB(Day2.parseInput(EXAMPLE_INPUT))).isEqualTo(4);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day2.solveB(Day2.parseInput(PUZZLE_INPUT))).isEqualTo(311);
    }

}