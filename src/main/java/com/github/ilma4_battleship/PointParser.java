package com.github.ilma4_battleship;

public class PointParser {

    public Point parse(String source) {
        if (!source.matches("^[A-J]([0-9]|10)$")) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates!");
        }
        int line = source.charAt(0) - 'A';
        int row = Integer.parseInt(source.substring(1)) - 1;
        return new Point(line, row);
    }
}
