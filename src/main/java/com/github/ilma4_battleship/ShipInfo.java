package com.github.ilma4_battleship;

import java.util.List;

public enum ShipInfo {
    CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER;

    private static final List<ShipInfo> PLACE_ORDER = List.of(
        CARRIER,
        BATTLESHIP,
        SUBMARINE,
        CRUISER,
        DESTROYER
    );

    public int getSize() {
        return switch (this) {
            case CARRIER -> 5;
            case BATTLESHIP -> 4;
            case SUBMARINE, CRUISER -> 3;
            case DESTROYER -> 2;
        };
    }


    public String getName() {
        return switch (this) {
            case CARRIER -> "Aircraft Carrier";
            case BATTLESHIP -> "Battleship";
            case SUBMARINE -> "Submarine";
            case CRUISER -> "Cruiser";
            case DESTROYER -> "Destroyer";
        };
    }

    @Override
    public String toString() {
        return getName() + " (" + getSize() + " cells)";
    }

    public static List<ShipInfo> getPlaceOrder() {
        return PLACE_ORDER;
    }
}
