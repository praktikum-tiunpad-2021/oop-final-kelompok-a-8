package pbo.gui;

import static pbo.logic.Grid.SIZE;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pbo.logic.Food;
import pbo.logic.Grid;
import pbo.logic.Point;
import pbo.logic.Snake;

public class Painter {


    public static void paint(Grid grid, GraphicsContext gc) {
        gc.setFill(Grid.COLOR);
        gc.fillRect(0, 0, grid.getWidth(), grid.getHeight());

        // Now the Food
        gc.setFill(Food.COLOR);
        paintPoint(grid.getFood().getPoint(), gc);

        // Now the snake
        Snake snake = grid.getSnake();
        gc.setFill(Snake.COLOR);
        snake.getPoints().forEach(point -> paintPoint(point, gc));
        if (!snake.isSafe()) {
            gc.setFill(Snake.DEAD);
            paintPoint(snake.getHead(), gc);
        }

        // The score
        gc.setFill(Color.BEIGE);
        gc.fillText("Score : " + 100 * snake.getPoints().size(), 10, 490);
    }

    private static void paintPoint(Point point, GraphicsContext gc) {
        gc.fillRect(point.getX() * SIZE, point.getY() * SIZE, SIZE, SIZE);
    }

    public static void paintResetMessage(GraphicsContext gc) {
        gc.setFill(Color.AQUAMARINE);
        gc.fillText("Press ENTER to reset.", 10, 10);
    }
}