package com.github.ilma4_battleship;


public record Point(int line, int row) {

    public Point[] getNeighbours() {
        final int NEIGHBOURS_COUNT = 4;
        Point[] neighbours = new Point[NEIGHBOURS_COUNT];
        final int[] D_LINE = new int[]{0, 1, 0, -1};
        final int[] D_ROW = new int[]{1, 0, -1, 0};
        for (int k = 0; k < NEIGHBOURS_COUNT; k++) {
            neighbours[k] = new Point(line + D_LINE[k], row + D_ROW[k]);
        }
        return neighbours;
    }
}
