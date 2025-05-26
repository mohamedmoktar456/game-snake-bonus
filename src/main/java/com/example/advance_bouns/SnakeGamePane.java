package com.example.advance_bouns;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGamePane extends Pane {
    private final int TILE_SIZE = 20;
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private final LinkedList<Rectangle> snake = new LinkedList<>();
    private Point2D direction = new Point2D(1, 0); // يمين بالبداية
    private Rectangle food;
    private Timeline timeline;
    private Player player;
    private Stage stage;

    public SnakeGamePane(Player player, int speed, Stage stage) {
        this.player = player;
        this.stage = stage;
        setPrefSize(WIDTH, HEIGHT);
        initGame();

        setFocusTraversable(true);
        requestFocus();  // <-- هنا طلبنا التركيز مباشرة بعد السماح به

        setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.UP && direction.getY() != 1) direction = new Point2D(0, -1);
            else if (code == KeyCode.DOWN && direction.getY() != -1) direction = new Point2D(0, 1);
            else if (code == KeyCode.LEFT && direction.getX() != 1) direction = new Point2D(-1, 0);
            else if (code == KeyCode.RIGHT && direction.getX() != -1) direction = new Point2D(1, 0);
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> move()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startGame() {
        timeline.play();
    }

    private void initGame() {
        snake.clear();
        getChildren().clear();
        Rectangle head = new Rectangle(TILE_SIZE, TILE_SIZE, Color.GREEN);
        head.setX(WIDTH / 2);
        head.setY(HEIGHT / 2);
        snake.add(head);
        getChildren().add(head);
        placeFood();
    }

    private void move() {
        Rectangle head = snake.getFirst();
        double newX = head.getX() + direction.getX() * TILE_SIZE;
        double newY = head.getY() + direction.getY() * TILE_SIZE;

        // خسارة عند الاصطدام بالحواف أو الذيل
        if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT || hitSelf(newX, newY)) {
            timeline.stop();
            DBManager.saveScore(player);
            showGameOver();
            return;
        }

        Rectangle newHead = new Rectangle(TILE_SIZE, TILE_SIZE, Color.GREEN);
        newHead.setX(newX);
        newHead.setY(newY);
        snake.addFirst(newHead);
        getChildren().add(newHead);

        if (newHead.getBoundsInParent().intersects(food.getBoundsInParent())) {
            player.addScore(10);
            getChildren().remove(food);
            placeFood();
        } else {
            Rectangle tail = snake.removeLast();
            getChildren().remove(tail);
        }
    }

    private boolean hitSelf(double x, double y) {
        for (int i = 1; i < snake.size(); i++) {
            Rectangle r = snake.get(i);
            if (r.getX() == x && r.getY() == y) return true;
        }
        return false;
    }

    private void placeFood() {
        Random random = new Random();
        int x = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
        int y = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;

        food = new Rectangle(TILE_SIZE, TILE_SIZE, Color.RED);
        food.setX(x);
        food.setY(y);
        getChildren().add(food);
    }

    private void showGameOver() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Game Over, " + player.getName());
        alert.setContentText("Your score: " + player.getScore());
        alert.setOnHidden(e -> stage.close());
        alert.show();
    }
}
