package com.minecraft2.android.entity.mob;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GunSlinger extends Mob {
    private static final Random RAND = new Random();
    private float shootCooldown = 0;
    private float reloadTimer = 0;
    private int magazine = 6;
    private static final int MAX_MAGAZINE = 6;
    private static final float BULLET_DAMAGE = 8.0f;
    private static final float SHOOT_COOLDOWN = 0.4f;
    private static final float RELOAD_TIME = 2.0f;

    public GunSlinger(float x, float y, float z) { super(x, y, z, MobType.GUN_SLINGER); }

    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);
        if (shootCooldown > 0) shootCooldown -= deltaTime;
        if (reloadTimer > 0) {
            reloadTimer -= deltaTime;
            if (reloadTimer <= 0) magazine = MAX_MAGAZINE;
        }
    }

    @Override
    protected void updateChase(float deltaTime, World world) {
        if (targetPlayer == null) return;
        float dist = distanceTo2D(this, targetPlayer);
        // Keep distance — circle strafe
        if (dist < 8f) {
            float dx = x - targetPlayer.getX(), dz = z - targetPlayer.getZ();
            float d = (float) Math.sqrt(dx*dx + dz*dz);
            velX = (dx / d) * moveSpeed;
            velZ = (dz / d) * moveSpeed;
        } else if (dist > 15f) {
            float dx = targetPlayer.getX() - x, dz = targetPlayer.getZ() - z;
            float d = (float) Math.sqrt(dx*dx + dz*dz);
            velX = (dx / d) * moveSpeed;
            velZ = (dz / d) * moveSpeed;
        } else {
            velX = (float) Math.sin(System.currentTimeMillis() * 0.002f) * moveSpeed;
            velZ = (float) Math.cos(System.currentTimeMillis() * 0.002f) * moveSpeed;
            state = MobState.ATTACKING;
        }
    }

    @Override
    protected void performAttack() {
        if (targetPlayer == null) return;
        if (magazine <= 0) {
            if (reloadTimer <= 0) reloadTimer = RELOAD_TIME;
            return;
        }
        if (shootCooldown <= 0) {
            shoot();
            shootCooldown = SHOOT_COOLDOWN;
            magazine--;
        }
    }

    private void shoot() {
        if (targetPlayer != null) {
            float dist = distanceTo(this, targetPlayer);
            float accuracy = Math.max(0.3f, 1.0f - dist / 30f);
            if (RAND.nextFloat() < accuracy) {
                targetPlayer.damage(BULLET_DAMAGE);
            }
        }
    }

    @Override
    protected void onDeath() {
        if (targetPlayer != null) targetPlayer.addXp(30);
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.PISTOL_AMMO, 5 + RAND.nextInt(10)));
        if (RAND.nextFloat() < 0.25f) drops.add(new ItemStack(ItemType.PISTOL, 1));
        if (RAND.nextFloat() < 0.4f) drops.add(new ItemStack(ItemType.LEATHER_ARMOR, 1));
        return drops;
    }
}
