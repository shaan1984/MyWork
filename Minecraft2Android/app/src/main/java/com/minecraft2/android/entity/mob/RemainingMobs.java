package com.minecraft2.android.entity.mob;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// ---- Passive Mobs ----

class Cow extends Mob {
    private static final Random R = new Random();
    public Cow(float x, float y, float z) { super(x, y, z, MobType.COW); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.BEEF, 1 + R.nextInt(3)));
        if (R.nextBoolean()) d.add(new ItemStack(ItemType.LEATHER, 1 + R.nextInt(2)));
        return d;
    }
}

class Sheep extends Mob {
    private static final Random R = new Random();
    public Sheep(float x, float y, float z) { super(x, y, z, MobType.SHEEP); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.MUTTON, 1 + R.nextInt(2)));
        d.add(new ItemStack(ItemType.WOOL, 1 + R.nextInt(3)));
        return d;
    }
}

class Pig extends Mob {
    private static final Random R = new Random();
    public Pig(float x, float y, float z) { super(x, y, z, MobType.PIG); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.PORK, 1 + R.nextInt(3)));
        return d;
    }
}

class Chicken extends Mob {
    private static final Random R = new Random();
    public Chicken(float x, float y, float z) { super(x, y, z, MobType.CHICKEN); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.CHICKEN_MEAT, 1));
        d.add(new ItemStack(ItemType.FEATHER, 1 + R.nextInt(2)));
        return d;
    }
}

class Horse extends Mob {
    public Horse(float x, float y, float z) { super(x, y, z, MobType.HORSE); }
    @Override public List<ItemStack> getDrops() { return new ArrayList<>(); }
}

class Rabbit extends Mob {
    private static final Random R = new Random();
    public Rabbit(float x, float y, float z) { super(x, y, z, MobType.RABBIT); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.RABBIT_MEAT, 1));
        if (R.nextFloat() < 0.1f) d.add(new ItemStack(ItemType.RABBIT_FOOT, 1));
        return d;
    }
}

class Squid extends Mob {
    public Squid(float x, float y, float z) { super(x, y, z, MobType.SQUID); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.INK_SAC, 1 + new Random().nextInt(3)));
        return d;
    }
}

class Parrot extends Mob {
    public Parrot(float x, float y, float z) { super(x, y, z, MobType.PARROT); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.FEATHER, 1 + new Random().nextInt(2)));
        return d;
    }
}

class PolarBear extends Mob {
    private static final Random R = new Random();
    public PolarBear(float x, float y, float z) { super(x, y, z, MobType.POLAR_BEAR); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.COD, 1 + R.nextInt(3)));
        return d;
    }
}

class Ocelot extends Mob {
    public Ocelot(float x, float y, float z) { super(x, y, z, MobType.OCELOT); }
    @Override public List<ItemStack> getDrops() { return new ArrayList<>(); }
}

class Wolf extends Mob {
    public Wolf(float x, float y, float z) { super(x, y, z, MobType.WOLF); }
    @Override public List<ItemStack> getDrops() { return new ArrayList<>(); }
}

class IronGolem extends Mob {
    private static final Random R = new Random();
    public IronGolem(float x, float y, float z) { super(x, y, z, MobType.IRON_GOLEM); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.IRON_INGOT, 3 + R.nextInt(5)));
        d.add(new ItemStack(ItemType.POPPY, 1 + R.nextInt(3)));
        return d;
    }
}

// ---- Hostile Mobs ----

class Spider extends Mob {
    private static final Random R = new Random();
    public Spider(float x, float y, float z) { super(x, y, z, MobType.SPIDER); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.STRING, 1 + R.nextInt(2)));
        if (R.nextFloat() < 0.33f) d.add(new ItemStack(ItemType.SPIDER_EYE, 1));
        return d;
    }
}

class Phantom extends Mob {
    private static final Random R = new Random();
    public Phantom(float x, float y, float z) { super(x, y, z, MobType.PHANTOM); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.PHANTOM_MEMBRANE, 1 + R.nextInt(2)));
        return d;
    }
}

