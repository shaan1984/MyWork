package com.minecraft2.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import com.minecraft2.android.engine.GameEngine;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.world.World;

public class TouchController {

    private final Context context;
    private final GameEngine gameEngine;

    private int screenW, screenH;
    private final Paint paint = new Paint();
    private final Paint textPaint = new Paint();

    // Joystick
    private static final int JOYSTICK_SIZE = 100;
    private static final int JOYSTICK_DEADZONE = 15;
    private int joystickCenterX, joystickCenterY;
    private int joystickX, joystickY;
    private boolean joystickActive = false;
    private int joystickPointerId = -1;

    // Look area
    private int lookStartX, lookStartY;
    private int lookLastX, lookLastY;
    private boolean lookActive = false;
    private int lookPointerId = -1;

    // Buttons
    private final int BUTTON_SIZE = 70;
    private int jumpBtnX, jumpBtnY;
    private int attackBtnX, attackBtnY;
    private int inventoryBtnX, inventoryBtnY;
    private int sprintBtnX, sprintBtnY;
    private int reloadBtnX, reloadBtnY;
    private int sneakBtnX, sneakBtnY;

    // Block interaction
    private float breakHoldTime = 0;
    private boolean holdingBreak = false;
    private static final float BREAK_HOLD_THRESHOLD = 0.15f;

    public TouchController(Context context, GameEngine gameEngine) {
        this.context = context;
        this.gameEngine = gameEngine;
        paint.setAntiAlias(true);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(16f);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void init(int w, int h) {
        screenW = w;
        screenH = h;

        // Left side - Joystick
        joystickCenterX = JOYSTICK_SIZE + 30;
        joystickCenterY = h - JOYSTICK_SIZE - 30;
        joystickX = joystickCenterX;
        joystickY = joystickCenterY;

        // Right side - Buttons
        int rightBase = w - BUTTON_SIZE - 20;
        int bottomBase = h - BUTTON_SIZE - 20;

        jumpBtnX = rightBase;
        jumpBtnY = bottomBase - BUTTON_SIZE - 10;
        attackBtnX = rightBase - BUTTON_SIZE - 10;
        attackBtnY = bottomBase;
        inventoryBtnX = rightBase;
        inventoryBtnY = bottomBase;
        sprintBtnX = joystickCenterX + JOYSTICK_SIZE + 20;
        sprintBtnY = joystickCenterY;
        sneakBtnX = joystickCenterX + JOYSTICK_SIZE + 20;
        sneakBtnY = joystickCenterY + BUTTON_SIZE + 10;
        reloadBtnX = rightBase - BUTTON_SIZE * 2 - 20;
        reloadBtnY = bottomBase;
    }

    public void draw(Canvas canvas) {
        drawJoystick(canvas);
        drawButton(canvas, jumpBtnX, jumpBtnY, "JUMP", Color.argb(160, 60, 130, 200));
        drawButton(canvas, attackBtnX, attackBtnY, "ATK", Color.argb(160, 200, 60, 60));
        drawButton(canvas, inventoryBtnX, inventoryBtnY, "INV", Color.argb(160, 80, 80, 160));
        drawButton(canvas, sprintBtnX, sprintBtnY, "RUN", Color.argb(140, 60, 160, 60));
        drawButton(canvas, sneakBtnX, sneakBtnY, "SNEAK", Color.argb(140, 120, 100, 60));
        drawButton(canvas, reloadBtnX, reloadBtnY, "RELOAD", Color.argb(140, 160, 140, 40));
    }

    private void drawJoystick(Canvas canvas) {
        // Outer ring
        paint.setColor(Color.argb(80, 200, 200, 200));
        canvas.drawCircle(joystickCenterX, joystickCenterY, JOYSTICK_SIZE, paint);

        // Inner ring
        paint.setColor(Color.argb(60, 255, 255, 255));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        canvas.drawCircle(joystickCenterX, joystickCenterY, JOYSTICK_SIZE, paint);
        paint.setStyle(Paint.Style.FILL);

        // Thumb
        paint.setColor(Color.argb(180, 150, 150, 200));
        canvas.drawCircle(joystickX, joystickY, JOYSTICK_SIZE * 0.45f, paint);
    }

    private void drawButton(Canvas canvas, int x, int y, String label, int color) {
        paint.setColor(color);
        canvas.drawRoundRect(new android.graphics.RectF(x, y, x + BUTTON_SIZE, y + BUTTON_SIZE), 12, 12, paint);
        paint.setColor(Color.argb(80, 255, 255, 255));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);
        canvas.drawRoundRect(new android.graphics.RectF(x, y, x + BUTTON_SIZE, y + BUTTON_SIZE), 12, 12, paint);
        paint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(label.length() > 4 ? 13f : 16f);
        canvas.drawText(label, x + BUTTON_SIZE / 2f, y + BUTTON_SIZE / 2f + 6, textPaint);
    }

