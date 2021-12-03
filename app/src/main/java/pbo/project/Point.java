package pbo.project;

public class Point {
    private int x;
    private int y;

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
}
