package SnakeGame.Logic;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Direction {

    private Dir direction;

    public enum Dir {
        left, right, up, down
    }

    public Direction() {
        direction = Dir.left;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
    }

    public Dir currentDirection() {
        return this.direction;
    }

    public Dir dirUp() {
        return Dir.up;
    }

    public Dir dirDown() {
        return Dir.down;
    }

    public Dir dirRight() {
        return Dir.right;
    }

    public Dir dirLeft() {
        return Dir.left;
    }

    public void tick(Snake snake, Score score, Food food, Random rand, GraphicsContext gc) {
        if (snake.isGameOver()) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 100, 250);
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
