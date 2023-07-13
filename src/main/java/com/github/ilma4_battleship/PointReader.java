package com.github.ilma4_battleship;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Supplier;

public class PointReader {

    private final Supplier<String> input;
    private final PointParser pointParser;

    public PointReader(Supplier<String> input, PointParser pointParser) {
        this.input = input;
        this.pointParser = pointParser;
    }

    public Point nextPoint() {
        String[] words = input.get().split("\\h");
        return pointParser.parse(words[0]);
    }

}
