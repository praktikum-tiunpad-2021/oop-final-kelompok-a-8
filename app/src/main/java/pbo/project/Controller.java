package pbo.project;

import java.net.URL;
import java.util.ArrayList;
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
import java.util.List;
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
    
    private final static int WIDTH = 50; // Lebar layar main
    private final static int HEIGHT = WIDTH; // Tinggi layar main
    private final static int CORNER_SIZE = 10;

    private int foodcolor = 0; // Warna food
    private int foodX = 0; // Absis food
    private int foodY = 0; // Ordinat food

    private Point snakeHead; // Head of snake
    private Point snakeTail; // Tail of snake
    private List<Point> snakeBody = new ArrayList<>(); // Body of snake
    private Dir direction = Dir.left; // First directon of snake
    
    private boolean gameOver = false; // Awal dan akhir game
    private Random rand = new Random(); // Keperluan random

    public enum Dir {
        left, right, up, down
    }

    public void run() {
        try {
            Canvas c = new Canvas(WIDTH * CORNER_SIZE, HEIGHT * CORNER_SIZE); // Untuk lapak main
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

            // Add start snake parts
            for (int i = 0; i < 5; i++) {
                snakeBody.add(new Point(WIDTH/2, HEIGHT/2));
            }
            snakeHead = snakeBody.get(0);
            snakeTail = snakeBody.get(snakeBody.size()-1);
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
        drawSnake(gc);
        drawScore(gc);

        // Set ular berjalan
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        // Direction
        switch (direction) {
            case up:
                moveUp();
                break;
            case down:
                moveDown();
                break;
            case left:
                moveLeft();
                break;
            case right:
                moveRight();
                break;

        }
        gameOver();
        eatFood();

    }

    private void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if ((i+j) % 2 == 0) {
                    gc.setFill(Color.LIGHTGREEN);
                } else {
                    gc.setFill(Color.LIGHTGREEN);
                }
                gc.fillRect(i * CORNER_SIZE, j * CORNER_SIZE, WIDTH * CORNER_SIZE, HEIGHT * CORNER_SIZE);
            }
        }
    }

    private void generateFood() {
        start: 
        while (true) {
            foodX = rand.nextInt(WIDTH);
            foodY = rand.nextInt(HEIGHT);

            for (Point c : snakeBody) {
                if (c.x == foodX && c.y == foodY) {
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
        gc.fillOval(foodX * CORNER_SIZE, foodY * CORNER_SIZE, CORNER_SIZE, CORNER_SIZE);
    }

    private void drawSnake(GraphicsContext gc) {
        for (int i = 0; i < snakeBody.size(); i++) {
            if (i == 0) {
                // Warna Border Head
                gc.setFill(Color.BLACK);
                gc.fillOval(snakeHead.x * CORNER_SIZE, snakeHead.y * CORNER_SIZE, CORNER_SIZE - 1, CORNER_SIZE - 1);
                // Warna Head
                gc.setFill(Color.WHITE);
                gc.fillOval(snakeHead.x * CORNER_SIZE, snakeHead.y * CORNER_SIZE, CORNER_SIZE - 2, CORNER_SIZE - 2);
            } else if (i == snakeBody.size()-1) {
                // Warna border Tail
                gc.setFill(Color.BLACK);
                gc.fillRect(snakeBody.get(snakeBody.size()-1).x * CORNER_SIZE, snakeBody.get(snakeBody.size()-1).y * CORNER_SIZE, CORNER_SIZE - 1, CORNER_SIZE - 1);
                // Warna Tail
                gc.setFill(Color.WHITE);
                gc.fillRect(snakeBody.get(snakeBody.size()-1).x * CORNER_SIZE, snakeBody.get(snakeBody.size()-1).y * CORNER_SIZE, CORNER_SIZE - 1, CORNER_SIZE - 1);
            } else {
                // Warna border Body
                gc.setFill(Color.BLACK);
                gc.fillRect(snakeBody.get(i).x * CORNER_SIZE, snakeBody.get(i).y * CORNER_SIZE, CORNER_SIZE - 1, CORNER_SIZE - 1);
                // Warna Body
                gc.setFill(Color.GREEN);
                gc.fillRect(snakeBody.get(i).x * CORNER_SIZE, snakeBody.get(i).y * CORNER_SIZE, CORNER_SIZE - 2, CORNER_SIZE - 2);
            }
        }
    }

    private void moveUp() {
        snakeHead.y--;
    }
    private void moveDown() {
        snakeHead.y++;
    }
    private void moveRight() {
        snakeHead.x++;
    }
    private void moveLeft() {
        snakeHead.x--;
    }

    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x > WIDTH-1 || snakeHead.y > HEIGHT-1) {
            gameOver = true;
        }

        // Self Destroy
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).x && snakeHead.y == snakeBody.get(i).y) {
                gameOver = true;
            }
        }
    }

    private void eatFood() {
        if (foodX == snakeHead.x && foodY == snakeHead.y) {
            snakeBody.add(new Point(-1, -1));
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