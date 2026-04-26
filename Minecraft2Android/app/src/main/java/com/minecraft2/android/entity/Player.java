package com.minecraft2.android.entity;

import com.minecraft2.android.block.BlockType;
import com.minecraft2.android.inventory.Inventory;
import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.save.SaveManager;
import com.minecraft2.android.weapon.Gun;
import com.minecraft2.android.weapon.GunType;
import com.minecraft2.android.world.World;

public class Player extends Entity {

    public static final float WALK_SPEED = 5.0f;
    public static final float SPRINT_SPEED = 8.0f;
    public static final float JUMP_VELOCITY = 8.0f;
    public static final float SWIM_SPEED = 3.0f;
    public static final float FLY_SPEED = 12.0f;
    public static final int INVENTORY_SIZE = 36;
    public static final int HOTBAR_SIZE = 9;

    private final Inventory inventory;
    private int selectedHotbarSlot = 0;
    private float hunger = 20f;
    private float maxHunger = 20f;
    private float saturation = 5f;
    private float xp = 0;
    private int xpLevel = 0;
    private float oxygen = 20f;
    private boolean sprinting = false;
    private boolean sneaking = false;
    private boolean flying = false;
    private boolean swimming = false;
    private boolean creative = false;
    private float breakProgress = 0f;
    private float damageCooldown = 0f;
    private float hungerTimer = 0f;
    private float reachDistance = 5.0f;
    private int armor = 0;
    private String equippedArmorSet = "none";
    private Gun equippedGun = null;

    // Movement input flags
    public boolean moveForward, moveBack, moveLeft, moveRight;
    public boolean jumpPressed, sneakPressed, sprintPressed;
    public float lookDX, lookDY;

    public Player(float x, float y, float z) {
        super(x, y, z, 20f, 0.6f, 1.8f);
        this.inventory = new Inventory(INVENTORY_SIZE);
        setupStartingInventory();
    }

    private void setupStartingInventory() {
        inventory.addItem(new ItemStack(ItemType.OAK_PLANKS, 20));
        inventory.addItem(new ItemStack(ItemType.CRAFTING_TABLE, 1));
        inventory.addItem(new ItemStack(ItemType.TORCH, 16));
        inventory.addItem(new ItemStack(ItemType.WOOD_SWORD, 1));
        inventory.addItem(new ItemStack(ItemType.WOOD_PICKAXE, 1));
        inventory.addItem(new ItemStack(ItemType.BREAD, 8));
    }

    @Override
    public void update(float deltaTime, World world) {
        if (dead) { deathTimer += deltaTime; return; }

        updateMovement(deltaTime, world);
        updateHunger(deltaTime);
        updateDamage(deltaTime, world);
        updateArmor();

        if (equippedGun != null) equippedGun.update(deltaTime);
        if (dead) return;
        if (hunger <= 0 && health > 1) damage(deltaTime * 0.5f);
        if (health < maxHealth && hunger > 18 && hungerTimer > 4f) {
            heal(0.5f * deltaTime);
        }
    }

    private void updateMovement(float deltaTime, World world) {
        float speed = sprinting ? SPRINT_SPEED : (sneaking ? WALK_SPEED * 0.3f : WALK_SPEED);
        if (swimming) speed = SWIM_SPEED;

        float sinYaw = (float) Math.sin(Math.toRadians(yaw));
        float cosYaw = (float) Math.cos(Math.toRadians(yaw));

        float targetVX = 0, targetVZ = 0;
        if (moveForward) { targetVX += sinYaw * speed; targetVZ += cosYaw * speed; }
        if (moveBack) { targetVX -= sinYaw * speed; targetVZ -= cosYaw * speed; }
        if (moveLeft) { targetVX -= cosYaw * speed; targetVZ += sinYaw * speed; }
        if (moveRight) { targetVX += cosYaw * speed; targetVZ -= sinYaw * speed; }

        velX = targetVX;
        velZ = targetVZ;

        if (jumpPressed && onGround && !flying) {
            velY = JUMP_VELOCITY;
            onGround = false;
            hunger = Math.max(0, hunger - 0.05f);
        }

        if (flying) {
            velY = jumpPressed ? FLY_SPEED : (sneakPressed ? -FLY_SPEED : 0);
        }

        yaw += lookDX * 0.15f;
        pitch = Math.max(-89, Math.min(89, pitch - lookDY * 0.15f));
        lookDX = 0;
        lookDY = 0;

        if (sprinting && (moveForward || moveBack || moveLeft || moveRight)) {
            hunger = Math.max(0, hunger - 0.03f * deltaTime);
        }
    }

