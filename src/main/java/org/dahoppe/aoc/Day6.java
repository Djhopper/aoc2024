package org.dahoppe.aoc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day6 {

    public static int solveA(World world) {
        return traverse(world).visited().stream()
                .map(PositionAndDirection::position)
                .collect(Collectors.toSet())
                .size();
    }

    public static int solveB(World world) {
        int total = 0;
        for (Position position : world.allPositions().toList()) {
            if (world.get(position) instanceof Empty) {
                world.setSpace(position, new Obstacle());
                if (traverse(world).foundLoop()) {
                    total += 1;
                }
                world.setSpace(position, new Empty());
            }
        }
        return total;
    }

    public static TraversalResult traverse(World world) {
        PositionAndDirection guardStartPositionAndDirection = world.getGuardPositionAndDirection();

        Position guardPosition = guardStartPositionAndDirection.position();
        Direction guardDirection = guardStartPositionAndDirection.direction();

        Set<PositionAndDirection> visited = new HashSet<>(List.of(guardStartPositionAndDirection));
        boolean foundLoop = false;
        while (true) {
            Position potentialNewGuardPosition = Position.move(guardPosition, guardDirection);

            if (world.isOutOfBounds(potentialNewGuardPosition)) {
                break;
            }

            Cell cell = world.get(potentialNewGuardPosition);
            if (cell instanceof Obstacle) {
                guardDirection = Direction.rotated90(guardDirection);
            } else {
                guardPosition = potentialNewGuardPosition;
            }

            PositionAndDirection newPosition = new PositionAndDirection(guardPosition, guardDirection);
            if (!visited.add(newPosition)) {
                foundLoop = true;
                break;
            }
        }

        return new TraversalResult(visited, foundLoop);
    }

    public record TraversalResult(Set<PositionAndDirection> visited, boolean foundLoop) {}

    public static class World {
        List<List<Cell>> cells;

        public World(String input) {
            this.cells = Utilities.splitOnNewLine(input)
                    .map(line -> line.chars()
                            .mapToObj(c -> Cell.fromCharacter((char) c))
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        }

        public PositionAndDirection getGuardPositionAndDirection() {
            return allPositions()
                    .flatMap(position -> {
                        if (get(position) instanceof Guard guard) {
                            return Stream.of(new PositionAndDirection(position, guard.direction()));
                        }
                        return Stream.of();
                    })
                    .findFirst()
                    .orElseThrow();
        }

        public boolean isOutOfBounds(Position position) {
            return position.x() < 0 || position.x() >= getWidth() || position.y() < 0 || position.y() >= getHeight();
        }

        public Cell get(Position position) {
            return cells.get(position.y()).get(position.x());
        }

        public void setSpace(Position position, Cell cell) {
            cells.get(position.y()).set(position.x(), cell);
        }

        public Stream<Position> allPositions() {
            return IntStream.range(0, getHeight()).boxed()
                    .flatMap(i -> IntStream.range(0, getWidth())
                            .mapToObj(j -> new Position(i, j)));
        }

        public int getHeight() {
            return cells.size();
        }

        public int getWidth() {
            return cells.getFirst().size();
        }
    }

    public record Position(int x, int y) {
        public static Position move(Position position, Direction direction) {
            return new Position(position.x() + direction.getXDir(), position.y() + direction.getYDir());
        }
    }

    public record PositionAndDirection(Position position, Direction direction) {}

    public abstract sealed static class Cell permits Obstacle, Empty, Guard {
        public static Cell fromCharacter(char character) {
            return switch (character) {
                case '.' -> new Empty();
                case '#' -> new Obstacle();
                case '<' -> new Guard(Direction.LEFT);
                case '>' -> new Guard(Direction.RIGHT);
                case '^' -> new Guard(Direction.UP);
                case 'v' -> new Guard(Direction.DOWN);
                default -> throw new IllegalArgumentException("Character %s not known".formatted(character));
            };
        }
    }

    public static final class Obstacle extends Cell {

    }

    public static final class Empty extends Cell {

    }

    public static final class Guard extends Cell {
        private final Direction direction;

        public Guard(Direction direction) {
            this.direction = direction;
        }

        public Direction direction() {
            return direction;
        }
    }

    public enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        private final int xDir;
        private final int yDir;

        public int getXDir() {
            return xDir;
        }

        public int getYDir() {
            return yDir;
        }

        public static Direction rotated90(Direction direction) {
            return switch (direction) {
                case UP -> RIGHT;
                case RIGHT -> DOWN;
                case DOWN -> LEFT;
                case LEFT -> UP;
            };
        }

        Direction(int xDir, int yDir) {
            this.xDir = xDir;
            this.yDir = yDir;
        }
    }

}
