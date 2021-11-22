package pbo.logic;

import javafx.scene.canvas.GraphicsContext;
import pbo.gui.Painter;

public class GameLoop implements Runnable {
    private final Grid grid;
    private final GraphicsContext context;
    private int frameRate;
    private float interval;
    private boolean running;
    private boolean paused;
    private boolean keyIsPressed;

    public GameLoop(final Grid grid, final GraphicsContext context) {
        this.grid = grid;
        this.context = context;
        frameRate = 20;
        interval = 2000.0f / frameRate; // 2000 ms in a second
        running = true;
        paused = false;
        keyIsPressed = false;
    }

    @Override
    public void run() {
        while (running && !paused) {
            // Time the update and paint calls
            float time = System.currentTimeMillis();

            keyIsPressed = false;
            grid.update();
            Painter.paint(grid, context);

            if (!grid.getSnake().isSafe()) {
                pause();
                Painter.paintResetMessage(context);
                break;
            }

            time = System.currentTimeMillis() - time;

            // Adjust the timing correctly
            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException ignore) {
                }
            }
        }
    }

    public void stop() {
        running = false;
    }

    public boolean isKeyPressed() {
        return keyIsPressed;
    }

    public void setKeyPressed() {
        keyIsPressed = true;
    }

    public void resume() {
        paused = false;
    }

    public void pause() {
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }
}