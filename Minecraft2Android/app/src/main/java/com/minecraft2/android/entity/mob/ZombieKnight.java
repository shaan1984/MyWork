package com.minecraft2.android.entity.mob;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZombieKnight extends Mob {
    private static final Random RAND = new Random();
    private float shieldCooldown = 0;
    private boolean shielding = false;
    private float chargeTimer = 0;
    private boolean charging = false;

    public ZombieKnight(float x, float y, float z) { super(x, y, z, MobType.ZOMBIE_KNIGHT); }

    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);
        if (shieldCooldown > 0) shieldCooldown -= deltaTime;
        if (chargeTimer > 0) {
            chargeTimer -= deltaTime;
            if (charging) {
                velX *= 1.5f;
                velZ *= 1.5f;
            }
        } else {
            charging = false;
        }
    }

    @Override
    protected void performAttack() {
        if (targetPlayer == null) return;
        // Charge attack every 5 seconds
        if (!charging && RAND.nextFloat() < 0.3f) {
            initiateCharge();
        } else {
            targetPlayer.damage(attackDamage);
        }
    }

    private void initiateCharge() {
        charging = true;
        chargeTimer = 0.8f;
        if (targetPlayer != null) {
            float dx = targetPlayer.getX() - x;
            float dz = targetPlayer.getZ() - z;
            float dist = (float) Math.sqrt(dx*dx + dz*dz);
            velX = (dx / dist) * moveSpeed * 3;
            velZ = (dz / dist) * moveSpeed * 3;
        }
    }

    @Override
    public void damage(float amount) {
        if (shielding && shieldCooldown <= 0) {
            amount *= 0.3f;
            shieldCooldown = 3.0f;
        }
        super.damage(amount);
    }

    @Override
    protected void onDeath() {
        if (targetPlayer != null) targetPlayer.addXp(25);
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.IRON_INGOT, 2 + RAND.nextInt(4)));
        drops.add(new ItemStack(ItemType.ROTTEN_FLESH, 1 + RAND.nextInt(3)));
        if (RAND.nextFloat() < 0.15f) drops.add(new ItemStack(ItemType.IRON_SWORD, 1));
        if (RAND.nextFloat() < 0.1f) drops.add(new ItemStack(ItemType.IRON_HELMET, 1));
        return drops;
    }
}
