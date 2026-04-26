package com.minecraft2.android.entity.boss;

import com.minecraft2.android.engine.GameEngine;
import com.minecraft2.android.entity.Entity;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.world.World;
import java.util.List;
import java.util.Random;

public abstract class Boss extends Entity {

    protected BossType type;
    protected int phase = 1;
    protected float phaseTimer = 0;
    protected float attackTimer = 0;
    protected float specialAttackTimer = 0;
    protected Player targetPlayer;
    protected final Random random = new Random();
    protected boolean enraged = false;
    protected List<String> announcements;

    public enum BossPhase {
        PHASE_1, PHASE_2, PHASE_3, ENRAGE
    }

    public Boss(float x, float y, float z, BossType type) {
        super(x, y, z, type.getMaxHealth(), type.getWidth(), type.getHeight());
        this.type = type;
        this.maxHealth = type.getMaxHealth();
        this.health = maxHealth;
    }

    @Override
    public void update(float deltaTime, World world) {
        if (dead) { deathTimer += deltaTime; return; }

        targetPlayer = world.getNearestPlayer();
        if (targetPlayer == null) return;

        attackTimer -= deltaTime;
        specialAttackTimer -= deltaTime;
        phaseTimer += deltaTime;

        checkPhaseTransition();
        updateBossAI(deltaTime, world);

        // Move toward player
        float dx = targetPlayer.getX() - x;
        float dz = targetPlayer.getZ() - z;
        float dist = (float) Math.sqrt(dx*dx + dz*dz);
        if (dist > type.getAttackRange()) {
            float speed = type.getMoveSpeed() * (enraged ? 1.5f : 1.0f);
            velX = (dx / dist) * speed;
            velZ = (dz / dist) * speed;
        } else {
            velX = 0; velZ = 0;
            if (attackTimer <= 0) {
                performMainAttack();
                attackTimer = type.getAttackCooldown();
            }
        }
        if (specialAttackTimer <= 0) {
            performSpecialAttack(world);
            specialAttackTimer = type.getSpecialCooldown();
        }

        velY += GameEngine.GRAVITY * deltaTime;
        x += velX * deltaTime;
        y += velY * deltaTime;
        z += velZ * deltaTime;
        clampToGround(world);
    }

    protected void checkPhaseTransition() {
        float ratio = health / maxHealth;
        int newPhase = ratio > 0.66f ? 1 : (ratio > 0.33f ? 2 : 3);
        if (newPhase != phase) {
            phase = newPhase;
            onPhaseTransition(phase);
        }
        if (!enraged && ratio < 0.15f) {
            enraged = true;
            onEnrage();
        }
    }

    protected void onPhaseTransition(int newPhase) {
        phaseTimer = 0;
        if (newPhase == 2) maxHealth = maxHealth * 0.85f;
    }

    protected void onEnrage() {
        attackTimer = 0;
        specialAttackTimer = 0;
    }

    protected abstract void updateBossAI(float deltaTime, World world);
    protected abstract void performMainAttack();
    protected abstract void performSpecialAttack(World world);
    public abstract List<ItemStack> getDrops();

    protected void clampToGround(World world) {
        int bx = (int) Math.floor(x), by = (int) Math.floor(y), bz = (int) Math.floor(z);
        if (world.getBlock(bx, by - 1, bz) != null && world.getBlock(bx, by - 1, bz).isSolid()) {
            if (velY < 0) { velY = 0; onGround = true; }
        } else onGround = false;
        if (y < 0) { y = 0; velY = 0; onGround = true; }
    }

    @Override
    protected void onDeath() {
        if (targetPlayer != null) targetPlayer.addXp(type.getXpReward());
    }

    public BossType getType() { return type; }
    public String getName() { return type.getDisplayName(); }
    public int getPhase() { return phase; }
    public boolean isEnraged() { return enraged; }
}
