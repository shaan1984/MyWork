package com.minecraft2.android.entity.mob;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrystalGolemMinion extends Mob {
    private static final Random R = new Random();
    public CrystalGolemMinion(float x, float y, float z) { super(x, y, z, MobType.CRYSTAL_GOLEM_MINION); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.CRYSTAL_SHARD, 2 + R.nextInt(5)));
        return d;
    }
}
