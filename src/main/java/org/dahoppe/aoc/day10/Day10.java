package org.dahoppe.aoc.day10;

import org.dahoppe.aoc.util.Parsing;

import java.util.*;

class Day10 {

    record Node(int score, int x, int y, List<Node> canReach) {
        boolean isTrailhead() {
            return score() == 0;
        }
        boolean isPeak() {
            return score() == 9;
        }
    }

    static int solveA(List<Node> trailheads) {
        return trailheads.stream()
                .map(Day10::findPeaksReachableFrom)
                .mapToInt(Set::size)
                .sum();
    }

    private static Set<Node> findPeaksReachableFrom(Node node) {
        if (node.isPeak()) {
            return Set.of(node);
        }

        if (node.canReach().isEmpty()) {
            return Set.of();
        }

        return node.canReach().stream()
                .map(Day10::findPeaksReachableFrom)
                .reduce(new HashSet<>(), (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                });
    }

    static int solveB(List<Node> trailheads) {
        return trailheads.stream()
                .mapToInt(Day10::numberOfRoutesFrom)
                .sum();
    }

    private static int numberOfRoutesFrom(Node node) {
        if (node.isPeak()) {
            return 1;
        }

        if (node.canReach().isEmpty()) {
            return 0;
        }

        return node.canReach().stream()
                .mapToInt(Day10::numberOfRoutesFrom)
                .sum();
    }

    static List<Node> constructTrailheadList(String input) {
        List<List<Integer>> list = Parsing.splitOnNewLine(input)
                .map(Parsing::treatAsUnseparatedIntegers)
                .toList();
        int height = list.size();
        int width = list.getFirst().size();
        List<List<Node>> nodes = new ArrayList<>();
        for (int y=0; y<height; y++) {
            List<Node> row = new ArrayList<>();
            for (int x=0; x<width; x++) {
                row.add(new Node(list.get(y).get(x), x, y, new ArrayList<>()));
            }
            nodes.add(row);
        }

        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                Node node = nodes.get(y).get(x);
                node.canReach().addAll(getAdjacentNodes(nodes, x, y, width, height)
                        .stream()
                        .filter(adjactentNode -> adjactentNode.score() == node.score() + 1)
                        .toList());
            }
        }

        return nodes.stream()
                .flatMap(Collection::stream)
                .filter(Node::isTrailhead)
                .toList();
    }

    private static List<Node> getAdjacentNodes(List<List<Node>> nodes, int x, int y, int width, int height) {
        List<Node> result = new ArrayList<>();
        if (x - 1 >= 0) {
            result.add(nodes.get(y).get(x-1));
        }
        if (x + 1 < width) {
            result.add(nodes.get(y).get(x+1));
        }
        if (y - 1 >= 0) {
            result.add(nodes.get(y - 1).get(x));
        }
        if (y + 1 < height) {
            result.add(nodes.get(y + 1).get(x));
        }
        return result;
    }

}
