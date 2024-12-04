package org.dahoppe.aoc;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day4Test {

    private static final String EXAMPLE_INPUT = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
            """;
    private static final String PUZZLE_INPUT = Utilities.readInput("4.txt");

    @Test
    void testParseExample() {
        assertThat(Day4.parseInput(EXAMPLE_INPUT))
                .hasSize(10)
                .allSatisfy(row -> assertThat(row).hasSize(10));
    }

    @Test
    void testParsePuzzle() {
        assertThat(Day4.parseInput(PUZZLE_INPUT))
                .hasSize(140)
                .allSatisfy(row -> assertThat(row).hasSize(140));
    }

    @Test
    void testSolveAExample() {
        assertThat(Day4.solveA(Day4.parseInput(EXAMPLE_INPUT))).isEqualTo(18);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day4.solveA(Day4.parseInput(PUZZLE_INPUT))).isEqualTo(2562);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day4.solveB(Day4.parseInput(EXAMPLE_INPUT))).isEqualTo(9);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day4.solveB(Day4.parseInput(PUZZLE_INPUT))).isEqualTo(1902);
    }


}