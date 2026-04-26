package com.minecraft2.android.physics;

import com.minecraft2.android.block.BlockType;
import com.minecraft2.android.engine.GameEngine;
import com.minecraft2.android.entity.Entity;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.world.World;

public class PhysicsEngine {

    private static final float FRICTION = 0.85f;
    private static final float AIR_RESISTANCE = 0.98f;
    private static final float WATER_RESISTANCE = 0.6f;

    public void update(Player player, World world, float deltaTime) {
        if (player.isFlying()) {
            player.setVelX(player.getVelX() * FRICTION);
            player.setVelZ(player.getVelZ() * FRICTION);
        } else {
            applyGravity(player, world, deltaTime);
        }

        moveWithCollision(player, world, deltaTime);
        applyFriction(player, world);
    }

    private void applyGravity(Entity entity, World world, float deltaTime) {
        BlockType blockAtFeet = world.getBlock((int) entity.getX(), (int) entity.getY(), (int) entity.getZ());
        BlockType blockAboveFeet = world.getBlock((int) entity.getX(), (int)(entity.getY() + 0.5f), (int) entity.getZ());

        if (blockAtFeet == BlockType.WATER || blockAboveFeet == BlockType.WATER) {
            float velY = entity.getVelY() + GameEngine.GRAVITY * deltaTime * 0.3f;
            entity.setVelY(Math.max(-5f, velY));
        } else {
            float velY = entity.getVelY() + GameEngine.GRAVITY * deltaTime;
            entity.setVelY(Math.max(GameEngine.TERMINAL_VELOCITY, velY));
        }
    }

    private void moveWithCollision(Player player, World world, float deltaTime) {
        float dx = player.getVelX() * deltaTime;
        float dy = player.getVelY() * deltaTime;
        float dz = player.getVelZ() * deltaTime;

        // X movement
        if (dx != 0) {
            float newX = player.getX() + dx;
            if (!collidesWithWorld(newX, player.getY(), player.getZ(), player, world)) {
                player.setX(newX);
            } else {
                player.setVelX(0);
            }
        }

        // Z movement
        if (dz != 0) {
            float newZ = player.getZ() + dz;
            if (!collidesWithWorld(player.getX(), player.getY(), newZ, player, world)) {
                player.setZ(newZ);
            } else {
                player.setVelZ(0);
            }
        }

        // Y movement
        if (dy != 0) {
            float newY = player.getY() + dy;
            if (!collidesWithWorld(player.getX(), newY, player.getZ(), player, world)) {
                player.setY(newY);
                player.setOnGround(false);
            } else {
                if (dy < 0) {
                    player.setOnGround(true);
                    player.setY((float) Math.floor(player.getY() + dy) + 1f);
                } else {
                    player.setY((float) Math.floor(newY));
                }
                player.setVelY(0);
            }
        }

        // Floor clamp
        if (player.getY() < 0) {
            player.setY(0);
            player.setVelY(0);
            player.setOnGround(true);
        }
    }

    private boolean collidesWithWorld(float x, float y, float z, Player player, World world) {
        float halfW = 0.3f;
        float height = 1.8f;

        int minBlockX = (int) Math.floor(x - halfW);
        int maxBlockX = (int) Math.floor(x + halfW);
        int minBlockY = (int) Math.floor(y);
        int maxBlockY = (int) Math.floor(y + height - 0.01f);
        int minBlockZ = (int) Math.floor(z - halfW);
        int maxBlockZ = (int) Math.floor(z + halfW);

        for (int bx = minBlockX; bx <= maxBlockX; bx++) {
            for (int by = minBlockY; by <= maxBlockY; by++) {
                for (int bz = minBlockZ; bz <= maxBlockZ; bz++) {
                    BlockType block = world.getBlock(bx, by, bz);
                    if (block != null && block.isSolid() && !block.isLiquid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void applyFriction(Player player, World world) {
        BlockType blockUnder = world.getBlock((int) player.getX(), (int)(player.getY() - 0.1f), (int) player.getZ());
        float friction = FRICTION;

        if (blockUnder == BlockType.WATER || blockUnder == BlockType.LAVA) {
            friction = WATER_RESISTANCE;
        } else if (blockUnder == BlockType.ICE) {
            friction = 0.98f;
        } else if (blockUnder == BlockType.SOUL_SAND) {
            friction = 0.5f;
        }

        if (player.isOnGround()) {
            player.setVelX(player.getVelX() * friction);
            player.setVelZ(player.getVelZ() * friction);
        } else {
            player.setVelX(player.getVelX() * AIR_RESISTANCE);
            player.setVelZ(player.getVelZ() * AIR_RESISTANCE);
        }
    }
}
