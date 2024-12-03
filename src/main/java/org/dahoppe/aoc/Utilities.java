package org.dahoppe.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);

    public static String readInput(String filename) {
        String path = "/input/" + filename;
        try {
            return new String(Utilities.class.getResourceAsStream(path).readAllBytes());
        } catch (IOException e) {
            log.info("Failed to read input from {}", path, e);
            throw new RuntimeException(e);
        }
    }

}
