package pbo.project;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Snake {

    private Grid grid;
    private List<Point> snakeBody; // Body of snake

    public Snake() {
        grid = new Grid();
        snakeBody = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            snakeBody.add(new Point(grid.getWidth() / 2, grid.getHeight() / 2));
        }
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

    public void drawSnake(GraphicsContext gc) {
        for (int i = 0; i < getSize(); i++) {
            if (i == 0) {
                // Warna Border Head
                gc.setFill(Color.BLACK);
                gc.fillOval(getHead().getX() * grid.getCornerSize(),
                        getHead().getY() * grid.getCornerSize(), grid.getCornerSize() - 1,
                        grid.getCornerSize() - 1);
                // Warna Head
                gc.setFill(Color.WHITE);
                gc.fillOval(getHead().getX() * grid.getCornerSize(),
                        getHead().getY() * grid.getCornerSize(), grid.getCornerSize() - 2,
                        grid.getCornerSize() - 2);
            } else if (i == getSize() - 1) {
                // Warna border Tail
                gc.setFill(Color.BLACK);
                gc.fillRect(getTail().getX() * grid.getCornerSize(),
                        getTail().getY() * grid.getCornerSize(), grid.getCornerSize() - 1,
                        grid.getCornerSize() - 1);
                // Warna Tail
                gc.setFill(Color.WHITE);
                gc.fillRect(getTail().getX() * grid.getCornerSize(),
                        getTail().getY() * grid.getCornerSize(), grid.getCornerSize() - 1,
                        grid.getCornerSize() - 1);
            } else {
                // Warna border Body
                gc.setFill(Color.BLACK);
                gc.fillRect(getBody(i).getX() * grid.getCornerSize(),
                        getBody(i).getY() * grid.getCornerSize(), grid.getCornerSize() - 1,
                        grid.getCornerSize() - 1);
                // Warna Body
                gc.setFill(Color.GREEN);
                gc.fillRect(getBody(i).getX() * grid.getCornerSize(),
                        getBody(i).getY() * grid.getCornerSize(), grid.getCornerSize() - 2,
                        grid.getCornerSize() - 2);
            }
        }
    }
}