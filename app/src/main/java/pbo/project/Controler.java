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
public class Controler implements Initializable {
    @FXML
    private FlowPane SnakeField;

    @FXML
    private void start() {
        jalan();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    static int speed = 5; // untuk kecepatan ular
    static int score = 0; // score
    static int foodcolor = 0; // warna makanan
    static int width = 20; // lebar layar main
    static int height = 20; // tinggi layar main
    static int foodX = 0; // absis makanan
    static int foodY = 0; // ordinat makanan
    static int cornersize = 25; // sel
    static List<Corner> snake = new ArrayList<>(); // untuk snake
    static Dir direction = Dir.left; // arah awal ular jalan
    static boolean gameOver = false; // mulai dan akhir game
    static Random rand = new Random(); // inisisalisasi untuk keperluan random

    public enum Dir {
        left, right, up, down
    }

    // Point untuk ular
    public static class Corner {
        int x;
        int y;

        public Corner(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    public void jalan() {
        try {
            newFood();

            Canvas c = new Canvas(width * cornersize, height * cornersize); // Untuk lapak main
            GraphicsContext gc = c.getGraphicsContext2D(); // graphic context
            SnakeField.getChildren().add(c); // untuk ngambil element scene builder

            new AnimationTimer() {
                long lastTick = 0;

                // Pengaturan speed
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
                }

            }.start();

            // controller game
            SnakeField.getScene().addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.UP) {
                    direction = Dir.up;
                }
                if (key.getCode() == KeyCode.LEFT) {
                    direction = Dir.left;
                }
                if (key.getCode() == KeyCode.DOWN) {
                    direction = Dir.down;
                }
                if (key.getCode() == KeyCode.RIGHT) {
                    direction = Dir.right;
                }

            });

            // add start snake parts
            snake.add(new Corner(width / 2, height / 2)); // set width = 20; height = 20;
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // tick
    public static void tick(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 100, 250);
            return;
        }

        // Codingan jalan untuk ular
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        // ngecek collisions
        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y <= 0) {
                    gameOver = true;
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y >= height) {
                    gameOver = true;
                }
                break;
            case left:
                snake.get(0).x--;
                if (snake.get(0).x <= 0) {
                    gameOver = true;
                }
                break;
            case right:
                snake.get(0).x++;
                if (snake.get(0).x >= width) {
                    gameOver = true;
                }
                break;

        }

        // eat food
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new Corner(-1, -1));
            newFood();
        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
            }
        }

        // fill
        // background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * cornersize, height * cornersize);

        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + score, 10, 30);

        // random foodcolor
        Color cc = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

        switch (foodcolor) {
            case 0:
                cc = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
                break;
            case 1:
                cc = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
                break;
            case 2:
                cc = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));           
                break;
            case 3:
                cc = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));            
                break;
            case 4:
                cc = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));              
                break;
        }
        gc.setFill(cc);
        gc.fillOval(foodX * cornersize, foodY * cornersize, cornersize, cornersize);

        // snake color
        for (Corner c : snake) {
            // warna border
            gc.setFill(Color.WHITE);
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 1, cornersize - 1);
            // warna body
            gc.setFill(Color.RED);
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 2, cornersize - 2);

        }

    }

    // set new food
    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);

            for (Corner c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }
            foodcolor = rand.nextInt(5);
            score += 10;
            break;
        }
    }

}
