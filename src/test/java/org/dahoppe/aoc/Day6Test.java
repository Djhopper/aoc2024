package org.dahoppe.aoc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day6Test {

    private static final String EXAMPLE_INPUT = """
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
            """;
    private static final String PUZZLE_INPUT = Utilities.readInput("6.txt");

    @Test
    void testParseExample() {
        assertThat(new Day6.World(EXAMPLE_INPUT)).satisfies(world -> {
            assertThat(world.getHeight()).isEqualTo(10);
            assertThat(world.getWidth()).isEqualTo(10);
        });
    }

    @Test
    void testParsePuzzle() {
        assertThat(new Day6.World(PUZZLE_INPUT)).satisfies(world -> {
            assertThat(world.getHeight()).isEqualTo(130);
            assertThat(world.getWidth()).isEqualTo(130);
        });
    }

    @Test
    void testSolveAExample() {
        assertThat(Day6.solveA(new Day6.World(EXAMPLE_INPUT))).isEqualTo(41);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day6.solveA(new Day6.World(PUZZLE_INPUT))).isEqualTo(4758);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day6.solveB(new Day6.World(EXAMPLE_INPUT))).isEqualTo(6);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day6.solveB(new Day6.World(PUZZLE_INPUT))).isEqualTo(1670);
    }

}