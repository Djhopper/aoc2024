package org.dahoppe.aoc.day8;

import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Day8Test {

    private static final String EXAMPLE_INPUT = """
            ............
            ........0...
            .....0......
            .......0....
            ....0.......
            ......A.....
            ............
            ............
            ........A...
            .........A..
            ............
            ............
            """;
    private static final String PUZZLE_INPUT = Files.read("8.txt");

    @Test
    void testParseExample() {
        assertThat(Day8.parseInput(EXAMPLE_INPUT)).isEqualTo(new Day8.Input(
                12,
                12,
                Map.of(
                        '0',
                        Set.of(
                                new Day8.Position(8, 1),
                                new Day8.Position(5, 2),
                                new Day8.Position(7, 3),
                                new Day8.Position(4, 4)
                        ),
                        'A',
                        Set.of(
                                new Day8.Position(6, 5),
                                new Day8.Position(8, 8),
                                new Day8.Position(9, 9)
                        )
                )
        ));
    }

    @Test
    void testParsePuzzle() {
        assertThat(Day8.parseInput(PUZZLE_INPUT)).satisfies(input -> {
            assertThat(input.width()).isEqualTo(50);
            assertThat(input.height()).isEqualTo(50);
            assertThat(input.positions()).isNotNull().isNotEmpty();
        });
    }

    @Test
    void testSolveAExample() {
        assertThat(Day8.solveA(Day8.parseInput(EXAMPLE_INPUT))).isEqualTo(14);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day8.solveA(Day8.parseInput(PUZZLE_INPUT))).isEqualTo(376);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day8.solveB(Day8.parseInput(EXAMPLE_INPUT))).isEqualTo(34);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day8.solveB(Day8.parseInput(PUZZLE_INPUT))).isEqualTo(1352);
    }

}