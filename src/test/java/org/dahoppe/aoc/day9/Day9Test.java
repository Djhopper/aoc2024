package org.dahoppe.aoc.day9;

import org.dahoppe.aoc.day9.Day9A.EmptyBlock;
import org.dahoppe.aoc.day9.Day9A.FileBlock;
import org.dahoppe.aoc.util.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    private static final String EXAMPLE_INPUT = "2333133121414131402";
    private static final String PUZZLE_INPUT = Files.read("9.txt");

    @Test
    void testParseExampleInput() {
        assertThat(Day9A.parseInput(EXAMPLE_INPUT)).isEqualTo(List.of(
                new FileBlock(0),
                new FileBlock(0),
                new EmptyBlock(),
                new EmptyBlock(),
                new EmptyBlock(),
                new FileBlock(1),
                new FileBlock(1),
                new FileBlock(1),
                new EmptyBlock(),
                new EmptyBlock(),
                new EmptyBlock(),
                new FileBlock(2),
                new EmptyBlock(),
                new EmptyBlock(),
                new EmptyBlock(),
                new FileBlock(3),
                new FileBlock(3),
                new FileBlock(3),
                new EmptyBlock(),
                new FileBlock(4),
                new FileBlock(4),
                new EmptyBlock(),
                new FileBlock(5),
                new FileBlock(5),
                new FileBlock(5),
                new FileBlock(5),
                new EmptyBlock(),
                new FileBlock(6),
                new FileBlock(6),
                new FileBlock(6),
                new FileBlock(6),
                new EmptyBlock(),
                new FileBlock(7),
                new FileBlock(7),
                new FileBlock(7),
                new EmptyBlock(),
                new FileBlock(8),
                new FileBlock(8),
                new FileBlock(8),
                new FileBlock(8),
                new FileBlock(9),
                new FileBlock(9)
        ));
    }

    @Test
    void testParsePuzzleInput() {
        assertThat(Day9A.parseInput(PUZZLE_INPUT)).hasSize(95307);
    }

    @Test
    void testSolveAExample() {
        assertThat(Day9A.solveA(Day9A.parseInput(EXAMPLE_INPUT))).isEqualTo(1928);
    }

    @Test
    void testSolveAPuzzle() {
        assertThat(Day9A.solveA(Day9A.parseInput(PUZZLE_INPUT))).isEqualTo(6331212425418L);
    }

    @ParameterizedTest
    @MethodSource
    void testSolveA(String inputString, int expectedAnswer) {
        assertThat(Day9A.solveA(Day9A.parseInput(inputString))).isEqualTo(expectedAnswer);
    }

    public static Stream<Arguments> testSolveA() {
        return Stream.of(
                Arguments.of("1010101010101010101011", 385), // Happy case
                Arguments.of("111111111111111111111", 290) // Happy case with spaces
        );
    }

    @Test
    void testSolveBExample() {
        assertThat(Day9B.solveB(Day9B.parseInput(EXAMPLE_INPUT))).isEqualTo(2858);
    }

    @Test
    void testSolveBPuzzle() {
        assertThat(Day9B.solveB(Day9B.parseInput(PUZZLE_INPUT))).isEqualTo(6363268339304L);
    }

}