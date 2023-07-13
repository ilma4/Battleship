package com.github.ilma4_battleship;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class Game {

    private final BoardWithEnemiesView firstBoard;
    private final BoardWithEnemiesView secondBoard;
    private final Supplier<String> inputSupplier;
    private final List<ShipInfo> shipsInfo;

    public Game(Supplier<BoardWithEnemiesView> boardFactory, Supplier<String> inputSupplier,
        List<ShipInfo> shipsInfo) {
        this.firstBoard = boardFactory.get();
        this.secondBoard = boardFactory.get();
        this.inputSupplier = inputSupplier;
        this.shipsInfo = shipsInfo;
    }

    private void processShip(ShipLocationReader shipLocationReader, ShipInfo ship, Board board) {
        tryUntilSuccess(() -> {
            ShipLocation location = shipLocationReader.nextShipLocation();
            if (location.size() != ship.getSize()) {
                throw new IllegalArgumentException(
                    "Error! Wrong length of the " + ship.getName() + "!");
            }
            board.addShip(location);
        });
    }

    private void placeShips(Board board) {
        System.out.println(board);
        var shipLocationReader = new ShipLocationReader(inputSupplier, new PointParser());
        for (var ship : shipsInfo) {
            System.out.println("Enter the coordinates of the " + ship + ":");
            processShip(shipLocationReader, ship, board);
            System.out.println(board);
        }
    }

    private static void tryUntilSuccess(Runnable task) {
        do {
            try {
                task.run();
                break;
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage() + " Try again:");
            }
        } while (true);
    }

    private static <T> T tryUntilSuccess(Callable<T> task) {
        do {
            try {
                return task.call();
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage() + " Try again:");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } while (true);
    }

    private ShotResult oneShot(PointReader pointReader, BoardWithEnemiesView board) {
        return tryUntilSuccess(() -> board.takeShot(pointReader.nextPoint()));
    }

    private BoardWithEnemiesView getCurrentBoard(int playerNumber) {
        return switch (playerNumber) {
            case (0) -> firstBoard;
            case (1) -> secondBoard;
            default -> throw new IllegalArgumentException(
                "Expected player number: '0' or '1'. Got: " + playerNumber);
        };
    }

    private BoardWithEnemiesView getOppositeBoard(int playerNumber) {
        return getCurrentBoard(playerNumber ^ 1);
    }

    private void shootingPhase() {
        final String separator = "-".repeat(firstBoard.getSize() * 2 + 1);

        var pointReader = new PointReader(inputSupplier, new PointParser());
        int curPlayerNumber = 0;
        while (true) {
            var oppositeBoard = getOppositeBoard(curPlayerNumber);
            var currentBoard = getCurrentBoard(curPlayerNumber);

            System.out.print(oppositeBoard.getEnemiesView());
            System.out.println(separator);
            System.out.println(currentBoard);
            System.out.printf("Player %d, it's your turn:", curPlayerNumber + 1);
            System.out.println();
            System.out.println();
            System.out.print("> ");
            ShotResult shotResult = oneShot(pointReader, oppositeBoard);
            System.out.println();
            System.out.println();
            System.out.println(shotResult);
            if (shotResult == ShotResult.ALL_SHIPS_DESTROYED) {
                break;
            }
            passMoveToAnotherPlayer();
            curPlayerNumber ^= 1; // 0 -> 1, 1 -> 0
        }
    }

    private void passMoveToAnotherPlayer() {
        System.out.println("Press Enter and pass the move to another player");
        inputSupplier.get();
    }

    public void runGame() {
        System.out.println("Player 1, place your ships on the game field");
        placeShips(firstBoard);
        passMoveToAnotherPlayer();

        System.out.println("Player 2, place your ships on the game field");
        placeShips(secondBoard);
        passMoveToAnotherPlayer();

        shootingPhase();
    }
}
