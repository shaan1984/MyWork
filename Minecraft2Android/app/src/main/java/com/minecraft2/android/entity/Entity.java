package com.minecraft2.android.entity;

import com.minecraft2.android.engine.GameEngine;
import com.minecraft2.android.physics.AABB;
import com.minecraft2.android.world.World;

public abstract class Entity {

    protected float x, y, z;
    protected float velX, velY, velZ;
    protected float yaw, pitch;
    protected float health, maxHealth;
    protected boolean onGround;
    protected boolean dead;
    protected float deathTimer;
    protected String id;
    protected AABB boundingBox;
    protected float width, height;

    public Entity(float x, float y, float z, float health, float width, float height) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.health = health;
        this.maxHealth = health;
        this.width = width;
        this.height = height;
        this.boundingBox = new AABB(x - width/2, y, z - width/2, x + width/2, y + height, z + width/2);
        this.id = java.util.UUID.randomUUID().toString();
    }

    public abstract void update(float deltaTime, World world);

    public void damage(float amount) {
        if (dead) return;
        health -= amount;
        if (health <= 0) {
            health = 0;
            dead = true;
            onDeath();
        }
    }

    public void heal(float amount) {
        health = Math.min(maxHealth, health + amount);
    }

    protected void onDeath() {}

    public void applyGravity(float deltaTime) {
        velY = Math.max(GameEngine.TERMINAL_VELOCITY, velY + GameEngine.GRAVITY * deltaTime);
    }

    public void updateBoundingBox() {
        boundingBox.set(x - width/2, y, z - width/2, x + width/2, y + height, z + width/2);
    }

    protected static float distanceTo(Entity a, Entity b) {
        float dx = a.x - b.x, dy = a.y - b.y, dz = a.z - b.z;
        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    protected static float distanceTo2D(Entity a, Entity b) {
        float dx = a.x - b.x, dz = a.z - b.z;
        return (float) Math.sqrt(dx*dx + dz*dz);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }
    public float getVelX() { return velX; }
    public float getVelY() { return velY; }
    public float getVelZ() { return velZ; }
    public void setVelX(float vx) { this.velX = vx; }
    public void setVelY(float vy) { this.velY = vy; }
    public void setVelZ(float vz) { this.velZ = vz; }
    public float getHealth() { return health; }
    public float getMaxHealth() { return maxHealth; }
    public boolean isDead() { return dead; }
    public float getDeathTimer() { return deathTimer; }
    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean g) { this.onGround = g; }
    public AABB getBoundingBox() { return boundingBox; }
    public String getId() { return id; }
}
