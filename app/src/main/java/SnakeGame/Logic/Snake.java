package SnakeGame.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake extends Point {

    protected List<Point> snakeBody; // Body of snake
    protected Point snakeHead; // Body of snake
    protected Point snakeTail; // Body of snake
    protected boolean gameOver;

    public Snake() {
        super();
        snakeBody = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            snakeBody.add(new Point(Grid.getWidth() / 2, Grid.getHeight() / 2));
        }
        snakeHead = snakeBody.get(0);
        snakeTail = snakeBody.get(snakeBody.size() - 1);
        gameOver = false;
    }

    public List<Point> getSnake() {
        return snakeBody;
    }

    public Point getHead() {
        return snakeHead;
    }

    public Point getTail() {
        return snakeBody.get(snakeBody.size() - 1);
    }

    public Point getBody(int index) {
        return snakeBody.get(index);
    }

    public int getSize() {
        return snakeBody.size();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void snakeMove() {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
    }

    public void moveUp() {
        snakeHead.y--;
    }

    public void moveDown() {
        snakeHead.y++;
    }

    public void moveRight() {
        snakeHead.x++;
    }

    public void moveLeft() {
        snakeHead.x--;
    }

    @Override
    public void add(Point point) {
        snakeBody.add(point);
    }

    public void eatFood(Score score, Food food, Random rand) {
        if (food.getFoodX() == snakeHead.x && food.getFoodY() == snakeHead.y) {
            add(new Point(-1, -1));
            food.generateFood(rand);
            score.setScore();
        }
    }

    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x > Grid.getWidth() - 1
                || snakeHead.y > Grid.getHeight() - 1) {
            gameOver = true;
        }

        // Self Destroy
        for (int i = 1; i < getSize(); i++) {
            if (snakeHead.x == snakeBody.get(i).x
                    && snakeHead.y == snakeBody.get(i).y) {
                gameOver = true;
            }
        }
    }
}