    public boolean handleTouch(MotionEvent event, Player player, World world, InventoryUI inventoryUI) {
        if (inventoryUI.isVisible()) return inventoryUI.handleTouch(event, player.getInventory());

        int action = event.getActionMasked();
        int pointerId = event.getPointerId(event.getActionIndex());
        float tx = event.getX(event.getActionIndex());
        float ty = event.getY(event.getActionIndex());

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            handleTouchDown(tx, ty, pointerId, player, world, inventoryUI);
        } else if (action == MotionEvent.ACTION_MOVE) {
            for (int i = 0; i < event.getPointerCount(); i++) {
                int pid = event.getPointerId(i);
                float px = event.getX(i), py = event.getY(i);
                if (pid == joystickPointerId) updateJoystick(px, py, player);
                else if (pid == lookPointerId) updateLook(px, py, player);
            }
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            handleTouchUp(pointerId, player);
        }
        return true;
    }

    private void handleTouchDown(float tx, float ty, int pid, Player player, World world, InventoryUI inventoryUI) {
        // Joystick area
        float jdx = tx - joystickCenterX, jdy = ty - joystickCenterY;
        if (Math.sqrt(jdx*jdx + jdy*jdy) < JOYSTICK_SIZE * 1.5f) {
            joystickActive = true;
            joystickPointerId = pid;
            updateJoystick(tx, ty, player);
            return;
        }

        // Buttons
        if (isInButton(tx, ty, jumpBtnX, jumpBtnY)) { player.jumpPressed = true; return; }
        if (isInButton(tx, ty, attackBtnX, attackBtnY)) {
            handleAttack(player, world);
            return;
        }
        if (isInButton(tx, ty, inventoryBtnX, inventoryBtnY)) { inventoryUI.toggle(); return; }
        if (isInButton(tx, ty, sprintBtnX, sprintBtnY)) {
            player.setSprinting(!player.isSprinting());
            return;
        }
        if (isInButton(tx, ty, sneakBtnX, sneakBtnY)) {
            player.setSneaking(!player.isSneaking());
            return;
        }
        if (isInButton(tx, ty, reloadBtnX, reloadBtnY)) {
            if (player.getEquippedGun() != null) player.getEquippedGun().startReload();
            return;
        }

        // Look area
        if (tx > screenW / 2f && !lookActive) {
            lookActive = true;
            lookPointerId = pid;
            lookStartX = lookLastX = (int) tx;
            lookStartY = lookLastY = (int) ty;
        }
    }

    private void handleTouchUp(int pid, Player player) {
        if (pid == joystickPointerId) {
            joystickActive = false;
            joystickPointerId = -1;
            joystickX = joystickCenterX;
            joystickY = joystickCenterY;
            player.moveForward = player.moveBack = player.moveLeft = player.moveRight = false;
        }
        if (pid == lookPointerId) {
            lookActive = false;
            lookPointerId = -1;
        }
        player.jumpPressed = false;
    }

    private void updateJoystick(float tx, float ty, Player player) {
        float dx = tx - joystickCenterX, dy = ty - joystickCenterY;
        float dist = (float) Math.sqrt(dx*dx + dy*dy);
        if (dist > JOYSTICK_SIZE) {
            dx = dx / dist * JOYSTICK_SIZE;
            dy = dy / dist * JOYSTICK_SIZE;
        }
        joystickX = joystickCenterX + (int) dx;
        joystickY = joystickCenterY + (int) dy;

        float normX = dx / JOYSTICK_SIZE;
        float normY = dy / JOYSTICK_SIZE;
        float dead = (float) JOYSTICK_DEADZONE / JOYSTICK_SIZE;

        player.moveRight = normX > dead;
        player.moveLeft = normX < -dead;
        player.moveForward = normY < -dead;
        player.moveBack = normY > dead;
    }

    private void updateLook(float tx, float ty, Player player) {
        float ddx = tx - lookLastX, ddy = ty - lookLastY;
        player.lookDX = ddx;
        player.lookDY = ddy;
        lookLastX = (int) tx;
        lookLastY = (int) ty;
    }

    private void handleAttack(Player player, World world) {
        if (player.getEquippedGun() != null) {
            player.getEquippedGun().shoot(player, world);
        } else {
            // Melee attack / break block
            com.minecraft2.android.item.ItemStack item = player.getSelectedItem();
            float damage = item != null ? item.getType().getMeleeDamage() : 1;
            var mobs = world.getNearbyMobs(player.getX(), player.getY(), player.getZ(), 3f);
            if (!mobs.isEmpty()) {
                mobs.get(0).damage(damage);
                world.getParticleSystem().spawnBloodParticles(mobs.get(0).getX(),
                        mobs.get(0).getY() + 1, mobs.get(0).getZ());
            }
        }
    }

    private boolean isInButton(float tx, float ty, int bx, int by) {
        return tx >= bx && tx <= bx + BUTTON_SIZE && ty >= by && ty <= by + BUTTON_SIZE;
    }
}
