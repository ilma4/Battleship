package com.github.ilma4_battleship;


public enum Cell {
    SHIP, HIT, FOG, MISS;

    @Override
    public String toString() {
        return switch (this) {
            case SHIP -> "O";
            case HIT -> "X";
            case FOG -> "~";
            case MISS -> "M";
        };
    }
}
