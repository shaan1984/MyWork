package com.minecraft2.android.entity.mob;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Zombie extends Mob {
    private static final Random RAND = new Random();

    public Zombie(float x, float y, float z) { super(x, y, z, MobType.ZOMBIE); }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        if (RAND.nextFloat() < 0.4f) drops.add(new ItemStack(ItemType.ROTTEN_FLESH, 1 + RAND.nextInt(2)));
        if (RAND.nextFloat() < 0.025f) drops.add(new ItemStack(ItemType.IRON_INGOT, 1));
        if (RAND.nextFloat() < 0.025f) drops.add(new ItemStack(ItemType.CARROT, 1));
        return drops;
    }
}
