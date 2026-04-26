package com.minecraft2.android.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import com.minecraft2.android.block.BlockType;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.entity.mob.Mob;
import com.minecraft2.android.entity.boss.Boss;
import com.minecraft2.android.furniture.Furniture;
import com.minecraft2.android.world.Chunk;
import com.minecraft2.android.world.World;
import java.util.List;

public class Renderer {

    private final Context context;
    private int screenW, screenH;
    private final Paint blockPaint = new Paint();
    private final Paint skyPaint = new Paint();
    private final Paint entityPaint = new Paint();
    private final Paint shadowPaint = new Paint();
    private static final int BLOCK_SIZE_BASE = 48;
    private static final int RENDER_DISTANCE = 24;

    public Renderer(Context context) {
        this.context = context;
        blockPaint.setAntiAlias(false);
        entityPaint.setAntiAlias(true);
        shadowPaint.setColor(Color.argb(80, 0, 0, 0));
        shadowPaint.setAntiAlias(true);
    }

    public void init(int w, int h) {
        screenW = w;
        screenH = h;
    }

    public void resize(int w, int h) {
        screenW = w;
        screenH = h;
    }

    public void render(Canvas canvas, World world, Player player) {
        drawSky(canvas, world);
        drawWorld(canvas, world, player);
        drawEntities(canvas, world, player);
        drawFurniture(canvas, world, player);
        drawParticles(canvas, world, player);
        drawCrosshair(canvas);
    }

    private void drawSky(Canvas canvas, World world) {
        int topColor, bottomColor;
        float timeOfDay = world.getTimeOfDay();
        if (timeOfDay < 0.25f || timeOfDay > 0.75f) {
            topColor = Color.rgb(5, 5, 20);
            bottomColor = Color.rgb(10, 10, 40);
        } else if (timeOfDay < 0.3f || timeOfDay > 0.7f) {
            topColor = Color.rgb(255, 100, 30);
            bottomColor = Color.rgb(255, 160, 50);
        } else {
            topColor = Color.rgb(30, 120, 220);
            bottomColor = Color.rgb(120, 180, 255);
        }

        LinearGradient skyGradient = new LinearGradient(0, 0, 0, screenH * 0.6f,
                topColor, bottomColor, Shader.TileMode.CLAMP);
        skyPaint.setShader(skyGradient);
        canvas.drawRect(0, 0, screenW, screenH, skyPaint);

        if (timeOfDay < 0.25f || timeOfDay > 0.75f) {
            drawStars(canvas);
        }
    }

    private void drawStars(Canvas canvas) {
        Paint starPaint = new Paint();
        starPaint.setColor(Color.WHITE);
        starPaint.setAlpha(200);
        int[] starPositions = {50, 30, 150, 80, 300, 20, 450, 60, 600, 40, 750, 90,
                100, 15, 250, 55, 400, 10, 550, 75, 700, 35, 800, 25};
        for (int i = 0; i < starPositions.length; i += 2) {
            canvas.drawCircle(starPositions[i], starPositions[i+1], 1.5f, starPaint);
        }
    }

