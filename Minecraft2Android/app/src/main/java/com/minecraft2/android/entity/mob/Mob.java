package com.minecraft2.android.entity.mob;

import com.minecraft2.android.engine.GameEngine;
import com.minecraft2.android.entity.Entity;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.world.World;
import java.util.List;
import java.util.Random;

public abstract class Mob extends Entity {

    protected MobType type;
    protected float attackDamage;
    protected float attackRange;
    protected float attackCooldown;
    protected float attackTimer = 0;
    protected float moveSpeed;
    protected boolean hostile;
    protected boolean canFly;
    protected boolean aquatic;
    protected Player targetPlayer;
    protected float detectionRange;
    protected float wanderTimer = 0;
    protected float wanderX, wanderZ;
    protected boolean hasTarget = false;
    protected float stateTimer = 0;
    protected MobState state = MobState.IDLE;
    protected final Random random = new Random();

    public enum MobState {
        IDLE, WANDERING, CHASING, ATTACKING, FLEEING, DEAD
    }

    public Mob(float x, float y, float z, MobType type) {
        super(x, y, z, type.getBaseHealth(), type.getWidth(), type.getHeight());
        this.type = type;
        this.attackDamage = type.getAttackDamage();
        this.attackRange = type.getAttackRange();
        this.moveSpeed = type.getMoveSpeed();
        this.hostile = type.isHostile();
        this.canFly = type.canFly();
        this.aquatic = type.isAquatic();
        this.detectionRange = type.getDetectionRange();
        this.wanderX = x;
        this.wanderZ = z;
    }

    @Override
    public void update(float deltaTime, World world) {
        if (dead) {
            deathTimer += deltaTime;
            return;
        }
        if (attackTimer > 0) attackTimer -= deltaTime;

        List<Player> players = List.of(); // Retrieved from world
        Player nearest = findNearestPlayer(world);

        if (hostile && nearest != null) {
            float dist = distanceTo2D(this, nearest);
            if (dist < detectionRange) {
                targetPlayer = nearest;
                hasTarget = true;
                state = dist < attackRange ? MobState.ATTACKING : MobState.CHASING;
            } else {
                hasTarget = false;
                state = MobState.WANDERING;
            }
        }

        switch (state) {
            case IDLE -> updateIdle(deltaTime);
            case WANDERING -> updateWander(deltaTime, world);
            case CHASING -> updateChase(deltaTime, world);
            case ATTACKING -> updateAttack(deltaTime);
            case FLEEING -> updateFlee(deltaTime, world);
        }

        if (!canFly) {
            velY += GameEngine.GRAVITY * deltaTime;
            velY = Math.max(-30f, velY);
        }
        x += velX * deltaTime;
        y += velY * deltaTime;
        z += velZ * deltaTime;
        clampToGround(world);
    }

    protected Player findNearestPlayer(World world) {
        // Would normally query world for players; returning null for single player
        return world.getNearestPlayer();
    }

    protected void updateIdle(float deltaTime) {
        velX = velZ = 0;
        stateTimer += deltaTime;
        if (stateTimer > 2f + random.nextFloat() * 3f) {
            state = MobState.WANDERING;
            stateTimer = 0;
            wanderX = x + (random.nextFloat() - 0.5f) * 20;
            wanderZ = z + (random.nextFloat() - 0.5f) * 20;
        }
    }

    protected void updateWander(float deltaTime, World world) {
        float dx = wanderX - x, dz = wanderZ - z;
        float dist = (float) Math.sqrt(dx*dx + dz*dz);
        if (dist < 1f) {
            state = MobState.IDLE;
            velX = velZ = 0;
            return;
        }
        float speed = moveSpeed * 0.4f;
        velX = (dx / dist) * speed;
        velZ = (dz / dist) * speed;

        wanderTimer += deltaTime;
        if (wanderTimer > 8f) {
            state = MobState.IDLE;
            wanderTimer = 0;
        }
    }

    protected void updateChase(float deltaTime, World world) {
        if (targetPlayer == null) { state = MobState.IDLE; return; }
        float dx = targetPlayer.getX() - x, dz = targetPlayer.getZ() - z;
        float dist = (float) Math.sqrt(dx*dx + dz*dz);
        if (dist < attackRange) {
            state = MobState.ATTACKING;
            velX = velZ = 0;
            return;
        }
        velX = (dx / dist) * moveSpeed;
        velZ = (dz / dist) * moveSpeed;

        // Jump if blocked
        if (!onGround && velX == 0 && velZ == 0) {
            if (onGround) velY = 5f;
        }
    }

    protected void updateAttack(float deltaTime) {
        velX = velZ = 0;
        if (targetPlayer == null) { state = MobState.IDLE; return; }
        if (distanceTo2D(this, targetPlayer) > attackRange + 1f) {
            state = MobState.CHASING;
            return;
        }
        if (attackTimer <= 0) {
            performAttack();
            attackTimer = 1.5f;
        }
    }

    protected void performAttack() {
        if (targetPlayer != null) {
            targetPlayer.damage(attackDamage);
        }
    }

    protected void updateFlee(float deltaTime, World world) {
        if (targetPlayer == null) { state = MobState.IDLE; return; }
        float dx = x - targetPlayer.getX(), dz = z - targetPlayer.getZ();
        float dist = (float) Math.sqrt(dx*dx + dz*dz);
        if (dist > 30f) { state = MobState.IDLE; return; }
        velX = (dx / dist) * moveSpeed;
        velZ = (dz / dist) * moveSpeed;
    }

    protected void clampToGround(World world) {
        int bx = (int) Math.floor(x), by = (int) Math.floor(y), bz = (int) Math.floor(z);
        if (world.getBlock(bx, by - 1, bz) != null && world.getBlock(bx, by - 1, bz).isSolid()) {
            if (velY < 0) { velY = 0; onGround = true; }
        } else {
            onGround = false;
        }
        if (y < 0) { y = 0; velY = 0; onGround = true; }
    }

    public abstract List<ItemStack> getDrops();

    public MobType getType() { return type; }
    public boolean isHostile() { return hostile; }
    public float getAttackDamage() { return attackDamage; }
    public String getName() { return type.getDisplayName(); }
}
