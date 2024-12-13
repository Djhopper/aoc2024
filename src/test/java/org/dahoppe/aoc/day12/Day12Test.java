package org.dahoppe.aoc.day12;

import org.dahoppe.aoc.day12.Day12.Fence;
import org.dahoppe.aoc.day12.Day12.Plant;
import org.dahoppe.aoc.day12.Day12.Position;
import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    private static final String EXAMPLE_INPUT = """
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
            """;
    private static final String PUZZLE_INPUT = Files.read("12.txt");

    @Test
    void testParseExample() {
        assertThat(Day12.parseGarden(EXAMPLE_INPUT)).satisfies(garden -> {
            assertThat(garden.plants()).hasSize(100);
            assertThat(garden.width()).isEqualTo(10);
            assertThat(garden.height()).isEqualTo(10);
            assertThat(garden.plants().get(new Position(0, 0))).isEqualTo(new Plant('R', new Position(0, 0)));
            assertThat(garden.adjacentPlants((new Plant('R', new Position(0, 0))))).containsExactlyInAnyOrder(
                    new Plant('R', new Position(1, 0)),
                    new Plant('R', new Position(0, 1))
            );
            assertThat(garden.adjacentFences((new Plant('R', new Position(0, 0))))).containsExactlyInAnyOrder(
                    new Fence(new Position(0, 0), new Position(0, 1)),
                    new Fence(new Position(0, 0), new Position(1, 0))
            );
            assertThat(garden.adjacentFences((new Plant('R', new Position(4, 2))))).containsExactlyInAnyOrder(
                    new Fence(new Position(4, 2), new Position(5, 2)),
                    new Fence(new Position(5, 2), new Position(5, 3)),
                    new Fence(new Position(4, 3), new Position(5, 3))
            );
        });
    }

    @Test
    void testSolveAExample() {
        assertThat(Day12.solveA(Day12.parseGarden(EXAMPLE_INPUT))).isEqualTo(1930);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day12.solveA(Day12.parseGarden(PUZZLE_INPUT))).isEqualTo(1396298);
    }

    @Test
    void testSolveBExample() {
        assertThat(Day12.solveB(Day12.parseGarden(EXAMPLE_INPUT))).isEqualTo(1206);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day12.solveB(Day12.parseGarden(PUZZLE_INPUT))).isEqualTo(853588);
    }

    @Test
    void testSolveBEnclosed() {
        String input = """
                AAAAAA
                AAABBA
                AAABBA
                ABBAAA
                ABBAAA
                AAAAAA
                """;
        assertThat(Day12.solveB(Day12.parseGarden(input))).isEqualTo(368);
    }

}