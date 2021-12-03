package pbo.project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.text.Font;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import java.util.Random;

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

    private final static int speed = 10; // Kecepata ular
    private int score = 0; // Score

    private Grid grid = new Grid();

    private int foodcolor = 0; // Warna food
    private int foodX = 0; // Absis food
    private int foodY = 0; // Ordinat food

    private Snake snake;
    private Dir direction = Dir.left; // First directon of snake

    private boolean gameOver = false; // Awal dan akhir game
    private Random rand = new Random(); // Keperluan random

    public enum Dir {
        left, right, up, down
    }

    public void run() {
        try {
            Canvas c = new Canvas(grid.getWidth() * grid.getCornerSize(), grid.getHeight() * grid.getCornerSize()); // Untuk lapak main
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

                    // controller game
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
                        if (key.getCode() == KeyCode.SPACE) {

                        }
                    });
                }
            }.start();

            snake = new Snake();
            generateFood();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tick(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 100, 250);
            return;
        }
        drawBackground(gc);
        drawFood(gc);
        snake.drawSnake(gc);
        drawScore(gc);

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
        gameOver();
        eatFood();

    }

    private void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.LIGHTGREEN);
                } else {
                    gc.setFill(Color.LIGHTGREEN);
                }
                gc.fillRect(i * grid.getCornerSize(), j * grid.getCornerSize(), grid.getWidth() * grid.getCornerSize(),
                        grid.getHeight() * grid.getCornerSize());
            }
        }
    }

    private void generateFood() {
        start: while (true) {
            foodX = rand.nextInt(grid.getWidth());
            foodY = rand.nextInt(grid.getHeight());

            for (Point c : snake.getSnake()) {
                if (c.getX() == foodX && c.getY() == foodY) {
                    continue start;
                }
            }
            foodcolor = rand.nextInt(1);
            break;
        }
    }

    private void drawFood(GraphicsContext gc) {
        Color cc = Color.WHITE;

        switch (foodcolor) {
            case 0:
                cc = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
                break;
        }
        gc.setFill(cc);
        gc.fillOval(foodX * grid.getCornerSize(), foodY * grid.getCornerSize(), grid.getCornerSize(),
                grid.getCornerSize());
    }

    public void gameOver() {
        if (snake.getHead().getX() < 0 || snake.getHead().getY() < 0 || snake.getHead().getX() > grid.getWidth() - 1
                || snake.getHead().getY() > grid.getHeight() - 1) {
            gameOver = true;
        }

        // Self Destroy
        for (int i = 1; i < snake.getSize(); i++) {
            if (snake.getHead().getX() == snake.getBody(i).getX()
                    && snake.getHead().getY() == snake.getBody(i).getY()) {
                gameOver = true;
            }
        }
    }

    private void eatFood() {
        if (foodX == snake.getHead().getX() && foodY == snake.getHead().getY()) {
            snake.add(new Point(-1, -1));
            generateFood();
            score += 10;
        }
    }

    private void drawScore(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 15));
        gc.fillText("Score: " + score, 5, 20);
    }
}