package com.github.ilma4_battleship;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BoardImpl extends Board {

    private static final int SIZE = 10;
    private final Cell[][] board = new Cell[SIZE][SIZE];
    private final Map<Point, AtomicInteger> pointToShipLives = new HashMap<>();
    private int shipsCellsCount = 0;
    private int shotShipsCellsCount = 0;

    public BoardImpl() {
        for (var arr : board) {
            Arrays.fill(arr, Cell.FOG);
        }
    }

    private Cell getCell(Point point) {
        if (!isCorrectPoint(point)) {
            throw new IllegalArgumentException("Bad point");
        }
        return board[point.line()][point.row()];
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    protected void setCell(Point point, Cell cell) {
        board[point.line()][point.row()] = cell;
    }

    @Override
    public int getShipsCellsCount() {
        return shipsCellsCount;
    }

    @Override
    public int getShotShipsCellsCount() {
        return shotShipsCellsCount;
    }

    @Override
    public String toString() {
        final char[] firstChar = new char[10];
        for (int i = 0; i < firstChar.length; i++) {
            firstChar[i] = (char) ('A' + i);
        }

        StringBuilder res = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < SIZE; i++) {
            res.append(firstChar[i]);
            res.append(" ");
            for (var cell : board[i]) {
                res.append(cell);
                res.append(" ");
            }
            res.append('\n');
        }
        return res.toString();
    }

    private boolean isCorrectPoint(Point point) {
        return 0 <= point.line() && point.line() < SIZE && 0 <= point.row() & point.row() < SIZE;
    }

    private void checkShipLocation(ShipLocation shipLocation) {
        for (var point : shipLocation) {
            Cell cell = getCell(point);
            if (!isCorrectPoint(point) || cell == Cell.SHIP) {
                throw new IllegalArgumentException("Error! Wrong ship location!");
            }
            for (var neighbour : point.getNeighbours()) {
                if (isCorrectPoint(neighbour) && getCell(neighbour) == Cell.SHIP) {
                    throw new IllegalArgumentException(
                        "Error! You placed it to close to another one.");
                }
            }
        }
    }

    @Override
    protected void addShip(ShipLocation shipLocation) {
        checkShipLocation(shipLocation);
        AtomicInteger shipLives = new AtomicInteger(shipLocation.size());
        for (var point : shipLocation) {
            board[point.line()][point.row()] = Cell.SHIP;
            pointToShipLives.put(point, shipLives);
        }
        shipsCellsCount += shipLocation.size();
    }

    @Override
    protected ShotResult takeShot(Point point) {
        if (!isCorrectPoint(point)) {
            throw new IllegalArgumentException("Error! Wrong coordinates!");
        }
        return switch (getCell(point)) {
            case HIT -> ShotResult.AGAIN_HIT;
            case MISS -> ShotResult.MISS;

            case SHIP -> {
                setCell(point, Cell.HIT);
                shotShipsCellsCount++;
                if (getShipsCellsCount() == getShotShipsCellsCount()) {
                    yield ShotResult.ALL_SHIPS_DESTROYED;
                }
                if (pointToShipLives.get(point).decrementAndGet() == 0) {
                    yield ShotResult.SANK_SHIP;
                }
                yield ShotResult.FIRST_TIME_HIT;
            }
            case FOG -> {
                setCell(point, Cell.MISS);
                yield ShotResult.MISS;
            }
        };
    }
}
