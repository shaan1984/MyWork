package com.minecraft2.android.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.ui.HUD;
import com.minecraft2.android.ui.InventoryUI;
import com.minecraft2.android.ui.TouchController;
import com.minecraft2.android.world.World;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final GameEngine gameEngine;
    private final Renderer renderer;
    private final HUD hud;
    private final InventoryUI inventoryUI;
    private final TouchController touchController;
    private GameLoop gameLoop;
    private Thread gameThread;

    private final Paint debugPaint = new Paint();
    private final Paint loadingPaint = new Paint();
    private boolean showDebug = false;

    public GameView(Context context, GameEngine engine) {
        super(context);
        this.gameEngine = engine;
        this.renderer = new Renderer(context);
        this.hud = new HUD(context);
        this.inventoryUI = new InventoryUI(context);
        this.touchController = new TouchController(context, engine);

        getHolder().addCallback(this);
        setFocusable(true);

        debugPaint.setColor(Color.WHITE);
        debugPaint.setTextSize(28f);

        loadingPaint.setColor(Color.WHITE);
        loadingPaint.setTextSize(48f);
        loadingPaint.setTextAlign(Paint.Align.CENTER);
        loadingPaint.setAntiAlias(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Dimensions may be 0 here; surfaceChanged always fires after with real size
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        renderer.init(width, height);
        hud.init(width, height);
        inventoryUI.init(width, height);
        touchController.init(width, height);

        if (gameLoop == null) {
            gameLoop = new GameLoop(gameEngine, this);
            gameEngine.start(gameLoop);
            gameThread = new Thread(gameLoop, "GameThread");
            gameThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (gameLoop != null) gameLoop.stop();
        try {
            if (gameThread != null) gameThread.join(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void drawFrame(Canvas canvas) {
        World world = gameEngine.getWorld();
        Player player = gameEngine.getPlayer();

        if (world == null || player == null) {
            canvas.drawColor(Color.BLACK);
            int cx = canvas.getWidth() / 2;
            int cy = canvas.getHeight() / 2;
            canvas.drawText("Generating world...", cx, cy, loadingPaint);
            return;
        }

        try {
            renderer.render(canvas, world, player);
        } catch (Exception e) {
            canvas.drawColor(Color.BLACK);
            return;
        }

        if (inventoryUI.isVisible()) {
            inventoryUI.draw(canvas, player.getInventory());
        } else {
            try { hud.draw(canvas, player); } catch (Exception ignored) {}
            try { touchController.draw(canvas); } catch (Exception ignored) {}
        }

        if (showDebug && gameLoop != null) {
            canvas.drawText("FPS: " + gameLoop.getFps(), 20, 60, debugPaint);
            canvas.drawText(String.format("XYZ: %.1f / %.1f / %.1f",
                    player.getX(), player.getY(), player.getZ()), 20, 100, debugPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Player player = gameEngine.getPlayer();
        World world = gameEngine.getWorld();
        if (player == null || world == null) return true;
        return touchController.handleTouch(event, player, world, inventoryUI);
    }
}
