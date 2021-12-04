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
import SnakeGame.Logic.Direction;
import SnakeGame.Logic.Food;
import SnakeGame.Logic.Grid;

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

    private final static int speed = 10; // Kecepatan ular

    private Food food = new Food();
    private Score score = new Score();
    private Snake snake;
    private Direction direction = new Direction();
    private Random rand = new Random(); // Keperluan random

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
                        direction.tick(snake, score, food, rand, gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        direction.tick(snake, score, food, rand, gc);
                    }

                    // controller game
                    SnakeField.getScene().addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                        if (key.getCode() == KeyCode.UP) {
                            if (direction.currentDirection() != direction.dirDown()) {
                                direction.setDirection(direction.dirUp());
                                ;
                            }
                        }
                        if (key.getCode() == KeyCode.LEFT) {
                            if (direction.currentDirection() != direction.dirRight()) {
                                direction.setDirection(direction.dirLeft());
                            }
                        }
                        if (key.getCode() == KeyCode.DOWN) {
                            if (direction.currentDirection() != direction.dirUp()) {
                                direction.setDirection(direction.dirDown());
                            }
                        }
                        if (key.getCode() == KeyCode.RIGHT) {
                            if (direction.currentDirection() != direction.dirLeft()) {
                                direction.setDirection(direction.dirRight());
                            }
                        }
                        if (key.getCode() == KeyCode.SPACE) {

                        }
                    });
                }
            }.start();

            snake = new Snake();
            food.generateFood(snake, rand);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}