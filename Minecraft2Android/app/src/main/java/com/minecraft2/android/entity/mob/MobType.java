package com.minecraft2.android.entity.mob;

import android.graphics.Color;

public enum MobType {

    // === PASSIVE MOBS (MC1) ===
    COW("Cow", 10, 0, 0, 2.5f, 0.6f, 1.4f, 20, false, false, false, Color.rgb(80, 60, 40)),
    SHEEP("Sheep", 8, 0, 0, 2.3f, 0.6f, 1.3f, 20, false, false, false, Color.rgb(200, 200, 200)),
    PIG("Pig", 10, 0, 0, 2.5f, 0.9f, 0.9f, 20, false, false, false, Color.rgb(220, 170, 170)),
    CHICKEN("Chicken", 4, 0, 0, 2.0f, 0.4f, 0.7f, 20, false, false, false, Color.rgb(240, 240, 230)),
    COD("Cod", 3, 0, 0, 3.0f, 0.5f, 0.3f, 10, false, false, true, Color.rgb(200, 160, 100)),
    SALMON("Salmon", 3, 0, 0, 3.0f, 0.7f, 0.4f, 10, false, false, true, Color.rgb(220, 100, 80)),
    SQUID("Squid", 10, 0, 0, 2.5f, 0.8f, 0.8f, 10, false, false, true, Color.rgb(50, 50, 80)),
    RABBIT("Rabbit", 3, 0, 0, 4.0f, 0.4f, 0.5f, 16, false, false, false, Color.rgb(200, 170, 130)),
    HORSE("Horse", 30, 0, 0, 5.0f, 1.4f, 1.6f, 20, false, false, false, Color.rgb(150, 120, 80)),
    WOLF("Wolf", 20, 4, 1.5f, 4.0f, 0.6f, 0.85f, 16, true, false, false, Color.rgb(150, 150, 140)),
    OCELOT("Ocelot", 10, 0, 0, 3.5f, 0.6f, 0.7f, 16, false, false, false, Color.rgb(200, 170, 80)),
    PARROT("Parrot", 6, 0, 0, 3.0f, 0.5f, 0.9f, 10, false, true, false, Color.rgb(255, 50, 50)),
    POLAR_BEAR("Polar Bear", 30, 6, 2.0f, 2.5f, 1.3f, 1.4f, 16, true, false, false, Color.WHITE),
    PANDA("Panda", 20, 6, 2.0f, 2.5f, 1.3f, 1.25f, 16, false, false, false, Color.rgb(220, 220, 200)),
    LLAMA("Llama", 22, 0, 0, 2.5f, 0.9f, 1.87f, 20, false, false, false, Color.rgb(210, 185, 155)),
    IRON_GOLEM("Iron Golem", 100, 15, 3.0f, 2.25f, 1.4f, 2.7f, 30, false, false, false, Color.rgb(200, 200, 200)),

    // === HOSTILE MOBS (MC1) ===
    ZOMBIE("Zombie", 20, 3, 1.5f, 2.5f, 0.6f, 1.95f, 35, true, false, false, Color.rgb(80, 140, 80)),
    SKELETON("Skeleton", 20, 0, 0, 3.0f, 0.6f, 1.99f, 40, true, false, false, Color.rgb(190, 190, 180)),
    CREEPER("Creeper", 20, 0, 0, 3.0f, 0.6f, 1.7f, 32, true, false, false, Color.rgb(80, 130, 80)),
    SPIDER("Spider", 16, 2, 1.0f, 3.8f, 1.4f, 0.9f, 40, true, false, false, Color.rgb(40, 40, 40)),
    ENDERMAN("Enderman", 40, 7, 2.0f, 2.5f, 0.6f, 2.9f, 64, true, false, false, Color.rgb(20, 20, 20)),
    WITCH("Witch", 26, 0, 0, 3.0f, 0.6f, 1.95f, 16, true, false, false, Color.rgb(80, 40, 80)),
    PHANTOM("Phantom", 20, 6, 2.0f, 4.5f, 0.9f, 0.5f, 64, true, true, false, Color.rgb(60, 80, 100)),
    BLAZE("Blaze", 20, 6, 2.0f, 3.5f, 0.6f, 1.8f, 48, true, true, false, Color.rgb(200, 140, 30)),
    GHAST("Ghast", 10, 0, 0, 2.0f, 4.0f, 4.0f, 100, true, true, false, Color.rgb(240, 230, 230)),
    SLIME("Slime", 16, 3, 1.5f, 1.5f, 2.04f, 2.04f, 24, true, false, false, Color.rgb(100, 200, 100)),
    MAGMA_CUBE("Magma Cube", 16, 6, 2.0f, 2.5f, 2.04f, 2.04f, 24, true, false, false, Color.rgb(200, 80, 20)),
    PIGLIN("Piglin", 16, 5, 2.0f, 3.0f, 0.6f, 1.95f, 32, true, false, false, Color.rgb(200, 150, 130)),
    PIGLIN_BRUTE("Piglin Brute", 50, 7, 2.0f, 3.0f, 0.6f, 1.95f, 32, true, false, false, Color.rgb(170, 100, 80)),
    HUSK("Husk", 20, 3, 1.5f, 2.5f, 0.6f, 1.95f, 35, true, false, false, Color.rgb(160, 130, 80)),
    DROWNED("Drowned", 20, 3, 1.5f, 2.5f, 0.6f, 1.95f, 35, true, false, true, Color.rgb(50, 100, 120)),
    GUARDIAN("Guardian", 30, 6, 2.0f, 3.0f, 0.85f, 0.85f, 32, true, false, true, Color.rgb(80, 150, 140)),
    ELDER_GUARDIAN("Elder Guardian", 80, 8, 2.0f, 1.5f, 1.9975f, 1.9975f, 50, true, false, true, Color.rgb(120, 160, 150)),
    SHULKER("Shulker", 30, 4, 1.5f, 0.5f, 1.0f, 1.0f, 16, true, false, false, Color.rgb(150, 100, 180)),
    SILVERFISH("Silverfish", 8, 1, 1.0f, 5.0f, 0.4f, 0.3f, 20, true, false, false, Color.rgb(100, 100, 110)),

