package org.dahoppe.aoc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Files {

    private static final Logger log = LoggerFactory.getLogger(Files.class);

    public static String read(String filename) {
        String path = "/input/" + filename;
        try {
            return new String(Parsing.class.getResourceAsStream(path).readAllBytes());
        } catch (IOException e) {
            log.info("Failed to read input from {}", path, e);
            throw new RuntimeException(e);
        }
    }

}
