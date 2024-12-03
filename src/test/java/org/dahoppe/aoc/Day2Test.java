package org.dahoppe.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    private static final List<List<Integer>> EXAMPLE_INPUT = List.of(
            List.of(7, 6, 4, 2, 1),
            List.of(1, 2, 7, 8, 9),
            List.of(9, 7, 6, 2, 1),
            List.of(1, 3, 2, 4, 5),
            List.of(8, 6, 4, 4, 1),
            List.of(1, 3, 6, 7, 9)
    );

    @Test
    void testReadInput() {
        assertThat(Day2.readInputFromResource("2.txt"))
                .hasSize(1000)
                .allSatisfy(report -> assertThat(report).isNotEmpty());
    }

    @Test
    void testSolveAExample() {
        assertThat(Day2.solveA(EXAMPLE_INPUT)).isEqualTo(2);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day2.solveA(Day2.readInputFromResource("2.txt"))).isEqualTo(242);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day2.solveB(EXAMPLE_INPUT)).isEqualTo(4);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day2.solveB(Day2.readInputFromResource("2.txt"))).isEqualTo(311);
    }

}