package com.minecraft2.android.entity.mob;

import com.minecraft2.android.block.BlockType;
import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IceDragon extends Mob {
    private static final Random RAND = new Random();
    private float breathTimer = 0;
    private float swoopTimer = 0;
    private boolean swooping = false;

    public IceDragon(float x, float y, float z) {
        super(x, y, z, MobType.ICE_DRAGON);
        y += 5;
    }

    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);
        breathTimer -= deltaTime;
        swoopTimer -= deltaTime;

        if (swooping) {
            y -= 8f * deltaTime;
            if (y <= getGroundY(world)) swooping = false;
        } else {
            float targetY = getGroundY(world) + 8f;
            y += (targetY - y) * 0.02f;
        }
    }

    private float getGroundY(World world) {
        return world.getSurfaceHeight((int) x, (int) z);
    }

    @Override
    protected void performAttack() {
        if (targetPlayer == null) return;
        if (breathTimer <= 0 && distanceTo2D(this, targetPlayer) < 15f) {
            breathIce(targetPlayer.getWorld());
            breathTimer = 4.0f;
        } else if (swoopTimer <= 0) {
            initiateSwoop();
            swoopTimer = 6.0f;
        }
    }

    private void breathIce(World world) {
        if (targetPlayer != null) {
            targetPlayer.damage(attackDamage * 0.7f);
            // Slow the player (apply ice effect)
            targetPlayer.setVelX(targetPlayer.getVelX() * 0.3f);
            targetPlayer.setVelZ(targetPlayer.getVelZ() * 0.3f);
            world.getParticleSystem().spawnCrystalParticles(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ());
        }
    }

    private void initiateSwoop() {
        swooping = true;
        if (targetPlayer != null) {
            float dx = targetPlayer.getX() - x;
            float dz = targetPlayer.getZ() - z;
            float dist = (float) Math.sqrt(dx*dx + dz*dz);
            velX = (dx / dist) * moveSpeed * 2;
            velZ = (dz / dist) * moveSpeed * 2;
        }
    }

    @Override
    protected void onDeath() {
        if (targetPlayer != null) targetPlayer.addXp(80);
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.ICE_SHARD, 5 + RAND.nextInt(10)));
        drops.add(new ItemStack(ItemType.CRYSTAL_SCALE, 2 + RAND.nextInt(4)));
        if (RAND.nextFloat() < 0.2f) drops.add(new ItemStack(ItemType.ICE_DRAGON_WING, 1));
        return drops;
    }
}
