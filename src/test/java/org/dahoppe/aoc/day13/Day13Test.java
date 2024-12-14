package org.dahoppe.aoc.day13;

import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {

    private static final String EXAMPLE_INPUT = """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400
            
            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176
            
            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450
            
            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
            """;
    private static final String PUZZLE_INPUT = Files.read("13.txt");

    @Test
    void testParseExample() {
        assertThat(Day13.parseInput(EXAMPLE_INPUT)).hasSize(4);
    }

    @Test
    void testSolveAExample() {
        assertThat(Day13.solveA(Day13.parseInput(EXAMPLE_INPUT))).isEqualTo(480);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day13.solveA(Day13.parseInput(PUZZLE_INPUT))).isEqualTo(26005);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day13.solveB(Day13.parseInput(PUZZLE_INPUT))).isEqualTo(105620095782547L);
    }

}