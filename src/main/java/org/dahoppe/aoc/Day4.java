package org.dahoppe.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day4 {

    private static final String TARGET_STRING = "XMAS";

    public static List<List<Character>> parseInput(String input) {
        return Arrays.stream(input.split("\\s+"))
                .map(Day4::toCharList)
                .toList();
    }

    public static int solveA(List<List<Character>> input) {
        final int width = input.size();
        final int height = input.getFirst().size();
        int total = 0;
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                for (int dirX : List.of(-1, 0, 1)) {
                    for (int dirY : List.of(-1, 0, 1)) {
                        if (dirX == 0 && dirY == 0) {
                            continue;
                        }
                        total += testFromPointInDirection(input, x, y, dirX, dirY) ? 1 : 0;
                    }
                }
            }
        }
        return total;
    }

    private static boolean testFromPointInDirection(List<List<Character>> input, int x, int y, int dirX, int dirY) {
        if (       x + (TARGET_STRING.length() - 1) * dirX < 0
                || x + (TARGET_STRING.length() - 1) * dirX >= input.size()
                || y + (TARGET_STRING.length() - 1) * dirY < 0
                || y + (TARGET_STRING.length() - 1) * dirY >= input.getFirst().size()) {
            return false;
        }

        List<Character> potentialString = new ArrayList<>();
        for (int i = 0; i<TARGET_STRING.length(); i++) {
            potentialString.add(input.get(x + i * dirX).get(y + i * dirY));
        }
        return potentialString.equals(toCharList(TARGET_STRING));
    }

    public static int solveB(List<List<Character>> input) {
        final int width = input.size();
        final int height = input.getFirst().size();
        int total = 0;
        for (int x=1; x<width-1; x++) {
            for (int y=1; y<height-1; y++) {
                if (input.get(x).get(y) == 'A') {
                    char topLeft = input.get(x-1).get(y-1);
                    char bottomRight = input.get(x+1).get(y+1);
                    char topRight = input.get(x+1).get(y-1);
                    char bottomLeft = input.get(x-1).get(y+1);
                    List<Character> chars = List.of(topLeft, bottomRight, topRight, bottomLeft);
                    if (containsExactlyTwo(chars, 'M')
                            && containsExactlyTwo(chars, 'S')
                            && topLeft != bottomRight
                            && topRight != bottomLeft) {
                        total += 1;
                    }
                }
            }
        }
        return total;
    }

    private static List<Character> toCharList(String string) {
        return string.chars().mapToObj(c -> (char) c).toList();
    }

    private static boolean containsExactlyTwo(List<Character> characters, Character targetCharacter) {
        return characters.stream()
                .filter(character -> character == targetCharacter)
                .count() == 2;
    }

}