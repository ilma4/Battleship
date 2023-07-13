package com.github.ilma4_battleship;

public abstract class Board {

    protected abstract void setCell(Point point, Cell cell);

    protected abstract void addShip(ShipLocation shipLocation);

    protected abstract ShotResult takeShot(Point point);

    public abstract int getShipsCellsCount();

    public abstract int getShotShipsCellsCount();
    public abstract int getSize();
}
