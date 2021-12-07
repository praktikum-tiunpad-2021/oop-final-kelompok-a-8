package SnakeGame.Logic;

import java.util.Random;

public class Food extends Snake {
    private int foodX;
    private int foodY;
    private int foodColor;

    public Food() {
        super();
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

    public void generateFood(Random rand) {
        start: while (true) {
            this.foodX = rand.nextInt(Grid.getWidth());
            this.foodY = rand.nextInt(Grid.getHeight());

            for (Point snake : snakeBody) {
                if (snake.x == foodX && snake.y == foodY) {
                    continue start;
                }
            }
            this.foodColor = rand.nextInt(1);
            break;
        }
    }
}
