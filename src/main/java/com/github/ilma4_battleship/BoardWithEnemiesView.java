package com.github.ilma4_battleship;

public class BoardWithEnemiesView extends BoardImpl {

    private final Board enemiesView;

    public BoardWithEnemiesView(Board enemiesView) {
        super();
        this.enemiesView = enemiesView;
    }

    @Override
    public void addShip(ShipLocation shipLocation) {
        super.addShip(shipLocation);
    }

    @Override
    public ShotResult takeShot(Point point) {
        ShotResult result = super.takeShot(point);
        switch (result) {
            case MISS -> enemiesView.setCell(point, Cell.MISS);
            case FIRST_TIME_HIT, ALL_SHIPS_DESTROYED, SANK_SHIP ->
                enemiesView.setCell(point, Cell.HIT);
        }
        return result;
    }

    public Board getEnemiesView() {
        return enemiesView;
    }
}
