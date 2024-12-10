package org.dahoppe.aoc.day9;

import org.dahoppe.aoc.util.Parsing;

import java.util.ArrayList;
import java.util.List;

class Day9B {

    abstract sealed static class MultiBlock permits EmptyMultiBlock, FileMultiBlock {
        private final int size;
        MultiBlock(int size) {
            this.size = size;
        }
        int size() {
            return size;
        }
    }

    final static class EmptyMultiBlock extends MultiBlock {
        EmptyMultiBlock(int size) {
            super(size);
        }
        @Override
        public String toString() {
            return ".".repeat(size());
        }
        @Override
        public boolean equals(Object o) {
            return o != null && getClass() == o.getClass();
        }
    }

    final static class FileMultiBlock extends MultiBlock {
        private final int id;
        FileMultiBlock(int id, int size) {
            super(size);
            this.id = id;
        }
        int id() {
            return id;
        }
        @Override
        public String toString() {
            return String.valueOf(id).repeat(size());
        }
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            FileMultiBlock fileBlock = (FileMultiBlock) o;
            return id == fileBlock.id;
        }
    }

    static List<MultiBlock> parseInput(String input) {
        int nextId = 0;
        boolean fullBlockNext = true;
        List<MultiBlock> blocks = new ArrayList<>();
        List<Integer> integers = Parsing.treatAsUnseparatedIntegers(input);

        for (int integer : integers) {
            if (fullBlockNext) {
                if (integer > 0) {
                    blocks.add(new FileMultiBlock(nextId, integer));
                    nextId++;
                }
            } else {
                if (integer > 0) {
                    blocks.add(new EmptyMultiBlock(integer));
                }
            }
            fullBlockNext = !fullBlockNext;
        }

        return blocks;
    }

    static long solveB(List<MultiBlock> blocks) {
        int biggestId = blocks.reversed().stream()
                .filter(block -> block instanceof FileMultiBlock)
                .map(block -> ((FileMultiBlock) block).id())
                .findFirst()
                .orElseThrow();

        for (int id = biggestId; id >= 0; id--) {
            int finalId = id;
            FileMultiBlock fileMultiBlock = blocks.stream()
                    .filter(block -> block instanceof FileMultiBlock)
                    .map(block -> ((FileMultiBlock) block))
                    .filter(block -> block.id() == finalId)
                    .findFirst()
                    .orElseThrow();
            int fileIndex = blocks.indexOf(fileMultiBlock);
            for (int i=0; i<fileIndex; i++) {
                MultiBlock block = blocks.get(i);
                if (block instanceof EmptyMultiBlock emptyMultiBlock) {
                    if (emptyMultiBlock.size() == fileMultiBlock.size()) {
                        // Swap fileMultiBlock with the emptyMultiBlock
                        blocks.set(fileIndex, new EmptyMultiBlock(fileMultiBlock.size()));
                        blocks.set(i, fileMultiBlock);
                        break;
                    } else if (emptyMultiBlock.size() > fileMultiBlock.size()) {
                        // Swap into the empty space, leaving a smaller empty space after it
                        blocks.set(fileIndex, new EmptyMultiBlock(fileMultiBlock.size()));
                        blocks.set(i, new EmptyMultiBlock(emptyMultiBlock.size() - fileMultiBlock.size()));
                        blocks.add(i, fileMultiBlock);
                        break;
                    }
                }
            }
        }

        long total = 0;
        int multiplier = 0;
        for (MultiBlock block : blocks) {
            if (block instanceof FileMultiBlock fileBlock) {
                for (int i=0; i<fileBlock.size(); i++) {
                    total += (long) multiplier * fileBlock.id();
                    multiplier++;
                }
            } else {
                multiplier += block.size();
            }
        }
        return total;
    }

}
