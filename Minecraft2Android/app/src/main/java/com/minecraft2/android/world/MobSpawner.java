package com.minecraft2.android.world;

import com.minecraft2.android.entity.mob.*;
import java.util.Random;

public class MobSpawner {

    public static Mob spawnForBiome(BiomeType biome, int x, int y, int z, float timeOfDay, Random random) {
        boolean isNight = timeOfDay > 0.75f || timeOfDay < 0.25f;

        return switch (biome) {
            case PLAINS, FOREST, BIRCH_FOREST -> isNight
                    ? spawnNightMob(x, y, z, random)
                    : spawnPassiveMob(x, y, z, random);
            case DESERT, BADLANDS -> spawnDesertMob(x, y, z, random, isNight);
            case JUNGLE -> spawnJungleMob(x, y, z, random, isNight);
            case SNOWY_TUNDRA, SNOWY_TAIGA, ICE_SPIRES -> spawnIceMob(x, y, z, random, isNight);
            case OCEAN, DEEP_OCEAN -> spawnOceanMob(x, y, z, random);
            case NETHER_WASTES, BASALT_DELTAS -> spawnNetherMob(x, y, z, random);
            case CRYSTAL_CAVES -> spawnCrystalMob(x, y, z, random);
            case VOID_WASTES -> spawnVoidMob(x, y, z, random);
            case LAVA_FIELDS -> spawnLavaMob(x, y, z, random);
            case SHADOW_FOREST -> spawnShadowMob(x, y, z, random);
            case TITANIUM_PEAKS -> spawnMountainMob(x, y, z, random, isNight);
            case FUNGAL_DEPTHS -> new GiantMushroom(x, y, z);
            default -> isNight ? spawnNightMob(x, y, z, random) : null;
        };
    }

    private static Mob spawnNightMob(int x, int y, int z, Random r) {
        return switch (r.nextInt(6)) {
            case 0 -> new Zombie(x, y, z);
            case 1 -> new Skeleton(x, y, z);
            case 2 -> new Creeper(x, y, z);
            case 3 -> new Spider(x, y, z);
            case 4 -> new ZombieKnight(x, y, z);
            default -> new Phantom(x, y, z);
        };
    }

    private static Mob spawnPassiveMob(int x, int y, int z, Random r) {
        return switch (r.nextInt(5)) {
            case 0 -> new Cow(x, y, z);
            case 1 -> new Sheep(x, y, z);
            case 2 -> new Pig(x, y, z);
            case 3 -> new Chicken(x, y, z);
            default -> new Horse(x, y, z);
        };
    }

    private static Mob spawnDesertMob(int x, int y, int z, Random r, boolean night) {
        if (night) return r.nextBoolean() ? new HuskZombie(x, y, z) : new Skeleton(x, y, z);
        return r.nextBoolean() ? new Rabbit(x, y, z) : null;
    }

    private static Mob spawnJungleMob(int x, int y, int z, Random r, boolean night) {
        if (night) return new CrystalSpider(x, y, z);
        return switch (r.nextInt(3)) {
            case 0 -> new Parrot(x, y, z);
            case 1 -> new Ocelot(x, y, z);
            default -> new PoisonFrog(x, y, z);
        };
    }

    private static Mob spawnIceMob(int x, int y, int z, Random r, boolean night) {
        if (night) return r.nextBoolean() ? new IceDragon(x, y, z) : new Skeleton(x, y, z);
        return r.nextBoolean() ? new PolarBear(x, y, z) : new IceDragon(x, y, z);
    }

    private static Mob spawnOceanMob(int x, int y, int z, Random r) {
        return switch (r.nextInt(3)) {
            case 0 -> new Squid(x, y, z);
            case 1 -> new GuardianMob(x, y, z);
            default -> new Drowned(x, y, z);
        };
    }

    private static Mob spawnNetherMob(int x, int y, int z, Random r) {
        return switch (r.nextInt(4)) {
            case 0 -> new Blaze(x, y, z);
            case 1 -> new PiglinBrute(x, y, z);
            case 2 -> new GhastMob(x, y, z);
            default -> new LavaGolem(x, y, z);
        };
    }

    private static Mob spawnCrystalMob(int x, int y, int z, Random r) {
        return r.nextBoolean() ? new CrystalSpider(x, y, z) : new CrystalGolemMinion(x, y, z);
    }

    private static Mob spawnVoidMob(int x, int y, int z, Random r) {
        return switch (r.nextInt(3)) {
            case 0 -> new VoidStalker(x, y, z);
            case 1 -> new ShadowCreeper(x, y, z);
            default -> new VoidStalker(x, y, z);
        };
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
