package pbo.project;

public class Grid {
    private final int WIDTH; // Lebar layar main
    private final int HEIGHT; // Tinggi layar main
    private final int CORNER_SIZE;

    public Grid() {
        WIDTH = 50;
        HEIGHT = WIDTH;
        CORNER_SIZE = 10;
    }

    public final int getWidth() {
        return WIDTH;
    }

    public final int getHeight() {
        return HEIGHT;
    }

    public final int getCornerSize() {
        return CORNER_SIZE;
    }
}
