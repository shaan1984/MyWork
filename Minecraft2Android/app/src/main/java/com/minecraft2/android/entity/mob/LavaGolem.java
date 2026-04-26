package com.minecraft2.android.entity.mob;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LavaGolem extends Mob {
    private static final Random RAND = new Random();
    private float projectileTimer = 0;
    private static final float LAVA_BALL_COOLDOWN = 3.0f;

    public LavaGolem(float x, float y, float z) { super(x, y, z, MobType.LAVA_GOLEM); }

    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);
        if (projectileTimer > 0) projectileTimer -= deltaTime;
    }

    @Override
    protected void performAttack() {
        if (targetPlayer == null) return;
        float dist = distanceTo(this, targetPlayer);
        if (dist < 3.0f) {
            targetPlayer.damage(attackDamage);
        } else if (projectileTimer <= 0) {
            fireLavaBall();
            projectileTimer = LAVA_BALL_COOLDOWN;
        }
    }

    private void fireLavaBall() {
        if (targetPlayer != null) {
            targetPlayer.damage(attackDamage * 0.8f);
            // Apply burn effect — simplified as direct damage over time represented here
        }
    }

    @Override
    public void damage(float amount) {
        // Immune to fire damage
        if (amount > 0 && amount < 5) return;
        super.damage(amount * 0.7f);
    }

    @Override
    protected void onDeath() {
        if (targetPlayer != null) targetPlayer.addXp(40);
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.MAGMA_CREAM, 2 + RAND.nextInt(4)));
        drops.add(new ItemStack(ItemType.LAVA_CORE, 1));
        if (RAND.nextFloat() < 0.1f) drops.add(new ItemStack(ItemType.NETHERITE_INGOT, 1));
        return drops;
    }
}
