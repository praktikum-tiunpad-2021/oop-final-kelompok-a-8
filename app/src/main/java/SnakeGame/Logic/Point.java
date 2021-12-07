package SnakeGame.Logic;

public class Point {
    protected int x;
    protected int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    
    }
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int incrementX() {
        return this.x++;
    }

    public int incrementY() {
        return this.y++;
    }

    public int decrementX() {
        return this.x--;
    }

    public int decrementY() {
        return this.y--;
    }

    public void add(Point point) {
        add(point);
    }
}
