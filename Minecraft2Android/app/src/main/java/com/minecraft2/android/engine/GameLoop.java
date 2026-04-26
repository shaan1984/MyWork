package com.minecraft2.android.engine;

public class GameLoop implements Runnable {

    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long TARGET_FRAME_NANOS = NANOS_PER_SECOND / GameEngine.TARGET_FPS;

    private final GameEngine gameEngine;
    private final GameView gameView;
    private volatile boolean active = true;

    private long lastFrameTime = 0;
    private int fps = 0;
    private int frameCount = 0;
    private long fpsTimer = 0;

    public GameLoop(GameEngine gameEngine, GameView gameView) {
        this.gameEngine = gameEngine;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        lastFrameTime = System.nanoTime();
        fpsTimer = lastFrameTime;

        while (active) {
            long currentTime = System.nanoTime();
            long elapsed = currentTime - lastFrameTime;

            if (elapsed >= TARGET_FRAME_NANOS) {
                float deltaTime = Math.min(elapsed / (float) NANOS_PER_SECOND, 0.05f);
                lastFrameTime = currentTime;

                gameEngine.update(deltaTime);
                gameView.postInvalidate();

                frameCount++;
                if (currentTime - fpsTimer >= NANOS_PER_SECOND) {
                    fps = frameCount;
                    frameCount = 0;
                    fpsTimer = currentTime;
                }
            } else {
                long sleepNanos = TARGET_FRAME_NANOS - elapsed;
                try {
                    Thread.sleep(sleepNanos / 1_000_000, (int)(sleepNanos % 1_000_000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void stop() {
        active = false;
    }

    public int getFps() { return fps; }
}
