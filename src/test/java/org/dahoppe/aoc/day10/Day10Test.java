package org.dahoppe.aoc.day10;

import org.dahoppe.aoc.day10.Day10.Node;
import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    private static final String EXAMPLE_INPUT = """
            89010123
            78121874
            87430965
            96549874
            45678903
            32019012
            01329801
            10456732
            """;
    private static final String PUZZLE_INPUT = Files.read("10.txt");

    @Test
    void testParseExample() {
        assertThat(Day10.constructTrailheadList(EXAMPLE_INPUT))
                .hasSize(9)
                .allSatisfy(Node::isTrailhead);
    }

    @Test
    void testParsePuzzle() {
        assertThat(Day10.constructTrailheadList(PUZZLE_INPUT)).hasSize(296);
    }

    @Test
    void testSolveAExample() {
        assertThat(Day10.solveA(Day10.constructTrailheadList(EXAMPLE_INPUT))).isEqualTo(36);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day10.solveA(Day10.constructTrailheadList(PUZZLE_INPUT))).isEqualTo(694);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day10.solveB(Day10.constructTrailheadList(EXAMPLE_INPUT))).isEqualTo(81);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day10.solveB(Day10.constructTrailheadList(PUZZLE_INPUT))).isEqualTo(1497);
    }

}