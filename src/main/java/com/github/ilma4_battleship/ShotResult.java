package com.github.ilma4_battleship;

public enum ShotResult {
    MISS, FIRST_TIME_HIT, AGAIN_HIT, ALL_SHIPS_DESTROYED, SANK_SHIP;

    @Override
    public String toString() {
        return switch (this) {
            case MISS -> "You missed!";
            case FIRST_TIME_HIT, AGAIN_HIT -> "You hit a ship!";
            case ALL_SHIPS_DESTROYED -> "You sank the last ship. You won. Congratulations!";
            case SANK_SHIP -> "You sank a ship!";
        };
    }
}
