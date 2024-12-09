package org.dahoppe.aoc.day7;

import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day7Test {

    private static final String EXAMPLE_INPUT = """
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
            """;
    private static final String PUZZLE_INPUT = Files.read("7.txt");

    @Test
    void testParseExample() {
        assertThat(Day7.parseEquations(EXAMPLE_INPUT)).hasSize(9);
    }

    @Test
    void testParsePuzzle() {
        assertThat(Day7.parseEquations(PUZZLE_INPUT)).hasSize(850);
    }

    @Test
    void testSolveAExample() {
        assertThat(Day7.solveA(Day7.parseEquations(EXAMPLE_INPUT))).isEqualTo(3749);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day7.solveA(Day7.parseEquations(PUZZLE_INPUT))).isEqualTo(5837374519342L);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day7.solveB(Day7.parseEquations(EXAMPLE_INPUT))).isEqualTo(11387);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day7.solveB(Day7.parseEquations(PUZZLE_INPUT))).isEqualTo(492383931650959L);
    }


}