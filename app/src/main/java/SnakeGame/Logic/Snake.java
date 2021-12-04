package SnakeGame.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake {

    private List<Point> snakeBody; // Body of snake
    private boolean gameOver;

    public Snake() {
        snakeBody = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            snakeBody.add(new Point(Grid.getWidth() / 2, Grid.getHeight() / 2));
        }
        gameOver = false;
    }

    public List<Point> getSnake() {
        return snakeBody;
    }

    public Point getHead() {
        return snakeBody.get(0);
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
        return this.gameOver;
    }

    public void snakeMove() {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).setX(snakeBody.get(i - 1).getX());
            snakeBody.get(i).setY(snakeBody.get(i - 1).getY());
        }
    }

    public void moveUp() {
        getHead().decrementY();
    }

    public void moveDown() {
        getHead().incrementY();
    }

    public void moveRight() {
        getHead().incrementX();
    }

    public void moveLeft() {
        getHead().decrementX();
    }

    public void add(Point point) {
        snakeBody.add(point);
    }

    public void eatFood(Score score, Food food, Random rand) {
        if (food.getFoodX() == getHead().getX() && food.getFoodY() == getHead().getY()) {
            add(new Point(-1, -1));
            food.generateFood(this, rand);
            score.setScore();
        }
    }

    public void gameOver() {
        if (getHead().getX() < 0 || getHead().getY() < 0 || getHead().getX() > Grid.getWidth() - 1
                || getHead().getY() > Grid.getHeight() - 1) {
            gameOver = true;
        }

        // Self Destroy
        for (int i = 1; i < getSize(); i++) {
            if (getHead().getX() == getBody(i).getX()
                    && getHead().getY() == getBody(i).getY()) {
                gameOver = true;
            }
        }
    }
}