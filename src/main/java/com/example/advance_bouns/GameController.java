package com.example.advance_bouns;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameController {
    public void startGame(Stage stage, Player player, String difficulty) {
        int speed;
        switch (difficulty) {
            case "Medium": speed = 150; break;
            case "Hard": speed = 100; break;
            default: speed = 200; // Easy
        }

        SnakeGamePane gamePane = new SnakeGamePane(player, speed, stage);
        Scene gameScene = new Scene(gamePane, 600, 400);
        stage.setScene(gameScene);
        stage.setTitle("Snake Game - " + player.getName());
        stage.show();


        gamePane.requestFocus();

        gamePane.startGame();
    }
}
