package org.dahoppe.aoc;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 {

    public record Input(int width, int height, Map<Character, Set<Position>> positions) {
        public boolean isWithinBounds(Position position) {
            return position.x() >= 0 && position.x() < width() && position.y() >= 0 && position.y() < height();
        }
    }

    public record Position(int x, int y) {
        public Position plus(Position position) {
            return new Position(x + position.x(), y + position.y());
        }
        public Position minus(Position position) {
            return new Position(x - position.x(), y - position.y());
        }
        public Position normalisedByGcd() {
            int gcd = gcd(x, y);
            return new Position(x / gcd, y / gcd);
        }
        private static int gcd(int x, int y) { // https://stackoverflow.com/a/40531215
            return (y == 0) ? x : gcd(y, x % y);
        }
    }

    public static Input parseInput(String input) {
        List<String> lines = Utilities.splitOnNewLine(input).toList();
        int width = lines.getFirst().length();
        int height = lines.size();
        Map<Character, Set<Position>> positions = new HashMap<>();
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                char character = lines.get(y).charAt(x);
                if (character != '.') {
                    positions.computeIfAbsent(character, ignored -> new HashSet<>()).add(new Position(x, y));
                }
            }
        }
        return new Input(width, height, positions);
    }

    public static int solveA(Input input) {
        return input.positions().keySet().stream()
                .flatMap(character -> antinodes(input, character).stream())
                .collect(Collectors.toSet())
                .size();
    }

    private static Set<Position> antinodes(Input input, char character) {
        Set<Position> positions = input.positions.get(character);
        Set<Position> potentialAntinodes = new HashSet<>();
        for (Position positionA : positions) {
            for (Position positionB : positions) {
                if (positionA.equals(positionB)) {
                    continue;
                }
                Position difference = positionB.minus(positionA);
                potentialAntinodes.add(positionB.plus(difference));
                potentialAntinodes.add(positionA.minus(difference));
            }
        }
        return potentialAntinodes.stream()
                .filter(input::isWithinBounds)
                .collect(Collectors.toSet());
    }

    public static int solveB(Input input) {
        return input.positions().keySet().stream()
                .flatMap(character -> antinodesB(input, character).stream())
                .collect(Collectors.toSet())
                .size();
    }

    private static Set<Position> antinodesB(Input input, char character) {
        Set<Position> positions = input.positions.get(character);
        Set<Position> potentialAntinodes = new HashSet<>();
        for (Position positionA : positions) {
            for (Position positionB : positions) {
                if (positionA.equals(positionB)) {
                    continue;
                }
                Position difference = positionB.minus(positionA).normalisedByGcd();
                Position oneEnd = positionA;
                while (true) {
                    Position next = oneEnd.minus(difference);
                    if (!input.isWithinBounds(next)) {
                        break;
                    }
                    oneEnd = next;
                }

                Position potentialAntinode = oneEnd;
                while (input.isWithinBounds(potentialAntinode)) {
                    potentialAntinodes.add(potentialAntinode);
                    potentialAntinode = potentialAntinode.plus(difference);
                }
            }
        }
        return potentialAntinodes.stream()
                .filter(input::isWithinBounds)
                .collect(Collectors.toSet());
    }

}
