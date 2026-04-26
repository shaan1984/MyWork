package com.minecraft2.android.world;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.minecraft2.android.block.BlockType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ParticleSystem {

    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random();
    private final Paint paint = new Paint();

    public void spawnBreakParticles(int x, int y, int z, BlockType block) {
        int color = block != null ? block.getColor() : Color.GRAY;
        for (int i = 0; i < 8; i++) {
            particles.add(new Particle(x + 0.5f, y + 0.5f, z,
                    (random.nextFloat() - 0.5f) * 4,
                    1 + random.nextFloat() * 3,
                    (random.nextFloat() - 0.5f) * 2,
                    color, 0.5f + random.nextFloat() * 0.3f));
        }
    }

    public void spawnExplosionParticles(float x, float y, float z) {
        for (int i = 0; i < 30; i++) {
            float angle = random.nextFloat() * 360;
            float speed = 2 + random.nextFloat() * 8;
            particles.add(new Particle(x, y, z,
                    (float) Math.cos(Math.toRadians(angle)) * speed,
                    2 + random.nextFloat() * 5,
                    (float) Math.sin(Math.toRadians(angle)) * speed,
                    Color.rgb(255, 100 + random.nextInt(100), 0),
                    0.8f + random.nextFloat() * 0.5f));
        }
    }

    public void spawnBloodParticles(float x, float y, float z) {
        for (int i = 0; i < 6; i++) {
            particles.add(new Particle(x, y, z,
                    (random.nextFloat() - 0.5f) * 3,
                    1 + random.nextFloat() * 2,
                    (random.nextFloat() - 0.5f) * 3,
                    Color.RED, 0.4f + random.nextFloat() * 0.2f));
        }
    }

    public void spawnGunSmoke(float x, float y, float z) {
        for (int i = 0; i < 4; i++) {
            int gray = 180 + random.nextInt(60);
            particles.add(new Particle(x, y, z,
                    (random.nextFloat() - 0.5f) * 1.5f,
                    0.5f + random.nextFloat(),
                    (random.nextFloat() - 0.5f) * 1.5f,
                    Color.rgb(gray, gray, gray), 0.6f + random.nextFloat() * 0.4f));
        }
    }

    public void spawnCrystalParticles(float x, float y, float z) {
        for (int i = 0; i < 5; i++) {
            particles.add(new Particle(x, y, z,
                    (random.nextFloat() - 0.5f) * 2,
                    1 + random.nextFloat() * 2,
                    (random.nextFloat() - 0.5f) * 2,
                    Color.rgb(150 + random.nextInt(100), 200 + random.nextInt(55), 255),
                    0.5f + random.nextFloat() * 0.5f));
        }
    }

    public void update(float deltaTime) {
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.update(deltaTime);
            if (p.isDead()) it.remove();
        }
        if (particles.size() > 500) {
            particles.subList(0, particles.size() - 500).clear();
        }
    }

    public void render(Canvas canvas, float playerX, float playerY, int screenCX, int screenCY, int blockSize) {
        for (Particle p : particles) {
            int sx = screenCX + (int)((p.x - playerX) * blockSize);
            int sy = screenCY - (int)((p.y - playerY) * blockSize);
            if (sx < -20 || sx > screenCX * 2 + 20) continue;
            if (sy < -20 || sy > screenCY * 2 + 20) continue;
            paint.setColor(p.color);
            paint.setAlpha((int)(255 * p.life / p.maxLife));
            canvas.drawCircle(sx, sy, 3 * p.scale, paint);
        }
    }

    private static class Particle {
        float x, y, z, vx, vy, vz;
        int color;
        float life, maxLife, scale;

        Particle(float x, float y, float z, float vx, float vy, float vz, int color, float life) {
            this.x = x; this.y = y; this.z = z;
            this.vx = vx; this.vy = vy; this.vz = vz;
            this.color = color;
            this.life = this.maxLife = life;
            this.scale = 1f;
        }

        void update(float dt) {
            x += vx * dt;
            y += vy * dt;
            z += vz * dt;
            vy -= 9.8f * dt;
            life -= dt;
        }

        boolean isDead() { return life <= 0; }
    }
}
