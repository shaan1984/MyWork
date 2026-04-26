package com.minecraft2.android.world;

import com.minecraft2.android.block.BlockType;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.entity.mob.Mob;
import com.minecraft2.android.entity.boss.Boss;
import com.minecraft2.android.furniture.Furniture;
import com.minecraft2.android.save.SaveManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class World {

    public static final int WIDTH = 4096;
    public static final int HEIGHT = 256;
    public static final int CHUNK_SIZE = 16;
    public static final int SEA_LEVEL = 64;

    private final long seed;
    private final String name;
    private final Map<Long, Chunk> chunks = new HashMap<>();
    private final WorldGenerator generator;
    private final List<Mob> mobs = new ArrayList<>();
    private final List<Boss> bosses = new ArrayList<>();
    private final List<Furniture> furnitureList = new ArrayList<>();
    private final ParticleSystem particleSystem = new ParticleSystem();
    private final Random random;

    private float timeOfDay = 0.3f;
    private long worldTick = 0;
    private boolean raining = false;
    private float temperature = 0.5f;
    private Player player = null;

    public World(long seed, String name) {
        this.seed = seed;
        this.name = name;
        this.random = new Random(seed);
        this.generator = new WorldGenerator(seed);
    }

    public void generate() {
        int chunksToGenerate = 32;
        for (int cx = -chunksToGenerate / 2; cx < chunksToGenerate / 2; cx++) {
            for (int cz = -chunksToGenerate / 2; cz < chunksToGenerate / 2; cz++) {
                loadOrGenerateChunk(cx, cz);
            }
        }
        spawnInitialMobs();
    }

    public Chunk getChunk(int cx, int cz) {
        long key = chunkKey(cx, cz);
        if (!chunks.containsKey(key)) {
            chunks.put(key, generator.generateChunk(cx, cz));
        }
        return chunks.get(key);
    }

    private Chunk loadOrGenerateChunk(int cx, int cz) {
        long key = chunkKey(cx, cz);
        if (!chunks.containsKey(key)) {
            chunks.put(key, generator.generateChunk(cx, cz));
        }
        return chunks.get(key);
    }

    public BlockType getBlock(int x, int y, int z) {
        if (y < 0 || y >= HEIGHT) return BlockType.AIR;
        int cx = Math.floorDiv(x, CHUNK_SIZE);
        int cz = Math.floorDiv(z, CHUNK_SIZE);
        Chunk chunk = getChunk(cx, cz);
        int lx = Math.floorMod(x, CHUNK_SIZE);
        int lz = Math.floorMod(z, CHUNK_SIZE);
        return chunk.getBlock(lx, y, lz);
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        if (y < 0 || y >= HEIGHT) return;
        int cx = Math.floorDiv(x, CHUNK_SIZE);
        int cz = Math.floorDiv(z, CHUNK_SIZE);
        Chunk chunk = getChunk(cx, cz);
        int lx = Math.floorMod(x, CHUNK_SIZE);
        int lz = Math.floorMod(z, CHUNK_SIZE);
        chunk.setBlock(lx, y, lz, type);
        chunk.markDirty();

        if (type == BlockType.AIR) {
            particleSystem.spawnBreakParticles(x, y, z, chunk.getBlock(lx, y, lz));
        }
    }

    public int getSurfaceHeight(int x, int z) {
        int cx = Math.floorDiv(x, CHUNK_SIZE);
        int cz = Math.floorDiv(z, CHUNK_SIZE);
        Chunk chunk = getChunk(cx, cz);
        int lx = Math.floorMod(x, CHUNK_SIZE);
        int lzl = Math.floorMod(z, CHUNK_SIZE);
        return chunk.getSurfaceHeight(lx, lzl);
    }

    public float[] findSafeSpawn() {
        int h = getSurfaceHeight(0, 0);
        return new float[]{0.5f, h + 1.5f, 0.5f};
    }

    public void update(float deltaTime) {
        worldTick++;
        timeOfDay += deltaTime / 1200f;
        if (timeOfDay >= 1f) timeOfDay -= 1f;

        updateMobs(deltaTime);
        updateBosses(deltaTime);
        particleSystem.update(deltaTime);

        if (random.nextFloat() < 0.00005f) {
            raining = !raining;
        }
    }

    public void randomTick() {
        for (Chunk chunk : chunks.values()) {
            chunk.randomTick(this, random);
        }
    }

    private void updateMobs(float deltaTime) {
        Iterator<Mob> it = mobs.iterator();
        while (it.hasNext()) {
            Mob mob = it.next();
            mob.update(deltaTime, this);
            if (mob.isDead() && mob.getDeathTimer() > 2.0f) {
                it.remove();
            }
        }

        if (worldTick % 200 == 0) {
            spawnNewMobs();
        }
    }

    private void updateBosses(float deltaTime) {
        for (Boss boss : bosses) {
            boss.update(deltaTime, this);
        }
        bosses.removeIf(b -> b.isDead() && b.getDeathTimer() > 3.0f);
    }

    private void spawnInitialMobs() {
        for (int i = 0; i < 30; i++) {
            trySpawnRandomMob(random.nextInt(200) - 100, random.nextInt(200) - 100);
        }
    }

    private void spawnNewMobs() {
        if (mobs.size() < 100) {
            for (int i = 0; i < 3; i++) {
                int rx = random.nextInt(400) - 200;
                int rz = random.nextInt(400) - 200;
                trySpawnRandomMob(rx, rz);
            }
        }
    }

    private void trySpawnRandomMob(int x, int z) {
        int y = getSurfaceHeight(x, z);
        BiomeType biome = generator.getBiome(x, z);
        Mob mob = MobSpawner.spawnForBiome(biome, x, y + 1, z, timeOfDay, random);
        if (mob != null) mobs.add(mob);
    }

    public void spawnBoss(Boss boss) {
        bosses.add(boss);
    }

    public void addFurniture(Furniture f) {
        furnitureList.add(f);
    }

    public List<Mob> getNearbyMobs(float x, float y, float z, float radius) {
        List<Mob> nearby = new ArrayList<>();
        for (Mob mob : mobs) {
            float dx = mob.getX() - x, dy = mob.getY() - y, dz = mob.getZ() - z;
            if (dx*dx + dy*dy + dz*dz < radius * radius) nearby.add(mob);
        }
        return nearby;
    }

    public List<Furniture> getNearbyFurniture(float x, float y, float z, float radius) {
        List<Furniture> nearby = new ArrayList<>();
        for (Furniture f : furnitureList) {
            float dx = f.getX() - x, dy = f.getY() - y, dz = f.getZ() - z;
            if (dx*dx + dy*dy + dz*dz < radius * radius) nearby.add(f);
        }
        return nearby;
    }

    public void loadFromData(SaveManager.WorldData data) {
        // Chunk data loaded from save
    }

    public BiomeType getBiomeAt(int x, int z) {
        return generator.getBiome(x, z);
    }

    public boolean isExposed(int x, int y, int z) {
        return getBlock(x+1,y,z) == BlockType.AIR || getBlock(x-1,y,z) == BlockType.AIR ||
               getBlock(x,y+1,z) == BlockType.AIR || getBlock(x,y-1,z) == BlockType.AIR ||
               getBlock(x,y,z+1) == BlockType.AIR || getBlock(x,y,z-1) == BlockType.AIR;
    }

    public boolean isNight() { return timeOfDay > 0.75f || timeOfDay < 0.25f; }
    public float getTimeOfDay() { return timeOfDay; }
    public boolean isRaining() { return raining; }
    public List<Boss> getActiveBosses() { return bosses; }
    public List<Mob> getMobs() { return mobs; }
    public ParticleSystem getParticleSystem() { return particleSystem; }
    public void setPlayer(Player p) { this.player = p; }
    public Player getNearestPlayer() { return player; }
    public long getSeed() { return seed; }
    public String getName() { return name; }
    public long getWorldTick() { return worldTick; }

    private long chunkKey(int cx, int cz) {
        return ((long) cx << 32) | (cz & 0xFFFFFFFFL);
    }
}
