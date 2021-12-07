package SnakeGame.Gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

import java.util.Random;

import SnakeGame.Logic.Snake;
import SnakeGame.Logic.Score;
import SnakeGame.Logic.Food;
import SnakeGame.Logic.Grid;
import SnakeGame.Logic.Painter;

/**
 *
 * @author Tim
 */
public class Controller implements Initializable {

    @FXML
    private FlowPane SnakeField;

    @FXML
    private void start() {
        run();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private final static int speed = 5; // Kecepatan ular

    private Food food = new Food();
    private Score score = new Score();
    private Snake snake;
    private Random rand = new Random(); // Keperluan random

    public enum Dir {
        left, right, up, down
    }

    Dir direction = Dir.left;

    public void run() {

        try {
            Canvas c = new Canvas(Grid.getWidth() * Grid.getCornerSize(), Grid.getHeight() * Grid.getCornerSize());
            GraphicsContext gc = c.getGraphicsContext2D(); // graphic context
            SnakeField.getChildren().add(c); // untuk ngambil element scene builder

            new AnimationTimer() {
                long lastTick = 0;

                @Override
                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }

                    // Controller game
                    SnakeField.getScene().addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                        if (key.getCode() == KeyCode.UP) {
                            if (direction != Dir.down) {
                                direction = Dir.up;
                            }
                        }
                        if (key.getCode() == KeyCode.LEFT) {
                            if (direction != Dir.right) {
                                direction = Dir.left;
                            }
                        }
                        if (key.getCode() == KeyCode.DOWN) {
                            if (direction != Dir.up) {
                                direction = Dir.down;
                            }
                        }
                        if (key.getCode() == KeyCode.RIGHT) {
                            if (direction != Dir.left) {
                                direction = Dir.right;
                            }
                        }
                    });
                }
            }.start();

            snake = new Snake();
            food.generateFood(rand);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tick(GraphicsContext gc) {
        if (snake.isGameOver() == true) {
            Painter.drawGameOver(gc);
            return;
        }
        Painter.drawBackground(gc);
        Painter.drawFood(gc, food, rand);
        Painter.drawSnake(snake, gc);
        Painter.drawScore(score, gc);

        // Set ular berjalan
        snake.snakeMove();

        // Direction
        switch (direction) {
            case up:
                snake.moveUp();
                break;
            case down:
                snake.moveDown();
                break;
            case left:
                snake.moveLeft();
                break;
            case right:
                snake.moveRight();
                break;
        }
        snake.gameOver();
        snake.eatFood(score, food, rand);
    }
}