class Blaze extends Mob {
    private static final Random R = new Random();
    public Blaze(float x, float y, float z) { super(x, y, z, MobType.BLAZE); }
    @Override protected void performAttack() {
        if (targetPlayer != null) targetPlayer.damage(attackDamage);
    }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.BLAZE_ROD, 1 + R.nextInt(2)));
        return d;
    }
}

class GhastMob extends Mob {
    private static final Random R = new Random();
    public GhastMob(float x, float y, float z) { super(x, y, z, MobType.GHAST); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.GHAST_TEAR, R.nextInt(2)));
        d.add(new ItemStack(ItemType.GUNPOWDER, 1 + R.nextInt(4)));
        return d;
    }
}

class HuskZombie extends Mob {
    private static final Random R = new Random();
    public HuskZombie(float x, float y, float z) { super(x, y, z, MobType.HUSK); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.ROTTEN_FLESH, 1 + R.nextInt(2)));
        return d;
    }
}

class Drowned extends Mob {
    private static final Random R = new Random();
    public Drowned(float x, float y, float z) { super(x, y, z, MobType.DROWNED); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.ROTTEN_FLESH, 1 + R.nextInt(2)));
        if (R.nextFloat() < 0.05f) d.add(new ItemStack(ItemType.TRIDENT, 1));
        return d;
    }
}

class GuardianMob extends Mob {
    private static final Random R = new Random();
    public GuardianMob(float x, float y, float z) { super(x, y, z, MobType.GUARDIAN); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.PRISMARINE_SHARD, 1 + R.nextInt(2)));
        d.add(new ItemStack(ItemType.COD, R.nextInt(2)));
        return d;
    }
}

class PiglinBrute extends Mob {
    private static final Random R = new Random();
    public PiglinBrute(float x, float y, float z) { super(x, y, z, MobType.PIGLIN_BRUTE); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.GOLD_INGOT, 1 + R.nextInt(3)));
        return d;
    }
}

// ---- New MC2 Mobs ----

class ShadowCreeper extends Mob {
    private static final Random R = new Random();
    public ShadowCreeper(float x, float y, float z) { super(x, y, z, MobType.SHADOW_CREEPER); }
    @Override protected void performAttack() {
        if (targetPlayer != null) {
            targetPlayer.damage(attackDamage * 2);
            // Teleport away after explosion
            x += R.nextFloat() * 10 - 5;
            z += R.nextFloat() * 10 - 5;
            dead = true;
        }
    }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.SHADOW_POWDER, 2 + R.nextInt(4)));
        d.add(new ItemStack(ItemType.GUNPOWDER, 1 + R.nextInt(2)));
        return d;
    }
}


class FireImp extends Mob {
    private static final Random R = new Random();
    public FireImp(float x, float y, float z) { super(x, y, z, MobType.FIRE_IMP); }
    @Override protected void performAttack() {
        if (targetPlayer != null) {
            targetPlayer.damage(attackDamage);
            // Fire effect
        }
    }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.BLAZE_POWDER, 1 + R.nextInt(3)));
        return d;
    }
}

class GiantMushroom extends Mob {
    private static final Random R = new Random();
    public GiantMushroom(float x, float y, float z) { super(x, y, z, MobType.GIANT_MUSHROOM); }
    @Override protected void performAttack() {
        if (targetPlayer != null) {
            targetPlayer.damage(attackDamage);
            // Spore cloud effect — poison
        }
    }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.MUSHROOM_STEW, 1 + R.nextInt(3)));
        d.add(new ItemStack(ItemType.GLOWSTONE_DUST, R.nextInt(4)));
        return d;
    }
}

class PoisonFrog extends Mob {
    private static final Random R = new Random();
    public PoisonFrog(float x, float y, float z) { super(x, y, z, MobType.POISON_FROG); }
    @Override public List<ItemStack> getDrops() {
        List<ItemStack> d = new ArrayList<>();
        d.add(new ItemStack(ItemType.SLIMEBALL, R.nextInt(2)));
        return d;
    }
}
