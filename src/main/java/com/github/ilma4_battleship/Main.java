package com.github.ilma4_battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(() -> new BoardWithEnemiesView(new BoardImpl()),
            scanner::nextLine,
            ShipInfo.getPlaceOrder());
        game.runGame();
    }
}
