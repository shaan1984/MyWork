package com.minecraft2.android.entity.mob;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Creeper extends Mob {
    private static final Random RAND = new Random();
    private float fuseTimer = 0;
    private boolean fusing = false;
    private static final float FUSE_TIME = 1.5f;
    private static final float EXPLOSION_RANGE = 4.0f;

    public Creeper(float x, float y, float z) { super(x, y, z, MobType.CREEPER); }

    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);
        if (fusing) {
            fuseTimer += deltaTime;
            if (fuseTimer >= FUSE_TIME) {
                explode(world);
            }
        }
    }

    @Override
    protected void performAttack() {
        fusing = true;
    }

    private void explode(World world) {
        world.getParticleSystem().spawnExplosionParticles(x, y, z);
        if (targetPlayer != null) {
            float dist = distanceTo(this, targetPlayer);
            if (dist < EXPLOSION_RANGE) {
                float damage = 30f * (1f - dist / EXPLOSION_RANGE);
                targetPlayer.damage(damage);
            }
        }
        // Destroy blocks in radius
        for (int bx = (int)x - 3; bx <= (int)x + 3; bx++) {
            for (int by = (int)y - 3; by <= (int)y + 3; by++) {
                for (int bz = (int)z - 3; bz <= (int)z + 3; bz++) {
                    float d = (float)Math.sqrt((bx-x)*(bx-x)+(by-y)*(by-y)+(bz-z)*(bz-z));
                    if (d < 3.5f && RAND.nextFloat() < 0.7f) {
                        world.setBlock(bx, by, bz, com.minecraft2.android.block.BlockType.AIR);
                    }
                }
            }
        }
        dead = true;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.GUNPOWDER, 1 + RAND.nextInt(2)));
        if (RAND.nextFloat() < 0.01f) drops.add(new ItemStack(ItemType.MUSIC_DISC, 1));
        return drops;
    }
}
