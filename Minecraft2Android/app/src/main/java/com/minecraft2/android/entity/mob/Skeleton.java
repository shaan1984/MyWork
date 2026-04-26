package com.minecraft2.android.entity.mob;

import com.minecraft2.android.entity.Player;
import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Skeleton extends Mob {
    private static final Random RAND = new Random();
    private float shootTimer = 0;

    public Skeleton(float x, float y, float z) { super(x, y, z, MobType.SKELETON); }

    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);
        shootTimer -= deltaTime;
    }

    @Override
    protected void performAttack() {
        if (targetPlayer != null && shootTimer <= 0) {
            shootArrow();
            shootTimer = 2.0f;
        }
    }

    private void shootArrow() {
        if (targetPlayer != null) {
            float damage = attackDamage;
            targetPlayer.damage(damage);
        }
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        if (RAND.nextFloat() < 0.5f) drops.add(new ItemStack(ItemType.ARROW, 1 + RAND.nextInt(3)));
        if (RAND.nextFloat() < 0.5f) drops.add(new ItemStack(ItemType.BONE, 1 + RAND.nextInt(2)));
        return drops;
    }
}
