package com.example.advance_bouns;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        DBManager.initializeDB();

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        ComboBox<String> difficultyBox = new ComboBox<>();
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyBox.setValue("Easy");

        Button startButton = new Button("Start Game");

        VBox root = new VBox(10, new Label("Snake Game"), nameField, difficultyBox, startButton);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        startButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String difficulty = difficultyBox.getValue();
            if (!name.isEmpty()) {
                Player player = new Player(name);
                new GameController().startGame(primaryStage, player, difficulty);
            }
        });

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game - Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
