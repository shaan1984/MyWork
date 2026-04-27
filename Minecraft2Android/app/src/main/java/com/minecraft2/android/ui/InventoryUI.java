package com.minecraft2.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.minecraft2.android.inventory.Inventory;
import com.minecraft2.android.item.ItemStack;

public class InventoryUI {

    private final Context context;
    private int screenW, screenH;
    private boolean visible = false;
    private final Paint paint = new Paint();
    private final Paint textPaint = new Paint();

    private static final int SLOT_SIZE = 56;
    private static final int SLOT_PADDING = 4;
    private static final int COLS = 9;
    private static final int ROWS = 4;

    private ItemStack heldItem = null;
    private int dragX, dragY;
    private int selectedSlot = -1;

    public InventoryUI(Context context) {
        this.context = context;
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(18f);
        textPaint.setColor(Color.WHITE);
    }

    public void init(int w, int h) {
        screenW = w;
        screenH = h;
    }

    public void show() { visible = true; }
    public void hide() { visible = false; heldItem = null; }
    public void toggle() { if (visible) hide(); else show(); }
    public boolean isVisible() { return visible; }

    public void draw(Canvas canvas, Inventory inventory) {
        if (!visible) return;

        int gridW = COLS * (SLOT_SIZE + SLOT_PADDING) - SLOT_PADDING;
        int gridH = ROWS * (SLOT_SIZE + SLOT_PADDING) - SLOT_PADDING;
        int startX = (screenW - gridW) / 2;
        int startY = (screenH - gridH) / 2 - 40;

        // Dark overlay
        paint.setColor(Color.argb(160, 0, 0, 0));
        canvas.drawRect(0, 0, screenW, screenH, paint);

        // Panel background
        paint.setColor(Color.argb(220, 30, 30, 30));
        canvas.drawRoundRect(new RectF(startX - 20, startY - 50,
                startX + gridW + 20, startY + gridH + 80), 12, 12, paint);

        // Title
        textPaint.setTextSize(24f);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Inventory", screenW / 2f, startY - 20, textPaint);
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Draw inventory slots
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int slotIndex = row * COLS + col;
                int sx = startX + col * (SLOT_SIZE + SLOT_PADDING);
                int sy = startY + row * (SLOT_SIZE + SLOT_PADDING);

                drawSlot(canvas, sx, sy, inventory.getSlot(slotIndex), slotIndex == selectedSlot);
            }
        }

        // Hotbar separator
        paint.setColor(Color.argb(100, 255, 255, 255));
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.STROKE);
        int hotbarY = startY + 3 * (SLOT_SIZE + SLOT_PADDING) - SLOT_PADDING / 2;
        canvas.drawLine(startX, hotbarY, startX + gridW, hotbarY, paint);
        paint.setStyle(Paint.Style.FILL);

        // Held item
        if (heldItem != null && !heldItem.isEmpty()) {
            paint.setColor(Color.argb(200, 100, 100, 150));
            canvas.drawRoundRect(new RectF(dragX - SLOT_SIZE/2f, dragY - SLOT_SIZE/2f,
                    dragX + SLOT_SIZE/2f, dragY + SLOT_SIZE/2f), 4, 4, paint);
            textPaint.setTextSize(14f);
            canvas.drawText(heldItem.getType().getDisplayName(),
                    dragX - SLOT_SIZE/2f, dragY - SLOT_SIZE/2f - 4, textPaint);
        }

        // Close button
        paint.setColor(Color.rgb(180, 50, 50));
        RectF closeBtn = new RectF(screenW - 60, 20, screenW - 20, 60);
        canvas.drawRoundRect(closeBtn, 6, 6, paint);
        textPaint.setTextSize(22f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("X", screenW - 40, 50, textPaint);
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void drawSlot(Canvas canvas, int x, int y, ItemStack item, boolean selected) {
        paint.setColor(selected ? Color.argb(200, 80, 80, 120) : Color.argb(150, 40, 40, 40));
        canvas.drawRoundRect(new RectF(x, y, x + SLOT_SIZE, y + SLOT_SIZE), 4, 4, paint);

        if (item != null && !item.isEmpty()) {
            // Item color block
            paint.setColor(getColorForItem(item));
            canvas.drawRoundRect(new RectF(x + 6, y + 6, x + SLOT_SIZE - 6, y + SLOT_SIZE - 6), 3, 3, paint);

            // Count
            if (item.getCount() > 1) {
                textPaint.setTextSize(14f);
                textPaint.setColor(Color.WHITE);
                canvas.drawText(String.valueOf(item.getCount()),
                        x + SLOT_SIZE - 20, y + SLOT_SIZE - 4, textPaint);
            }
        }

        // Border
        paint.setColor(selected ? Color.WHITE : Color.argb(80, 200, 200, 200));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.5f);
        canvas.drawRoundRect(new RectF(x, y, x + SLOT_SIZE, y + SLOT_SIZE), 4, 4, paint);
        paint.setStyle(Paint.Style.FILL);
    }

    private int getColorForItem(ItemStack item) {
        // Simplified color mapping
        switch (item.getType()) {
            case IRON_INGOT: return Color.rgb(200, 200, 210);
            case GOLD_INGOT: return Color.rgb(220, 180, 50);
            case DIAMOND: return Color.rgb(50, 200, 220);
            case TITANIUM_INGOT: return Color.rgb(150, 180, 220);
            case ADAMANTITE_INGOT: return Color.rgb(180, 50, 180);
            case CRYSTAL_SHARD: return Color.rgb(180, 240, 255);
            default: return Color.rgb(130, 100, 70);
        }
    }

    public boolean handleTouch(MotionEvent event, Inventory inventory) {
        if (!visible) return false;
        int gridW = COLS * (SLOT_SIZE + SLOT_PADDING) - SLOT_PADDING;
        int startX = (screenW - gridW) / 2;
        int startY = (screenH - (ROWS * (SLOT_SIZE + SLOT_PADDING) - SLOT_PADDING)) / 2 - 40;

        float tx = event.getX(), ty = event.getY();

        // Close button
        if (tx >= screenW - 60 && tx <= screenW - 20 && ty >= 20 && ty <= 60) {
            if (event.getAction() == MotionEvent.ACTION_UP) hide();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            int col = (int)((tx - startX) / (SLOT_SIZE + SLOT_PADDING));
            int row = (int)((ty - startY) / (SLOT_SIZE + SLOT_PADDING));
            if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                int slotIndex = row * COLS + col;
                handleSlotClick(inventory, slotIndex);
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            dragX = (int) tx;
            dragY = (int) ty;
        }
        return true;
    }

    private void handleSlotClick(Inventory inventory, int slotIndex) {
        if (heldItem == null) {
            ItemStack slot = inventory.getSlot(slotIndex);
            if (slot != null && !slot.isEmpty()) {
                heldItem = inventory.removeSlot(slotIndex);
                selectedSlot = slotIndex;
            }
        } else {
            ItemStack existing = inventory.getSlot(slotIndex);
            if (existing == null || existing.isEmpty()) {
                inventory.setSlot(slotIndex, heldItem);
                heldItem = null;
                selectedSlot = -1;
            } else if (existing.canMergeWith(heldItem)) {
                existing.add(heldItem.getCount());
                heldItem = null;
                selectedSlot = -1;
            } else {
                inventory.setSlot(slotIndex, heldItem);
                heldItem = existing;
            }
        }
    }
}
