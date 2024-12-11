package org.dahoppe.aoc.day11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 {

    private static final Logger log = LoggerFactory.getLogger(Day11.class);

    static long solveA(Set<Long> initialPositions) {
        return calculateSizeAfterBlinks(initialPositions, 25);
    }

    static long solveB(Set<Long> initialPositions) {
        return calculateSizeAfterBlinks(initialPositions, 75);
    }

    static long calculateSizeAfterBlinks(Set<Long> initialStones, int blinks) {
        AdjacencyMatrixAndKey adjacencyMatrixAndKey = findAdjacencies(initialStones);
        List<Long> pathsToPosition = adjacencyMatrixAndKey.key().stream()
                .map(position -> initialStones.contains(position) ? 1L : 0L)
                .toList();
        for (int i=0; i<blinks; i++) {
            log.debug("After {} steps we have {} stones: {}", i,
                    pathsToPosition.stream().mapToLong(l -> l).sum(),
                    toString(pathsToPosition, adjacencyMatrixAndKey.key()));
            pathsToPosition = matrixMultiply(adjacencyMatrixAndKey.adjacencyMatrix(), pathsToPosition);
        }
        log.info("After {} steps we have {} stones: {}", blinks,
                pathsToPosition.stream().mapToLong(l -> l).sum(),
                toString(pathsToPosition, adjacencyMatrixAndKey.key()));
        return pathsToPosition.stream()
                .mapToLong(l -> l)
                .sum();
    }

    private static String toString(List<Long> pathsToPosition, List<Long> key) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<pathsToPosition.size(); i++) {
            if (pathsToPosition.get(i) > 0) {
                builder.append(key.get(i));
                if (pathsToPosition.get(i) > 1) {
                    builder.append("x" + pathsToPosition.get(i));
                }
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    private static List<Long> matrixMultiply(List<List<Long>> matrix, List<Long> vector) {
        List<Long> output = new ArrayList<>(vector.size());
        for (List<Long> row : matrix) {
            long total = 0;
            for (int i=0; i<row.size(); i++) {
                total += row.get(i) * vector.get(i);
            }
           output.add(total);
        }
        return output;
    }

    private static AdjacencyMatrixAndKey findAdjacencies(Set<Long> initialPositions) {
        // Figure out what's adjacent to what
        Map<Long, List<Long>> adjacencies = new HashMap<>();
        Set<Long> todo = initialPositions;
        while (!todo.isEmpty()) {
            todo.forEach(stone -> adjacencies.put(stone, getReachableFrom(stone)));

            todo = adjacencies.entrySet().stream().flatMap(entry -> entry.getValue().stream()).collect(Collectors.toSet());
            todo.removeAll(adjacencies.keySet());
        }

        // Generate key
        List<Long> key = adjacencies.keySet().stream().sorted().toList();

        // Generate adjacency matrix
        List<List<Long>> adjacencyMatrix = new ArrayList<>(key.size());
        for (long x : key) {
            List<Long> row = new ArrayList<>(key.size());
            for (Long y : key) {
                if (adjacencies.get(y).contains(x)) {
                    row.add(adjacencies.get(y).stream().filter(l -> l == x).count()); // not just 1L - account for 9494 -> 94 x 2
                } else {
                    row.add(0L);
                }
            }
            adjacencyMatrix.add(row);
        }

        return new AdjacencyMatrixAndKey(adjacencyMatrix, key);
    }

    record AdjacencyMatrixAndKey(List<List<Long>> adjacencyMatrix, List<Long> key) {}

    private static List<Long> getReachableFrom(long stone) {
        if (stone == 0) {
            return List.of(1L);
        }

        String stoneAsString = String.valueOf(stone);
        if (stoneAsString.length() % 2 == 0) {
            long left = Long.parseLong(stoneAsString.substring(0, stoneAsString.length() / 2));
            long right = Long.parseLong(stoneAsString.substring(stoneAsString.length() / 2));
            return List.of(left, right);
        }

        return List.of(stone * 2024);
    }

}
