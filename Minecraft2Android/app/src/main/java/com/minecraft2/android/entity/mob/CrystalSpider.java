package com.minecraft2.android.entity.mob;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrystalSpider extends Mob {
    private static final Random RAND = new Random();
    private float webTimer = 0;

    public CrystalSpider(float x, float y, float z) { super(x, y, z, MobType.CRYSTAL_SPIDER); }

    @Override
    protected void performAttack() {
        if (targetPlayer == null) return;
        targetPlayer.damage(attackDamage);
        if (webTimer <= 0) {
            // Slow player in web
            targetPlayer.setVelX(targetPlayer.getVelX() * 0.2f);
            targetPlayer.setVelZ(targetPlayer.getVelZ() * 0.2f);
            webTimer = 5.0f;
        }
    }

    @Override
    protected void onDeath() {
        if (targetPlayer != null) targetPlayer.addXp(20);
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.CRYSTAL_SHARD, 2 + RAND.nextInt(4)));
        if (RAND.nextFloat() < 0.3f) drops.add(new ItemStack(ItemType.SPIDER_EYE, 1));
        if (RAND.nextFloat() < 0.15f) drops.add(new ItemStack(ItemType.CRYSTAL_WEB, 1));
        return drops;
    }
}
