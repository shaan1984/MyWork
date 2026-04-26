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
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderer.init(getWidth(), getHeight());
        hud.init(getWidth(), getHeight());
        inventoryUI.init(getWidth(), getHeight());
        touchController.init(getWidth(), getHeight());

        gameLoop = new GameLoop(gameEngine, this);
        gameEngine.start(gameLoop);
        gameThread = new Thread(gameLoop, "GameThread");
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        renderer.resize(width, height);
        hud.init(width, height);
        touchController.init(width, height);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) return;

        World world = gameEngine.getWorld();
        Player player = gameEngine.getPlayer();

        renderer.render(canvas, world, player);

        if (inventoryUI.isVisible()) {
            inventoryUI.draw(canvas, player.getInventory());
        } else {
            hud.draw(canvas, player);
            touchController.draw(canvas);
        }

        if (showDebug && gameLoop != null) {
            canvas.drawText("FPS: " + gameLoop.getFps(), 20, 60, debugPaint);
            canvas.drawText(String.format("XYZ: %.1f / %.1f / %.1f",
                    player.getX(), player.getY(), player.getZ()), 20, 100, debugPaint);
            canvas.drawText("Biome: " + world.getBiomeAt((int)player.getX(), (int)player.getZ()), 20, 140, debugPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return touchController.handleTouch(event, gameEngine.getPlayer(),
                gameEngine.getWorld(), inventoryUI);
    }
}
