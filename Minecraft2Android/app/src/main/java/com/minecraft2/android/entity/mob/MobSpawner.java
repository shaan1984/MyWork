package com.minecraft2.android.entity.mob;

import com.minecraft2.android.world.BiomeType;
import java.util.Random;

public class MobSpawner {

    public static Mob spawnForBiome(BiomeType biome, int x, int y, int z, float timeOfDay, Random random) {
        boolean isNight = timeOfDay > 0.75f || timeOfDay < 0.25f;

        if (biome == BiomeType.PLAINS || biome == BiomeType.FOREST || biome == BiomeType.BIRCH_FOREST) {
            return isNight ? spawnNightMob(x, y, z, random) : spawnPassiveMob(x, y, z, random);
        } else if (biome == BiomeType.DESERT || biome == BiomeType.BADLANDS) {
            return spawnDesertMob(x, y, z, random, isNight);
        } else if (biome == BiomeType.JUNGLE) {
            return spawnJungleMob(x, y, z, random, isNight);
        } else if (biome == BiomeType.SNOWY_TUNDRA || biome == BiomeType.SNOWY_TAIGA || biome == BiomeType.ICE_SPIRES) {
            return spawnIceMob(x, y, z, random, isNight);
        } else if (biome == BiomeType.OCEAN || biome == BiomeType.DEEP_OCEAN) {
            return spawnOceanMob(x, y, z, random);
        } else if (biome == BiomeType.NETHER_WASTES || biome == BiomeType.BASALT_DELTAS) {
            return spawnNetherMob(x, y, z, random);
        } else if (biome == BiomeType.CRYSTAL_CAVES) {
            return spawnCrystalMob(x, y, z, random);
        } else if (biome == BiomeType.VOID_WASTES) {
            return spawnVoidMob(x, y, z, random);
        } else if (biome == BiomeType.LAVA_FIELDS) {
            return spawnLavaMob(x, y, z, random);
        } else if (biome == BiomeType.SHADOW_FOREST) {
            return spawnShadowMob(x, y, z, random);
        } else if (biome == BiomeType.TITANIUM_PEAKS) {
            return spawnMountainMob(x, y, z, random, isNight);
        } else if (biome == BiomeType.FUNGAL_DEPTHS) {
            return new GiantMushroom(x, y, z);
        } else {
            return isNight ? spawnNightMob(x, y, z, random) : null;
        }
    }

    private static Mob spawnNightMob(int x, int y, int z, Random r) {
        int n = r.nextInt(6);
        if (n == 0) return new Zombie(x, y, z);
        if (n == 1) return new Skeleton(x, y, z);
        if (n == 2) return new Creeper(x, y, z);
        if (n == 3) return new Spider(x, y, z);
        if (n == 4) return new ZombieKnight(x, y, z);
        return new Phantom(x, y, z);
    }

    private static Mob spawnPassiveMob(int x, int y, int z, Random r) {
        int n = r.nextInt(5);
        if (n == 0) return new Cow(x, y, z);
        if (n == 1) return new Sheep(x, y, z);
        if (n == 2) return new Pig(x, y, z);
        if (n == 3) return new Chicken(x, y, z);
        return new Horse(x, y, z);
    }

    private static Mob spawnDesertMob(int x, int y, int z, Random r, boolean night) {
        if (night) return r.nextBoolean() ? new HuskZombie(x, y, z) : new Skeleton(x, y, z);
        return r.nextBoolean() ? new Rabbit(x, y, z) : null;
    }

    private static Mob spawnJungleMob(int x, int y, int z, Random r, boolean night) {
        if (night) return new CrystalSpider(x, y, z);
        int n = r.nextInt(3);
        if (n == 0) return new Parrot(x, y, z);
        if (n == 1) return new Ocelot(x, y, z);
        return new PoisonFrog(x, y, z);
    }

    private static Mob spawnIceMob(int x, int y, int z, Random r, boolean night) {
        if (night) return r.nextBoolean() ? new IceDragon(x, y, z) : new Skeleton(x, y, z);
        return r.nextBoolean() ? new PolarBear(x, y, z) : new IceDragon(x, y, z);
    }

    private static Mob spawnOceanMob(int x, int y, int z, Random r) {
        int n = r.nextInt(3);
        if (n == 0) return new Squid(x, y, z);
        if (n == 1) return new GuardianMob(x, y, z);
        return new Drowned(x, y, z);
    }

    private static Mob spawnNetherMob(int x, int y, int z, Random r) {
        int n = r.nextInt(4);
        if (n == 0) return new Blaze(x, y, z);
        if (n == 1) return new PiglinBrute(x, y, z);
        if (n == 2) return new GhastMob(x, y, z);
        return new LavaGolem(x, y, z);
    }

    private static Mob spawnCrystalMob(int x, int y, int z, Random r) {
        return r.nextBoolean() ? new CrystalSpider(x, y, z) : new CrystalGolemMinion(x, y, z);
    }

    private static Mob spawnVoidMob(int x, int y, int z, Random r) {
        return r.nextBoolean() ? new VoidStalker(x, y, z) : new ShadowCreeper(x, y, z);
    }

    private static Mob spawnLavaMob(int x, int y, int z, Random r) {
        return r.nextBoolean() ? new LavaGolem(x, y, z) : new FireImp(x, y, z);
    }

    private static Mob spawnShadowMob(int x, int y, int z, Random r) {
        return r.nextBoolean() ? new ShadowCreeper(x, y, z) : new GunSlinger(x, y, z);
    }

    private static Mob spawnMountainMob(int x, int y, int z, Random r, boolean night) {
        if (night) return new ZombieKnight(x, y, z);
        return r.nextBoolean() ? new IronGolem(x, y, z) : new Wolf(x, y, z);
    }
}
