package org.dahoppe.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day3Test {

    private static final String EXAMPLE_INPUT_A = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
    private static final String EXAMPLE_INPUT_B = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
    private static final List<Day3.Instruction> EXAMPLE_PARSED = List.of(
           new Day3.Mul(2, 4),
           new Day3.Dont(),
           new Day3.Mul(5, 5),
           new Day3.Mul(11, 8),
           new Day3.Do(),
           new Day3.Mul(8, 5)
    );
    private static final String PUZZLE_INPUT = Utilities.readInput("3.txt");

    @Test
    void testParseA() {
        assertThat(Day3.parseInput(EXAMPLE_INPUT_A)).isEqualTo(EXAMPLE_PARSED
                .stream()
                .filter(instruction -> instruction instanceof Day3.Mul)
                .toList());
    }

    @Test
    void testSolveAExample() {
        assertThat(Day3.solveA(EXAMPLE_PARSED)).isEqualTo(161);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day3.solveA(Day3.parseInput(PUZZLE_INPUT))).isEqualTo(157621318);
    }

    @Test
    void testParseB() {
        assertThat(Day3.parseInput(EXAMPLE_INPUT_B)).isEqualTo(EXAMPLE_PARSED);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day3.solveB(EXAMPLE_PARSED)).isEqualTo(48);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day3.solveB(Day3.parseInput(PUZZLE_INPUT))).isEqualTo(79845780);
    }

}