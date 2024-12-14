package org.dahoppe.aoc.day12;

import org.dahoppe.aoc.util.Parsing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day12 {

    private static final Logger log = LoggerFactory.getLogger(Day12.class);

    record Garden(Map<Position, Plant> plants, int width, int height) {
        List<Plant> adjacentPlants(Plant plant) {
            return plant.position().adjacentPositions()
                    .stream()
                    .filter(position -> position.isWithinBounds(width, height))
                    .map(plants::get)
                    .toList();
        }
        List<Fence> adjacentFences(Plant plant) {
            return plant.position().adjacentPositions()
                    .stream()
                    .flatMap(position -> {
                        if (position.isWithinBounds(width, height)) {
                            Plant plantOnOtherSideOfFence = plants.get(position);
                            if (plantOnOtherSideOfFence.name() == plant.name()) {
                                return Stream.of();
                            }
                        }
                        Position plantPosition = plant.position();
                        if (plantPosition.x() == position.x()) {
                            int x = position.x();
                            int y = Math.max(plantPosition.y(), position.y());
                            Fence fence = new Fence(new Position(x, y), new Position(x + 1, y));
                            return Stream.of(fence);
                        } else if (plantPosition.y() == position.y()) {
                            int x = Math.max(plantPosition.x(), position.x());
                            int y = position.y();
                            Fence fence = new Fence(new Position(x, y), new Position(x, y + 1));
                            return Stream.of(fence);
                        } else {
                            throw new IllegalStateException();
                        }
                    })
                    .toList();
        }
    }

    record Position(int x, int y) {
        List<Position> adjacentPositions() {
            return List.of(
                    new Position(x-1, y),
                    new Position(x+1, y),
                    new Position(x, y-1),
                    new Position(x, y+1)
            );
        }
        boolean isWithinBounds(int width, int height) {
            return x >= 0 && x < width && y >= 0 && y < height;
        }
    }

    record Plant(char name, Position position) {}

    static Garden parseGarden(String input) {
        log.info("Parsing input of length {}", input.length());
        List<List<Character>> gardenLayout = Parsing.splitOnNewLine(input)
                .map(Parsing::toListOfCharacters)
                .toList();
        Map<Position, Plant> plants = new HashMap<>();
        int height = gardenLayout.size();
        int width = gardenLayout.getFirst().size();
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                Position pos = new Position(x, y);
                plants.put(pos, new Plant(gardenLayout.get(y).get(x), pos));
            }
        }
        log.info("Parsed input to produce a {} x {} garden", width, height);
        return new Garden(plants, width, height);
    }

    private static Set<Plant> findPlantsInRegion(Garden garden, Plant plant) {
        Set<Plant> explored = new HashSet<>();
        List<Plant> nextToExplore = new ArrayList<>();
        nextToExplore.add(plant);
        while (!nextToExplore.isEmpty()) {
            Plant exploring = nextToExplore.removeFirst();
            nextToExplore.addAll(garden.adjacentPlants(exploring)
                    .stream()
                    .filter(maybeInRegion -> maybeInRegion.name() == plant.name())
                    .filter(Predicate.not(explored::contains))
                    .filter(Predicate.not(nextToExplore::contains))
                    .toList());
            explored.add(exploring);
        }
        return explored;
    }

    private static List<Set<Plant>> findRegions(Garden garden) {
        List<Set<Plant>> regions = new ArrayList<>();
        List<Plant> notYetInARegion = new ArrayList<>(garden.plants().values());
        while (!notYetInARegion.isEmpty()) {
            Plant plantInNewRegion = notYetInARegion.getFirst();
            Set<Plant> newRegion = findPlantsInRegion(garden, plantInNewRegion);
            regions.add(newRegion);
            notYetInARegion.removeAll(newRegion);
        }
        return regions;
    }

    private static long getAreaOfRegion(Set<Plant> region) {
        return region.size();
    }

    private static long getPerimeterOfRegion(Garden garden, Set<Plant> region) {
        return getFencesAroundRegion(garden, region).size();
    }

    private static List<Fence> getFencesAroundRegion(Garden garden, Set<Plant> region) {
        return region.stream()
                .flatMap(plantInRegion -> garden.adjacentFences(plantInRegion).stream())
                .toList();
    }

    private static long getNumberOfEdgesOfRegion(Garden garden, Set<Plant> region) {
        List<Fence> fences = getFencesAroundRegion(garden, region);
        Set<Integer> fenceColumns = fences.stream()
                .flatMap(fence -> Stream.of(fence.startPosition().x(), fence.endPosition.x()))
                .collect(Collectors.toSet());
        char plantName = region.stream().findFirst().orElseThrow().name();
        List<Fence> mergedVerticalFences = fenceColumns.stream()
                .flatMap(column -> {
                    List<Fence> columnOfFences = fences.stream()
                            .filter(fence -> fence.inColumn(column))
                            .sorted(Comparator.comparing(fence -> fence.startPosition.y()))
                            .toList();
                    if (columnOfFences.isEmpty()) {
                        return Stream.of();
                    }
                    List<Fence> merged = new ArrayList<>();
                    int start = columnOfFences.getFirst().startPosition().y();
                    int end = columnOfFences.getFirst().endPosition().y();
                    for (Fence fence : columnOfFences.subList(1, columnOfFences.size())) {
                        if (fence.startPosition().y() != end || shouldNotConnectAround(new Position(column, end), plantName, garden)) {
                            merged.add(new Fence(new Position(column, start), new Position(column, end)));
                            start = fence.startPosition().y();
                            end = fence.endPosition().y();
                        } else {
                            end = fence.endPosition().y();
                        }
                    }
                    merged.add(new Fence(new Position(column, start), new Position(column, end)));
                    return merged.stream();
                })
                .toList();
        Set<Integer> fenceRows = fences.stream()
                .flatMap(fence -> Stream.of(fence.startPosition().y(), fence.endPosition.y()))
                .collect(Collectors.toSet());
        List<Fence> mergedHorizontalFences = fenceRows.stream()
                .flatMap(row -> {
                    List<Fence> rowOfFences = fences.stream()
                            .filter(fence -> fence.inRow(row))
                            .sorted(Comparator.comparing(fence -> fence.startPosition.x()))
                            .toList();
                    if (rowOfFences.isEmpty()) {
                        return Stream.of();
                    }
                    List<Fence> merged = new ArrayList<>();
                    int start = rowOfFences.getFirst().startPosition().x();
                    int end = rowOfFences.getFirst().endPosition().x();
                    for (Fence fence : rowOfFences.subList(1, rowOfFences.size())) {
                        if (fence.startPosition().x() != end || shouldNotConnectAround(new Position(end, row), plantName, garden)) {
                            merged.add(new Fence(new Position(start, row), new Position(end, row)));
                            start = fence.startPosition().x();
                            end = fence.endPosition().x();
                        } else {
                            end = fence.endPosition().x();
                        }
                    }
                    merged.add(new Fence(new Position(start, row), new Position(end, row)));
                    return merged.stream();
                })
                .toList();
        return mergedVerticalFences.size() + mergedHorizontalFences.size();
    }

    private static boolean shouldNotConnectAround(Position position, char name, Garden garden) {
        return
                (plantExistsInPositionWithDifferentName(new Position(position.x() - 1, position.y() - 1), name, garden)
                && plantExistsInPositionWithDifferentName(new Position(position.x(), position.y()), name, garden))
                ||
                (plantExistsInPositionWithDifferentName(new Position(position.x() - 1, position.y()), name, garden)
                    && plantExistsInPositionWithDifferentName(new Position(position.x(), position.y() - 1), name, garden));
    }

    private static boolean plantExistsInPositionWithDifferentName(Position position, char name, Garden garden) {
        if (!position.isWithinBounds(garden.width(), garden.height())) {
            return false;
        }
        return garden.plants().get(position).name() != name;
    }

    record Fence(Position startPosition, Position endPosition) {
        boolean inColumn(int column) {
            return startPosition().x() == column && endPosition().x() == column;
        }
        boolean inRow(int row) {
            return startPosition().y() == row && endPosition().y() == row;
        }
    }

    static long solveA(Garden garden) {
        List<Set<Plant>> regions = findRegions(garden);
        return regions.stream()
                .mapToLong(region -> {
                    long area = getAreaOfRegion(region);
                    long perimeter = getPerimeterOfRegion(garden, region);
                    long price = area * perimeter;
                    log.info("A region of {} plants with price {} x {} = {}",
                            region.stream().findFirst().orElseThrow().name(),
                            area,
                            perimeter,
                            price);
                    return price;
                })
                .sum();
    }

    static long solveB(Garden garden) {
        List<Set<Plant>> regions = findRegions(garden);
        return regions.stream()
                .mapToLong(region -> {
                    long area = getAreaOfRegion(region);
                    long numberOfEdges = getNumberOfEdgesOfRegion(garden, region);
                    long price = area * numberOfEdges;
                    log.info("A region of {} plants with price {} x {} = {}",
                            region.stream().findFirst().orElseThrow().name(),
                            area,
                            numberOfEdges,
                            price);
                    return price;
                })
                .sum();
    }

}
