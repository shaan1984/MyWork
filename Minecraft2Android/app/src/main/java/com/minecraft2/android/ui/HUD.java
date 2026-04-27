package com.minecraft2.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.weapon.Gun;

public class HUD {

    private final Context context;
    private int screenW, screenH;
    private final Paint paint = new Paint();
    private final Paint textPaint = new Paint();
    private final Paint barPaint = new Paint();

    private static final int HOTBAR_SLOT_SIZE = 60;
    private static final int HOTBAR_PADDING = 4;
    private static final int BAR_HEIGHT = 10;
    private static final int BAR_WIDTH = 150;

    public HUD(Context context) {
        this.context = context;
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20f);
        textPaint.setColor(Color.WHITE);
        barPaint.setAntiAlias(true);
    }

    public void init(int w, int h) {
        screenW = w;
        screenH = h;
    }

    public void draw(Canvas canvas, Player player) {
        drawHotbar(canvas, player);
        drawStatusBars(canvas, player);
        if (player.getEquippedGun() != null) {
            drawGunHUD(canvas, player.getEquippedGun());
        }
        drawActionBar(canvas, player);
        drawArmor(canvas, player);
    }

    private void drawHotbar(Canvas canvas, Player player) {
        int totalWidth = 9 * (HOTBAR_SLOT_SIZE + HOTBAR_PADDING) - HOTBAR_PADDING;
        int startX = (screenW - totalWidth) / 2;
        int startY = screenH - HOTBAR_SLOT_SIZE - 20;

        // Background
        paint.setColor(Color.argb(150, 0, 0, 0));
        canvas.drawRoundRect(new RectF(startX - 8, startY - 4,
                startX + totalWidth + 8, startY + HOTBAR_SLOT_SIZE + 4), 8, 8, paint);

        for (int i = 0; i < 9; i++) {
            int slotX = startX + i * (HOTBAR_SLOT_SIZE + HOTBAR_PADDING);

            // Slot background
            boolean selected = i == player.getSelectedHotbarSlot();
            paint.setColor(selected ? Color.argb(200, 100, 100, 100) : Color.argb(120, 50, 50, 50));
            canvas.drawRoundRect(new RectF(slotX, startY, slotX + HOTBAR_SLOT_SIZE,
                    startY + HOTBAR_SLOT_SIZE), 4, 4, paint);

            // Item in slot
            ItemStack item = player.getInventory().getHotbarSlot(i);
            if (item != null && !item.isEmpty()) {
                drawItemInSlot(canvas, item, slotX, startY, HOTBAR_SLOT_SIZE);
            }

            // Selection border
            if (selected) {
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2f);
                canvas.drawRoundRect(new RectF(slotX, startY, slotX + HOTBAR_SLOT_SIZE,
                        startY + HOTBAR_SLOT_SIZE), 4, 4, paint);
                paint.setStyle(Paint.Style.FILL);
            }

            // Slot number
            textPaint.setTextSize(14f);
            textPaint.setColor(Color.argb(180, 200, 200, 200));
            canvas.drawText(String.valueOf(i + 1), slotX + 4, startY + 16, textPaint);
        }
    }

    private void drawItemInSlot(Canvas canvas, ItemStack item, int slotX, int slotY, int size) {
        // Draw colored square representing item
        int color = getItemColor(item);
        paint.setColor(color);
        int margin = 8;
        canvas.drawRoundRect(new RectF(slotX + margin, slotY + margin,
                slotX + size - margin, slotY + size - margin), 3, 3, paint);

        // Count
        if (item.getCount() > 1) {
            textPaint.setTextSize(16f);
            textPaint.setColor(Color.WHITE);
            String countStr = String.valueOf(item.getCount());
            canvas.drawText(countStr, slotX + size - countStr.length() * 10 - 4,
                    slotY + size - 4, textPaint);
        }

        // Durability bar
        if (item.getType().getDurability() > 0 && item.getDurability() < item.getType().getDurability()) {
            float ratio = (float) item.getDurability() / item.getType().getDurability();
            int barY = slotY + size - 6;
            paint.setColor(Color.BLACK);
            canvas.drawRect(slotX + 2, barY, slotX + size - 2, barY + 3, paint);
            paint.setColor(ratio > 0.5f ? Color.GREEN : (ratio > 0.25f ? Color.YELLOW : Color.RED));
            canvas.drawRect(slotX + 2, barY, slotX + 2 + (int)((size - 4) * ratio), barY + 3, paint);
        }
    }

    private int getItemColor(ItemStack item) {
        switch (item.getType()) {
            case OAK_PLANKS: case OAK_LOG: return Color.rgb(150, 110, 60);
            case STONE: return Color.rgb(120, 120, 120);
            case IRON_INGOT: case IRON_PICKAXE: case IRON_SWORD: return Color.rgb(200, 200, 210);
            case DIAMOND: case DIAMOND_SWORD: case DIAMOND_PICKAXE: return Color.rgb(50, 200, 220);
            case GOLD_INGOT: case GOLD_SWORD: return Color.rgb(220, 180, 50);
            case TITANIUM_INGOT: case TITANIUM_SWORD: return Color.rgb(150, 180, 220);
            case ADAMANTITE_INGOT: case ADAMANTITE_SWORD: return Color.rgb(180, 50, 180);
            case CRYSTAL_SHARD: case CRYSTAL_BLADE: return Color.rgb(180, 240, 255);
            case VOID_ESSENCE: case VOID_BLADE: return Color.rgb(20, 0, 50);
            case PISTOL: return Color.rgb(60, 60, 70);
            case SHOTGUN: return Color.rgb(80, 70, 60);
            case RIFLE: return Color.rgb(70, 60, 50);
            case SNIPER_RIFLE: return Color.rgb(50, 60, 70);
            case RPG: return Color.rgb(90, 60, 40);
            case MINIGUN: return Color.rgb(80, 80, 90);
            case BREAD: case BEEF: case PORK: return Color.rgb(200, 140, 80);
            case TORCH: return Color.rgb(255, 200, 50);
            case TNT: return Color.rgb(200, 60, 60);
            default: return Color.rgb(160, 120, 80);
        }
    }

    private void drawStatusBars(Canvas canvas, Player player) {
        int barX = 20;
        int barY = screenH - 100;
        int spacing = 18;

        // Health bar
        drawBar(canvas, barX, barY, BAR_WIDTH, BAR_HEIGHT,
                player.getHealth() / player.getMaxHealth(), Color.RED, "♥ " + (int)player.getHealth());

        // Hunger bar
        drawBar(canvas, barX, barY + spacing, BAR_WIDTH, BAR_HEIGHT,
                player.getHunger() / player.getMaxHunger(), Color.rgb(180, 100, 30),
                "🍗 " + (int)player.getHunger());

        // XP bar
        drawBar(canvas, barX, barY + spacing * 2, BAR_WIDTH, BAR_HEIGHT,
                (player.getXp() % 100) / 100f, Color.rgb(50, 220, 50),
                "XP " + player.getXpLevel());

        // Armor
        if (player.getArmor() > 0) {
            drawBar(canvas, barX, barY + spacing * 3, BAR_WIDTH, BAR_HEIGHT,
                    Math.min(1f, player.getArmor() / 20f), Color.rgb(150, 150, 200),
                    "🛡 " + player.getArmor());
        }
    }

    private void drawBar(Canvas canvas, int x, int y, int w, int h,
                          float ratio, int color, String label) {
        // Background
        paint.setColor(Color.argb(150, 0, 0, 0));
        canvas.drawRoundRect(new RectF(x, y, x + w, y + h), 3, 3, paint);

        // Fill
        paint.setColor(color);
        canvas.drawRoundRect(new RectF(x, y, x + w * Math.max(0, Math.min(1, ratio)), y + h), 3, 3, paint);

        // Border
        paint.setColor(Color.argb(100, 255, 255, 255));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1f);
        canvas.drawRoundRect(new RectF(x, y, x + w, y + h), 3, 3, paint);
        paint.setStyle(Paint.Style.FILL);

        // Label
        textPaint.setTextSize(14f);
        textPaint.setColor(Color.WHITE);
        canvas.drawText(label, x + w + 8, y + h - 1, textPaint);
    }

    private void drawGunHUD(Canvas canvas, Gun gun) {
        int gunX = screenW - 220;
        int gunY = screenH - 80;

        paint.setColor(Color.argb(160, 0, 0, 0));
        canvas.drawRoundRect(new RectF(gunX - 8, gunY - 8, gunX + 200, gunY + 40), 8, 8, paint);

        textPaint.setTextSize(32f);
        textPaint.setColor(Color.WHITE);
        canvas.drawText(gun.getCurrentMag() + " / " + gun.getReserveAmmo(), gunX, gunY + 30, textPaint);

        textPaint.setTextSize(14f);
        textPaint.setColor(Color.rgb(200, 200, 200));
        canvas.drawText(gun.getType().getDisplayName(), gunX, gunY, textPaint);

        if (gun.isReloading()) {
            float progress = gun.getReloadProgress();
            paint.setColor(Color.YELLOW);
            canvas.drawRect(gunX, gunY + 34, gunX + 192 * progress, gunY + 38, paint);
            textPaint.setTextSize(12f);
            textPaint.setColor(Color.YELLOW);
            canvas.drawText("RELOADING...", gunX + 60, gunY + 52, textPaint);
        }

        if (gun.getCurrentMag() == 0) {
            textPaint.setTextSize(16f);
            textPaint.setColor(Color.RED);
            canvas.drawText("EMPTY - Press R to reload", gunX - 20, gunY + 60, textPaint);
        }
    }

    private void drawActionBar(Canvas canvas, Player player) {
        // Show item name when changed
        ItemStack selected = player.getSelectedItem();
        if (selected != null && !selected.isEmpty()) {
            textPaint.setTextSize(18f);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.argb(100, 0, 0, 0));
            float tw = textPaint.measureText(selected.getType().getDisplayName());
            canvas.drawRoundRect(new RectF(screenW/2f - tw/2 - 8, screenH - 90,
                    screenW/2f + tw/2 + 8, screenH - 68), 6, 6, paint);
            canvas.drawText(selected.getType().getDisplayName(), screenW / 2f, screenH - 74, textPaint);
            textPaint.setTextAlign(Paint.Align.LEFT);
        }
    }

    private void drawArmor(Canvas canvas, Player player) {
        // Display equipped armor name
        if (!player.getEquippedArmorSet().equals("none")) {
            textPaint.setTextSize(14f);
            textPaint.setColor(Color.rgb(150, 200, 255));
            canvas.drawText("Armor: " + player.getEquippedArmorSet(), 20, 40, textPaint);
        }
    }
}
