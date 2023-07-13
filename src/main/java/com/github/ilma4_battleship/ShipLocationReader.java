package com.github.ilma4_battleship;

import java.util.function.Supplier;

public class ShipLocationReader {

    private final PointParser pointParser;
    private final Supplier<String> input;

    public ShipLocationReader(Supplier<String> input, PointParser pointParser) {
        this.input = input;
        this.pointParser = pointParser;
    }

    public ShipLocation nextShipLocation() {
        String[] unparsedPoints = input.get().split("\\h"); // split by spaces
        final int POINTS_REQUIRED = 2;
        if (unparsedPoints.length != POINTS_REQUIRED) {
            throw new IllegalArgumentException(
                "Wrong ship location! Expected 2 points separated by space. Got: "
                    + unparsedPoints.length);
        }
        return new ShipLocation(pointParser.parse(unparsedPoints[0]),
            pointParser.parse(unparsedPoints[1]));
    }

}
