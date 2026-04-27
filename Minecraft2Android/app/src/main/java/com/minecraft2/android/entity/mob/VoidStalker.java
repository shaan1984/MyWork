package com.minecraft2.android.entity.mob;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VoidStalker extends Mob {
    private static final Random R = new Random();
    private float teleportTimer = 0;
    public VoidStalker(float x, float y, float z) { super(x, y, z, MobType.VOID_STALKER); }
    @Override public void update(float dt, World world) {
        super.update(dt, world);
        teleportTimer -= dt;
        if (teleportTimer <= 0 && targetPlayer != null && distanceTo(this, targetPlayer) < 5f) {
            x = targetPlayer.getX() + R.nextFloat() * 4 - 2;
            z = targetPlayer.getZ() + R.nextFloat() * 4 - 2;
            teleportTimer = 3f;
        }
    }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.VOID_ESSENCE, 1 + R.nextInt(3)));
        return d;
    }
}
