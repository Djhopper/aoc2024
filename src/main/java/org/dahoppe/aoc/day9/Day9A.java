package org.dahoppe.aoc.day9;

import org.dahoppe.aoc.util.Parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Day9A {

    sealed static class Block permits EmptyBlock, FileBlock {}

    final static class EmptyBlock extends Block {
        @Override
        public String toString() {
            return ".";
        }
        @Override
        public boolean equals(Object o) {
            return o != null && getClass() == o.getClass();
        }
    }

    final static class FileBlock extends Block {
        private final int id;
        FileBlock(int id) {
            this.id = id;
        }
        int id() {
            return id;
        }
        @Override
        public String toString() {
            return String.valueOf(id);
        }
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            FileBlock fileBlock = (FileBlock) o;
            return id == fileBlock.id;
        }
    }

    static List<Block> parseInput(String input) {
        int nextId = 0;
        boolean fullBlockNext = true;
        List<Block> blocks = new ArrayList<>();
        List<Integer> integers = Parsing.treatAsUnseparatedIntegers(input);

        for (int integer : integers) {
            if (fullBlockNext) {
                int fileBlockId = nextId;
                IntStream.range(0, integer).forEach(ignored -> blocks.add(new FileBlock(fileBlockId)));
                nextId++;
            } else {
                IntStream.range(0, integer).forEach(ignored -> blocks.add(new EmptyBlock()));
            }
            fullBlockNext = !fullBlockNext;
        }

        return blocks;
    }

    static long solveA(List<Block> blocks) {
        int firstEmptySpaceIndex = blocks.indexOf(new EmptyBlock());
        FileBlock lastFileBlock = blocks.reversed().stream()
                .filter(block -> block instanceof FileBlock)
                .map(block -> (FileBlock) block)
                .findFirst()
                .orElseThrow();
        int lastFileBlockIndex = blocks.lastIndexOf(lastFileBlock);

        while (lastFileBlockIndex > firstEmptySpaceIndex) {
            blocks.set(firstEmptySpaceIndex, lastFileBlock);
            blocks.set(lastFileBlockIndex, new EmptyBlock());

            firstEmptySpaceIndex = blocks.indexOf(new EmptyBlock());
            lastFileBlock = blocks.reversed().stream()
                    .filter(block -> block instanceof FileBlock)
                    .map(block -> (FileBlock) block)
                    .findFirst()
                    .orElseThrow();
            lastFileBlockIndex = blocks.lastIndexOf(lastFileBlock);
        }

        long total = 0;
        for (int i=0; i<blocks.size(); i++) {
            Block block = blocks.get(i);
            if (block instanceof FileBlock fileBlock) {
                if (total + (long) i * fileBlock.id() < total) {
                    return -1;
                }
                total += (long) i * fileBlock.id();
            }
        }
        return total;
    }

}
