package com.minecraft2.android.furniture;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.minecraft2.android.entity.Player;

public class Furniture {

    private final FurnitureType type;
    private float x, y, z;
    private float rotation; // 0, 90, 180, 270 degrees
    private boolean inUse = false;
    private Player occupant = null;
    private final Paint paint = new Paint();

    public Furniture(FurnitureType type, float x, float y, float z, float rotation) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
        paint.setAntiAlias(true);
    }

    public void interact(Player player) {
        if (type.isSeat()) {
            if (!inUse) {
                inUse = true;
                occupant = player;
                player.setX(x + 0.5f);
                player.setZ(z + 0.5f);
                player.setVelX(0);
                player.setVelZ(0);
            }
        } else if (type.isBed()) {
            // Set spawn point and sleep
            player.heal(5f);
        }
    }

    public void stopUsing() {
        inUse = false;
        occupant = null;
    }

    public void draw(Canvas canvas, int screenX, int screenY, int blockSize) {
        float w = type.getWidthBlocks() * blockSize;
        float h = type.getHeightBlocks() * blockSize;

        paint.setColor(type.getColor());
        RectF rect = new RectF(screenX, screenY - h, screenX + w, screenY);
        canvas.drawRoundRect(rect, 4, 4, paint);

        // Highlight/shadow
        paint.setColor(android.graphics.Color.argb(60, 255, 255, 255));
        canvas.drawLine(screenX, screenY - h, screenX + w, screenY - h, paint);
        canvas.drawLine(screenX, screenY - h, screenX, screenY, paint);

        paint.setColor(android.graphics.Color.argb(60, 0, 0, 0));
        canvas.drawLine(screenX + w, screenY - h, screenX + w, screenY, paint);
        canvas.drawLine(screenX, screenY, screenX + w, screenY, paint);

        drawFurnitureDetails(canvas, screenX, screenY, blockSize, (int)w, (int)h);
    }

    private void drawFurnitureDetails(Canvas canvas, int sx, int sy, int bs, int w, int h) {
        paint.setColor(android.graphics.Color.argb(80, 0, 0, 0));
        switch (type) {
            case CHAIR:
                canvas.drawRect(sx, sy - h, sx + w * 0.15f, sy - h * 0.4f, paint);
                canvas.drawRect(sx + w * 0.85f, sy - h, sx + w, sy - h * 0.4f, paint);
                break;
            case BED:
                paint.setColor(android.graphics.Color.rgb(240, 240, 250));
                canvas.drawRect(sx + 4, sy - h + 4, sx + w - 4, sy - h * 0.35f, paint);
                paint.setColor(android.graphics.Color.rgb(180, 80, 80));
                canvas.drawRect(sx + 4, sy - h * 0.35f, sx + w - 4, sy - 4, paint);
                break;
            case LAMP:
                paint.setColor(android.graphics.Color.rgb(255, 240, 180));
                canvas.drawCircle(sx + w / 2f, sy - h + 10, w * 0.4f, paint);
                paint.setColor(android.graphics.Color.argb(60, 255, 220, 100));
                canvas.drawCircle(sx + w / 2f, sy - h / 2f, w * 0.8f, paint);
                break;
            case TABLE:
                paint.setColor(android.graphics.Color.rgb(160, 120, 70));
                canvas.drawRect(sx - 4, sy - h, sx + w + 4, sy - h + 8, paint);
                break;
            case TELEVISION:
                paint.setColor(android.graphics.Color.rgb(20, 60, 120));
                canvas.drawRect(sx + 6, sy - h + 6, sx + w - 6, sy - 16, paint);
                break;
            case FIREPLACE:
                paint.setColor(android.graphics.Color.rgb(255, 100, 20));
                canvas.drawRect(sx + w/4, sy - h/2, sx + w * 3/4, sy - 4, paint);
                paint.setColor(android.graphics.Color.rgb(255, 200, 20));
                canvas.drawRect(sx + w/3, sy - h/2 - 8, sx + w * 2/3, sy - h/4, paint);
                break;
            default: break;
        }
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public FurnitureType getType() { return type; }
    public float getRotation() { return rotation; }
    public boolean isInUse() { return inUse; }
}
