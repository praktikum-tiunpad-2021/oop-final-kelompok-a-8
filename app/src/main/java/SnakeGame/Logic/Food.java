package SnakeGame.Logic;

import java.util.Random;

public class Food {
    private int foodX;
    private int foodY;
    private int foodColor;

    public Food() {
        this.foodX = 0;
        this.foodY = 0;
        this.foodColor = 0;
    }

    public int getFoodX() {
        return this.foodX;
    }

    public int getFoodY() {
        return this.foodY;
    }

    public int getFoodColor() {
        return this.foodColor;
    }

    public void generateFood(Snake snake, Random rand) {
        start: while (true) {
            this.foodX = rand.nextInt(Grid.getWidth());
            this.foodY = rand.nextInt(Grid.getHeight());

            for (Point c : snake.getSnake()) {
                if (c.getX() == foodX && c.getY() == foodY) {
                    continue start;
                }
            }
            this.foodColor = rand.nextInt(1);
            break;
        }
    }
}
