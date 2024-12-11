package org.dahoppe.aoc.day11;

import org.dahoppe.aoc.util.Files;
import org.dahoppe.aoc.util.Parsing;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    private static final String EXAMPLE_INPUT = "125 17";
    private static final String PUZZLE_INPUT = Files.read("11.txt");

    @Test
    void test() {
        Set<Long> startingPositions = Parsing.treatAsSpaceSeparatedLongs(EXAMPLE_INPUT).collect(Collectors.toSet());
        List<Long> startingStones = Parsing.treatAsSpaceSeparatedLongs(EXAMPLE_INPUT).toList();

        for (int i=0; i<25; i++) {
            long oldAnswer = Day11Old.calculateSizeAfterBlinks(startingStones, i);
            long newAnswer = Day11.calculateSizeAfterBlinks(startingPositions, i);
            assertThat(newAnswer).isEqualTo(oldAnswer);
        }
    }

    @Test
    void testSolveAExample() {
        assertThat(Day11.solveA(Parsing.treatAsSpaceSeparatedLongs(EXAMPLE_INPUT).collect(Collectors.toSet()))).isEqualTo(55312);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day11.solveA(Parsing.treatAsSpaceSeparatedLongs(PUZZLE_INPUT).collect(Collectors.toSet()))).isEqualTo(228668);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day11.solveB(Parsing.treatAsSpaceSeparatedLongs(PUZZLE_INPUT).collect(Collectors.toSet()))).isEqualTo(270673834779359L);
    }

}