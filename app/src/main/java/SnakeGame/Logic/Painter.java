package SnakeGame.Logic;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Painter {

    public static void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < Grid.getWidth(); i++) {
            for (int j = 0; j < Grid.getHeight(); j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.LIGHTGREEN);
                } else {
                    gc.setFill(Color.LIGHTGREEN);
                }
                gc.fillRect(i * Grid.getCornerSize(), j * Grid.getCornerSize(), Grid.getWidth() * Grid.getCornerSize(),
                        Grid.getHeight() * Grid.getCornerSize());
            }
        }
    }

    public static void drawSnake(Snake snake, GraphicsContext gc) {
        for (int i = 0; i < snake.getSize(); i++) {
            if (i == 0) {
                // Warna Border Head
                gc.setFill(Color.BLACK);
                gc.fillOval(snake.getHead().getX() * Grid.getCornerSize(),
                        snake.getHead().getY() * Grid.getCornerSize(), Grid.getCornerSize() - 1,
                        Grid.getCornerSize() - 1);
                // Warna Head
                gc.setFill(Color.WHITE);
                gc.fillOval(snake.getHead().getX() * Grid.getCornerSize(),
                        snake.getHead().getY() * Grid.getCornerSize(), Grid.getCornerSize() - 2,
                        Grid.getCornerSize() - 2);
            } else if (i == snake.getSize() - 1) {
                // Warna border Tail
                gc.setFill(Color.BLACK);
                gc.fillRect(snake.getTail().getX() * Grid.getCornerSize(),
                        snake.getTail().getY() * Grid.getCornerSize(), Grid.getCornerSize() - 1,
                        Grid.getCornerSize() - 1);
                // Warna Tail
                gc.setFill(Color.WHITE);
                gc.fillRect(snake.getTail().getX() * Grid.getCornerSize(),
                        snake.getTail().getY() * Grid.getCornerSize(), Grid.getCornerSize() - 1,
                        Grid.getCornerSize() - 1);
            } else {
                // Warna border Body
                gc.setFill(Color.BLACK);
                gc.fillRect(snake.getBody(i).getX() * Grid.getCornerSize(),
                        snake.getBody(i).getY() * Grid.getCornerSize(), Grid.getCornerSize() - 1,
                        Grid.getCornerSize() - 1);
                // Warna Body
                gc.setFill(Color.GREEN);
                gc.fillRect(snake.getBody(i).getX() * Grid.getCornerSize(),
                        snake.getBody(i).getY() * Grid.getCornerSize(), Grid.getCornerSize() - 2,
                        Grid.getCornerSize() - 2);
            }
        }
    }

    public static void drawFood(GraphicsContext gc, Food food, Random rand) {
        Color cc = Color.WHITE;

        switch (food.getFoodColor()) {
            case 0:
                cc = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
                break;
        }
        gc.setFill(cc);
        gc.fillOval(food.getFoodX() * Grid.getCornerSize(), food.getFoodY() * Grid.getCornerSize(),
                Grid.getCornerSize(),
                Grid.getCornerSize());
    }

    public static void drawScore(Score score, GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 15));
        gc.fillText("Score: " + score.getScore(), 5, 20);
    }
}