    private void updateHunger(float deltaTime) {
        hungerTimer += deltaTime;
        if (hungerTimer >= 80f) {
            hungerTimer = 0;
            hunger = Math.max(0, hunger - 0.5f);
        }
    }

    private void updateDamage(float deltaTime, World world) {
        if (damageCooldown > 0) damageCooldown -= deltaTime;

        BlockType blockAtFeet = world.getBlock((int)x, (int)y, (int)z);
        if (blockAtFeet == BlockType.LAVA && damageCooldown <= 0) {
            damage(4f);
            damageCooldown = 0.5f;
        }
        if (blockAtFeet == BlockType.CACTUS && damageCooldown <= 0) {
            damage(1f);
            damageCooldown = 0.5f;
        }
    }

    private void updateArmor() {
        armor = 0;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack is = inventory.getSlot(i);
            if (is != null && is.getType().isArmor()) {
                armor += is.getType().getArmorValue();
            }
        }
    }

    @Override
    public void damage(float amount) {
        if (damageCooldown > 0) return;
        float reduction = armor * 0.04f;
        float actual = amount * (1f - reduction);
        super.damage(actual);
        damageCooldown = 0.5f;
    }

    public void eat(ItemType food) {
        hunger = Math.min(maxHunger, hunger + food.getFoodValue());
        saturation = Math.min(20f, saturation + food.getSaturationValue());
    }

    public void pickupItem(ItemStack stack) {
        inventory.addItem(stack);
    }

    public ItemStack getSelectedItem() {
        return inventory.getHotbarSlot(selectedHotbarSlot);
    }

    public void equipGun(Gun gun) { this.equippedGun = gun; }
    public Gun getEquippedGun() { return equippedGun; }

    public void loadFromData(SaveManager.WorldData data) {
        health = data.playerHealth;
        hunger = data.playerHunger;
        xp = data.playerXP;
        xpLevel = data.playerXPLevel;
    }

    public Inventory getInventory() { return inventory; }
    public int getSelectedHotbarSlot() { return selectedHotbarSlot; }
    public void setSelectedHotbarSlot(int slot) { this.selectedHotbarSlot = Math.max(0, Math.min(8, slot)); }
    public float getHunger() { return hunger; }
    public float getMaxHunger() { return maxHunger; }
    public float getXp() { return xp; }
    public int getXpLevel() { return xpLevel; }
    public void addXp(float amount) { xp += amount; if (xp >= xpLevel * 100 + 100) { xpLevel++; xp = 0; } }
    public float getOxygen() { return oxygen; }
    public boolean isSprinting() { return sprinting; }
    public void setSprinting(boolean s) { this.sprinting = s; }
    public boolean isSneaking() { return sneaking; }
    public void setSneaking(boolean s) { this.sneaking = s; }
    public boolean isFlying() { return flying; }
    public void setFlying(boolean f) { this.flying = f; }
    public boolean isCreative() { return creative; }
    public void setCreative(boolean c) { this.creative = c; }
    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    public int getArmor() { return armor; }
    public String getEquippedArmorSet() { return equippedArmorSet; }
    public float getReachDistance() { return reachDistance; }

    // Called by IceDragon — returns null (single player session, world tracked in engine)
    public com.minecraft2.android.world.World getWorld() { return null; }
}
