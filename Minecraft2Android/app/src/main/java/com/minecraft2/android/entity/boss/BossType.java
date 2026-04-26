package com.minecraft2.android.entity.boss;

import android.graphics.Color;

public enum BossType {

    // === CLASSIC BOSSES (MC1) ===
    ENDER_DRAGON("Ender Dragon", 200f, 2.5f, 5.0f, 8.0f, 10.0f, 2.5f, 1.0f,
            3.0f, 15.0f, 500, Color.rgb(20, 20, 30), 5.0f, 4.0f),
    WITHER("Wither", 300f, 1.5f, 4.0f, 6.0f, 4.0f, 5.0f, 1.2f,
            2.0f, 8.0f, 400, Color.rgb(30, 20, 30), 4.0f, 3.0f),
    ELDER_GUARDIAN("Elder Guardian", 80f, 1.5f, 2.0f, 2.0f, 6.0f, 8.0f, 0.5f,
            3.0f, 10.0f, 200, Color.rgb(100, 150, 140), 3.0f, 2.0f),

    // === NEW MC2 BOSSES ===
    SKELETON_KING("Skeleton King", 400f, 1.0f, 4.0f, 10.0f, 6.0f, 3.0f, 1.5f,
            2.5f, 6.0f, 600, Color.rgb(200, 200, 180), 5.0f, 4.0f),
    DRAGON_LORD("Dragon Lord", 600f, 1.5f, 4.5f, 14.0f, 5.0f, 15.0f, 2.0f,
            3.0f, 12.0f, 1000, Color.rgb(160, 20, 20), 6.0f, 5.0f),
    CRYSTAL_GOLEM("Crystal Golem", 500f, 1.0f, 4.0f, 15.0f, 6.0f, 8.0f, 1.2f,
            2.5f, 10.0f, 800, Color.rgb(150, 220, 255), 5.0f, 4.5f),
    VOID_WALKER("Void Walker", 450f, 2.0f, 3.5f, 12.0f, 5.0f, 12.0f, 3.0f,
            2.0f, 8.0f, 900, Color.rgb(10, 0, 25), 4.5f, 4.0f),
    LAVA_TITAN("Lava Titan", 550f, 1.2f, 4.0f, 18.0f, 6.0f, 10.0f, 1.5f,
            2.5f, 10.0f, 850, Color.rgb(200, 50, 10), 5.5f, 4.5f),
    ICE_QUEEN("Ice Queen", 380f, 1.8f, 3.5f, 12.0f, 4.0f, 8.0f, 2.0f,
            2.0f, 8.0f, 700, Color.rgb(180, 230, 255), 4.0f, 3.5f),
    SHADOW_EMPEROR("Shadow Emperor", 700f, 2.5f, 4.0f, 14.0f, 5.0f, 10.0f, 2.5f,
            2.5f, 10.0f, 1200, Color.rgb(5, 0, 20), 6.0f, 5.0f);

    private final String displayName;
    private final float width, height;
    private final float maxHealth;
    private final float moveSpeed;
    private final float attackRange;
    private final float attackDamage;
    private final float attackCooldown;
    private final float specialDamage;
    private final float specialCooldown;
    private final int xpReward;
    private final int color;
    private final float knockback;
    private final float specialKnockback;

    BossType(String displayName, float width, float height, float maxHealth, float moveSpeed,
             float attackRange, float attackDamage, float attackCooldown,
             float specialDamage, float specialCooldown, int xpReward, int color,
             float knockback, float specialKnockback) {
        this.displayName = displayName;
        this.width = width;
        this.height = height;
        this.maxHealth = maxHealth;
        this.moveSpeed = moveSpeed;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        this.attackCooldown = attackCooldown;
        this.specialDamage = specialDamage;
        this.specialCooldown = specialCooldown;
        this.xpReward = xpReward;
        this.color = color;
        this.knockback = knockback;
        this.specialKnockback = specialKnockback;
    }

    public String getDisplayName() { return displayName; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getMaxHealth() { return maxHealth; }
    public float getMoveSpeed() { return moveSpeed; }
    public float getAttackRange() { return attackRange; }
    public float getAttackDamage() { return attackDamage; }
    public float getAttackCooldown() { return attackCooldown; }
    public float getSpecialDamage() { return specialDamage; }
    public float getSpecialCooldown() { return specialCooldown; }
    public int getXpReward() { return xpReward; }
    public int getColor() { return color; }
    public float getKnockback() { return knockback; }
}
