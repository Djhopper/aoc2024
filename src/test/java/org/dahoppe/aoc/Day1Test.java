package org.dahoppe.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    private static final Day1.Input EXAMPLE_INPUT = new Day1.Input(
            List.of(3, 4, 2, 1, 3, 3),
            List.of(4, 3, 5, 3, 9, 3)
    );

    @Test
    void testReadInput() throws IOException {
        assertThat(Day1.readInputFromResource("1.txt")).satisfies(input -> {
            assertThat(input.left()).hasSize(1000);
            assertThat(input.right()).hasSize(1000);
        });
    }

    @Test
    void testSolveAExample() {
        assertThat(Day1.solveA(EXAMPLE_INPUT)).isEqualTo(11);
    }

    @Test
    void testSolveAPuzzle() throws IOException {
        assertThat(Day1.solveA(Day1.readInputFromResource("1.txt"))).isEqualTo(2086478);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day1.solveB(EXAMPLE_INPUT)).isEqualTo(31);
    }

    @Test
    void testSolveBPuzzle() throws IOException {
        assertThat(Day1.solveB(Day1.readInputFromResource("1.txt"))).isEqualTo(24941624);
    }

}