    private void drawWorld(Canvas canvas, World world, Player player) {
        int playerBlockX = (int) Math.floor(player.getX());
        int playerBlockY = (int) Math.floor(player.getY());
        int playerBlockZ = (int) Math.floor(player.getZ());

        int blockSize = BLOCK_SIZE_BASE;
        int halfScreen = screenW / 2;
        int halfScreenH = screenH / 2;

        int startX = playerBlockX - RENDER_DISTANCE;
        int endX = playerBlockX + RENDER_DISTANCE;
        int startY = Math.max(0, playerBlockY - 16);
        int endY = Math.min(World.HEIGHT, playerBlockY + 16);

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                BlockType block = world.getBlock(x, y, playerBlockZ);
                if (block == null || block == BlockType.AIR) continue;

                int screenX = halfScreen + (x - playerBlockX) * blockSize;
                int screenY = halfScreenH - (y - playerBlockY) * blockSize;

                if (screenX + blockSize < 0 || screenX > screenW) continue;
                if (screenY + blockSize < 0 || screenY > screenH) continue;

                RectF rect = new RectF(screenX, screenY, screenX + blockSize, screenY + blockSize);
                blockPaint.setColor(block.getColor());
                canvas.drawRect(rect, blockPaint);

                blockPaint.setColor(Color.argb(40, 0, 0, 0));
                canvas.drawRect(rect, blockPaint);

                blockPaint.setColor(Color.argb(20, 255, 255, 255));
                canvas.drawLine(screenX, screenY, screenX + blockSize, screenY, blockPaint);
                canvas.drawLine(screenX, screenY, screenX, screenY + blockSize, blockPaint);
            }
        }
    }

    private void drawEntities(Canvas canvas, World world, Player player) {
        List<Mob> mobs = world.getNearbyMobs(player.getX(), player.getY(), player.getZ(), 30);
        int blockSize = BLOCK_SIZE_BASE;
        int halfScreen = screenW / 2;
        int halfScreenH = screenH / 2;

        for (Mob mob : mobs) {
            int screenX = halfScreen + (int)((mob.getX() - player.getX()) * blockSize);
            int screenY = halfScreenH - (int)((mob.getY() - player.getY()) * blockSize);

            entityPaint.setColor(mob.getType().getColor());
            RectF rect = new RectF(screenX - 16, screenY - 32, screenX + 16, screenY);
            canvas.drawRect(rect, entityPaint);

            drawHealthBar(canvas, screenX, screenY - 40, mob.getHealth(), mob.getMaxHealth());
        }

        for (Boss boss : world.getActiveBosses()) {
            int screenX = halfScreen + (int)((boss.getX() - player.getX()) * blockSize);
            int screenY = halfScreenH - (int)((boss.getY() - player.getY()) * blockSize);

            entityPaint.setColor(boss.getType().getColor());
            RectF rect = new RectF(screenX - 32, screenY - 64, screenX + 32, screenY);
            canvas.drawRect(rect, entityPaint);

            drawBossHealthBar(canvas, boss);
        }
    }

    private void drawHealthBar(Canvas canvas, int x, int y, float hp, float maxHp) {
        Paint hpPaint = new Paint();
        float ratio = Math.max(0, hp / maxHp);
        hpPaint.setColor(Color.RED);
        canvas.drawRect(x - 20, y, x + 20, y + 5, hpPaint);
        hpPaint.setColor(Color.GREEN);
        canvas.drawRect(x - 20, y, x - 20 + 40 * ratio, y + 5, hpPaint);
    }

    private void drawBossHealthBar(Canvas canvas, Boss boss) {
        Paint paint = new Paint();
        int barW = screenW - 200;
        int barX = 100;
        int barY = 60;
        int barH = 24;

        paint.setColor(Color.argb(180, 0, 0, 0));
        canvas.drawRect(barX - 4, barY - 4, barX + barW + 4, barY + barH + 4, paint);

        paint.setColor(Color.rgb(150, 0, 200));
        canvas.drawRect(barX, barY, barX + barW, barY + barH, paint);

        float ratio = Math.max(0, boss.getHealth() / boss.getMaxHealth());
        paint.setColor(Color.rgb(220, 50, 255));
        canvas.drawRect(barX, barY, barX + (int)(barW * ratio), barY + barH, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(18f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(boss.getName() + " — " + (int)boss.getHealth() + "/" + (int)boss.getMaxHealth(),
                screenW / 2f, barY + barH - 4, paint);
    }

    private void drawFurniture(Canvas canvas, World world, Player player) {
        List<Furniture> furnitureList = world.getNearbyFurniture(player.getX(), player.getY(), player.getZ(), 20);
        int blockSize = BLOCK_SIZE_BASE;
        int halfScreen = screenW / 2;
        int halfScreenH = screenH / 2;

        for (Furniture f : furnitureList) {
            int screenX = halfScreen + (int)((f.getX() - player.getX()) * blockSize);
            int screenY = halfScreenH - (int)((f.getY() - player.getY()) * blockSize);
            f.draw(canvas, screenX, screenY, blockSize);
        }
    }

    private void drawParticles(Canvas canvas, World world, Player player) {
        world.getParticleSystem().render(canvas,
                player.getX(), player.getY(), screenW / 2, screenH / 2, BLOCK_SIZE_BASE);
    }

    private void drawCrosshair(Canvas canvas) {
        Paint crosshairPaint = new Paint();
        crosshairPaint.setColor(Color.argb(200, 255, 255, 255));
        crosshairPaint.setStrokeWidth(2f);
        int cx = screenW / 2;
        int cy = screenH / 2;
        canvas.drawLine(cx - 12, cy, cx + 12, cy, crosshairPaint);
        canvas.drawLine(cx, cy - 12, cx, cy + 12, crosshairPaint);
    }
}