    // === NEW MC2 MOBS ===
    ZOMBIE_KNIGHT("Zombie Knight", 50, 8, 2.5f, 3.5f, 0.6f, 2.0f, 40, true, false, false, Color.rgb(60, 100, 60)),
    ICE_DRAGON("Ice Dragon", 80, 12, 3.0f, 4.0f, 2.0f, 1.5f, 60, true, true, false, Color.rgb(140, 200, 255)),
    LAVA_GOLEM("Lava Golem", 70, 10, 3.0f, 2.0f, 1.2f, 2.2f, 32, true, false, false, Color.rgb(200, 60, 20)),
    CRYSTAL_SPIDER("Crystal Spider", 30, 6, 2.0f, 4.5f, 1.4f, 1.0f, 48, true, false, false, Color.rgb(160, 220, 255)),
    GUN_SLINGER("Gun Slinger", 35, 0, 0, 3.5f, 0.6f, 1.95f, 64, true, false, false, Color.rgb(100, 80, 60)),
    SHADOW_CREEPER("Shadow Creeper", 30, 0, 0, 4.0f, 0.6f, 1.7f, 48, true, false, false, Color.rgb(20, 10, 40)),
    VOID_STALKER("Void Stalker", 45, 9, 2.5f, 5.0f, 0.6f, 2.5f, 64, true, true, false, Color.rgb(10, 0, 30)),
    CRYSTAL_GOLEM_MINION("Crystal Golem Minion", 40, 6, 2.0f, 2.5f, 1.2f, 2.0f, 30, true, false, false, Color.rgb(180, 230, 255)),
    FIRE_IMP("Fire Imp", 18, 5, 2.0f, 5.0f, 0.5f, 1.2f, 40, true, true, false, Color.rgb(255, 120, 30)),
    GIANT_MUSHROOM("Giant Mushroom", 60, 12, 3.0f, 1.5f, 1.8f, 2.5f, 20, true, false, false, Color.rgb(180, 80, 180)),
    POISON_FROG("Poison Frog", 8, 3, 1.5f, 4.0f, 0.5f, 0.5f, 24, true, false, false, Color.rgb(80, 200, 50)),
    TITANIUM_GOLEM("Titanium Golem", 200, 20, 3.5f, 2.0f, 1.6f, 3.0f, 32, true, false, false, Color.rgb(150, 180, 220)),
    SHADOW_WOLF("Shadow Wolf", 25, 6, 2.0f, 6.0f, 0.8f, 1.0f, 48, true, false, false, Color.rgb(30, 15, 50));

    private final String displayName;
    private final float baseHealth;
    private final float attackDamage;
    private final float attackRange;
    private final float moveSpeed;
    private final float width, height;
    private final float detectionRange;
    private final boolean hostile, canFly, aquatic;
    private final int color;

    MobType(String displayName, float baseHealth, float attackDamage, float attackRange,
             float moveSpeed, float width, float height, float detectionRange,
             boolean hostile, boolean canFly, boolean aquatic, int color) {
        this.displayName = displayName;
        this.baseHealth = baseHealth;
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.moveSpeed = moveSpeed;
        this.width = width;
        this.height = height;
        this.detectionRange = detectionRange;
        this.hostile = hostile;
        this.canFly = canFly;
        this.aquatic = aquatic;
        this.color = color;
    }

    public String getDisplayName() { return displayName; }
    public float getBaseHealth() { return baseHealth; }
    public float getAttackDamage() { return attackDamage; }
    public float getAttackRange() { return attackRange; }
    public float getMoveSpeed() { return moveSpeed; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getDetectionRange() { return detectionRange; }
    public boolean isHostile() { return hostile; }
    public boolean canFly() { return canFly; }
    public boolean isAquatic() { return aquatic; }
    public int getColor() { return color; }
}
