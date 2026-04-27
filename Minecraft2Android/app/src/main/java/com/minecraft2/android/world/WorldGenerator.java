package com.minecraft2.android.world;

import com.minecraft2.android.block.BlockType;
import java.util.Random;

public class WorldGenerator {

    private final long seed;
    private final Random random;
    private final NoiseGenerator terrainNoise;
    private final NoiseGenerator caveNoise;
    private final NoiseGenerator biomeNoise;
    private final NoiseGenerator temperatureNoise;
    private final NoiseGenerator humidityNoise;

    public WorldGenerator(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
        this.terrainNoise = new NoiseGenerator(seed);
        this.caveNoise = new NoiseGenerator(seed ^ 0xDEADBEEFL);
        this.biomeNoise = new NoiseGenerator(seed ^ 0xCAFEBABEL);
        this.temperatureNoise = new NoiseGenerator(seed ^ 0x12345678L);
        this.humidityNoise = new NoiseGenerator(seed ^ 0x87654321L);
    }

    public Chunk generateChunk(int cx, int cz) {
        Chunk chunk = new Chunk(cx, cz);
        int worldX = cx * Chunk.SIZE;
        int worldZ = cz * Chunk.SIZE;

        for (int lx = 0; lx < Chunk.SIZE; lx++) {
            for (int lz = 0; lz < Chunk.SIZE; lz++) {
                int wx = worldX + lx;
                int wz = worldZ + lz;
                BiomeType biome = getBiome(wx, wz);
                generateColumn(chunk, lx, lz, wx, wz, biome);
            }
        }

        generateCaves(chunk, worldX, worldZ);
        generateOres(chunk, worldX, worldZ);
        generateStructures(chunk, worldX, worldZ);
        chunk.computeSurfaceHeights();
        chunk.setGenerated(true);
        return chunk;
    }

    private void generateColumn(Chunk chunk, int lx, int lz, int wx, int wz, BiomeType biome) {
        int surfaceHeight = getSurfaceHeight(wx, wz, biome);
        int seaLevel = World.SEA_LEVEL;

        // Bedrock layer
        chunk.setBlock(lx, 0, lz, BlockType.BEDROCK);
        for (int y = 1; y <= 4; y++) {
            if (random.nextInt(5) < 5 - y) chunk.setBlock(lx, y, lz, BlockType.BEDROCK);
            else chunk.setBlock(lx, y, lz, BlockType.STONE);
        }

        // Stone layer
        for (int y = 5; y < surfaceHeight - 4; y++) {
            if (y > 128) chunk.setBlock(lx, y, lz, BlockType.DEEPSLATE);
            else chunk.setBlock(lx, y, lz, BlockType.STONE);
        }

        // Surface layers
        applySurfaceLayers(chunk, lx, lz, wx, wz, biome, surfaceHeight, seaLevel);

        // Water fill
        if (surfaceHeight < seaLevel) {
            for (int y = surfaceHeight + 1; y <= seaLevel; y++) {
                if (chunk.getBlock(lx, y, lz) == BlockType.AIR) {
                    chunk.setBlock(lx, y, lz, BlockType.WATER);
                }
            }
        }
    }

    private void applySurfaceLayers(Chunk chunk, int lx, int lz, int wx, int wz,
                                     BiomeType biome, int surfaceHeight, int seaLevel) {
        if (biome == BiomeType.DESERT || biome == BiomeType.BADLANDS) {
            for (int y = surfaceHeight - 3; y <= surfaceHeight; y++) chunk.setBlock(lx, y, lz, BlockType.SAND);
            if (surfaceHeight - 4 >= 0) chunk.setBlock(lx, surfaceHeight - 4, lz, BlockType.SANDSTONE);
        } else if (biome == BiomeType.SNOWY_TUNDRA || biome == BiomeType.SNOWY_TAIGA || biome == BiomeType.ICE_SPIRES) {
            chunk.setBlock(lx, surfaceHeight, lz, BlockType.SNOW);
            for (int d = 1; d <= 3; d++) chunk.setBlock(lx, surfaceHeight - d, lz, BlockType.DIRT);
        } else if (biome == BiomeType.OCEAN || biome == BiomeType.DEEP_OCEAN) {
            for (int d = 0; d <= 3; d++) chunk.setBlock(lx, surfaceHeight - d, lz, BlockType.GRAVEL);
        } else if (biome == BiomeType.BEACH) {
            for (int d = 0; d <= 3; d++) chunk.setBlock(lx, surfaceHeight - d, lz, BlockType.SAND);
        } else if (biome == BiomeType.SWAMP) {
            chunk.setBlock(lx, surfaceHeight, lz, BlockType.GRASS);
            for (int d = 1; d <= 3; d++) chunk.setBlock(lx, surfaceHeight - d, lz, BlockType.DIRT);
        } else if (biome == BiomeType.CRYSTAL_CAVES) {
            chunk.setBlock(lx, surfaceHeight, lz, BlockType.CRYSTAL_BLOCK);
            for (int d = 1; d <= 3; d++) chunk.setBlock(lx, surfaceHeight - d, lz, BlockType.STONE);
        } else if (biome == BiomeType.VOID_WASTES) {
            for (int d = 0; d <= 4; d++) chunk.setBlock(lx, surfaceHeight - d, lz, BlockType.VOID_STONE);
        } else if (biome == BiomeType.LAVA_FIELDS) {
            chunk.setBlock(lx, surfaceHeight, lz, BlockType.LAVA_STONE);
            for (int d = 1; d <= 3; d++) chunk.setBlock(lx, surfaceHeight - d, lz, BlockType.NETHERRACK);
        } else if (biome == BiomeType.TITANIUM_PEAKS) {
            for (int d = 0; d <= 3; d++) chunk.setBlock(lx, surfaceHeight - d, lz, BlockType.STONE);
        } else {
            chunk.setBlock(lx, surfaceHeight, lz, BlockType.GRASS);
            for (int d = 1; d <= 3; d++) chunk.setBlock(lx, surfaceHeight - d, lz, BlockType.DIRT);
        }
    }

    private void generateCaves(Chunk chunk, int worldX, int worldZ) {
        for (int i = 0; i < 8; i++) {
            int startX = random.nextInt(Chunk.SIZE);
            int startY = 10 + random.nextInt(60);
            int startZ = random.nextInt(Chunk.SIZE);
            carveCave(chunk, startX, startY, startZ, 12 + random.nextInt(20));
        }

        // Carve ravines occasionally
        if (random.nextInt(10) == 0) {
            carveRavine(chunk, worldX, worldZ);
        }
    }

    private void carveCave(Chunk chunk, int sx, int sy, int sz, int length) {
        float x = sx, y = sy, z = sz;
        float dx = random.nextFloat() - 0.5f;
        float dy = (random.nextFloat() - 0.5f) * 0.3f;
        float dz = random.nextFloat() - 0.5f;
        float mag = (float) Math.sqrt(dx*dx + dz*dz);
        dx /= mag; dz /= mag;

        for (int step = 0; step < length; step++) {
            int radius = 1 + random.nextInt(2);
            for (int rx = -radius; rx <= radius; rx++) {
                for (int ry = -1; ry <= 1; ry++) {
                    for (int rz = -radius; rz <= radius; rz++) {
                        if (rx*rx + ry*ry + rz*rz <= radius * radius) {
                            int bx = (int)x + rx;
                            int by = (int)y + ry;
                            int bz = (int)z + rz;
                            if (by > 4 && by < Chunk.HEIGHT - 1 &&
                                chunk.getBlock(bx, by, bz) != BlockType.BEDROCK) {
                                chunk.setBlock(bx, by, bz, BlockType.AIR);
                            }
                        }
                    }
                }
            }
            x += dx;
            y += dy;
            z += dz;
            dx += (random.nextFloat() - 0.5f) * 0.2f;
            dy += (random.nextFloat() - 0.5f) * 0.1f;
            dz += (random.nextFloat() - 0.5f) * 0.2f;
        }
    }

    private void carveRavine(Chunk chunk, int worldX, int worldZ) {
        int rx = random.nextInt(Chunk.SIZE);
        int startY = 40 + random.nextInt(40);
        int depth = 20 + random.nextInt(30);
        for (int y = startY; y > startY - depth && y > 4; y--) {
            for (int w = -1; w <= 1; w++) {
                if (rx + w >= 0 && rx + w < Chunk.SIZE) {
                    for (int z = 0; z < Chunk.SIZE; z++) {
                        chunk.setBlock(rx + w, y, z, BlockType.AIR);
                    }
                }
            }
        }
    }

    private void generateOres(Chunk chunk, int worldX, int worldZ) {
        placeOreVeins(chunk, BlockType.COAL_ORE, 20, 8, 5, 128);
        placeOreVeins(chunk, BlockType.IRON_ORE, 20, 6, 5, 72);
        placeOreVeins(chunk, BlockType.COPPER_ORE, 16, 5, 5, 96);
        placeOreVeins(chunk, BlockType.GOLD_ORE, 8, 4, 5, 40);
        placeOreVeins(chunk, BlockType.LAPIS_ORE, 4, 4, 5, 34);
        placeOreVeins(chunk, BlockType.REDSTONE_ORE, 8, 5, 5, 15);
        placeOreVeins(chunk, BlockType.DIAMOND_ORE, 4, 3, 5, 15);
        placeOreVeins(chunk, BlockType.EMERALD_ORE, 2, 2, 5, 29);
        placeOreVeins(chunk, BlockType.NETHERITE_ORE, 1, 2, 5, 15);

        // New MC2 ores
        placeOreVeins(chunk, BlockType.TITANIUM_ORE, 6, 4, 10, 60);
        placeOreVeins(chunk, BlockType.TUNGSTEN_ORE, 4, 3, 20, 50);
        placeOreVeins(chunk, BlockType.ADAMANTITE_ORE, 2, 2, 30, 30);
        placeOreVeins(chunk, BlockType.CRYSTAL_ORE, 4, 3, 40, 60);
        placeOreVeins(chunk, BlockType.VOID_ORE, 1, 1, 5, 10);
        placeOreVeins(chunk, BlockType.STARLIGHT_ORE, 3, 3, 50, 80);
        placeOreVeins(chunk, BlockType.SHADOW_ORE, 2, 2, 5, 20);
    }

    private void placeOreVeins(Chunk chunk, BlockType ore, int count, int veinSize, int minY, int maxY) {
        for (int i = 0; i < count; i++) {
            int ox = random.nextInt(Chunk.SIZE);
            int oy = minY + random.nextInt(Math.max(1, maxY - minY));
            int oz = random.nextInt(Chunk.SIZE);
            for (int j = 0; j < veinSize; j++) {
                int bx = ox + random.nextInt(3) - 1;
                int by = oy + random.nextInt(3) - 1;
                int bz = oz + random.nextInt(3) - 1;
                if (chunk.getBlock(bx, by, bz) == BlockType.STONE ||
                    chunk.getBlock(bx, by, bz) == BlockType.DEEPSLATE) {
                    chunk.setBlock(bx, by, bz, ore);
                }
            }
        }
    }

    private void generateStructures(Chunk chunk, int worldX, int worldZ) {
        Random chunkRand = new Random(seed ^ ((long)worldX * 341873128712L) ^ ((long)worldZ * 132897987541L));
        BiomeType biome = getBiome(worldX + 8, worldZ + 8);

        if (chunkRand.nextInt(20) == 0) {
            generateTree(chunk, biome, chunkRand);
        }
        if (chunkRand.nextInt(80) == 0) {
            generateVillageHouse(chunk, chunkRand);
        }
        if (chunkRand.nextInt(200) == 0) {
            generateDungeon(chunk, chunkRand);
        }
        if (biome == BiomeType.DESERT && chunkRand.nextInt(30) == 0) {
            generatePyramid(chunk, chunkRand);
        }
        if (biome == BiomeType.CRYSTAL_CAVES && chunkRand.nextInt(10) == 0) {
            generateCrystalFormation(chunk, chunkRand);
        }
    }

    private void generateTree(Chunk chunk, BiomeType biome, Random r) {
        int tx = 2 + r.nextInt(12);
        int tz = 2 + r.nextInt(12);
        int surface = chunk.getSurfaceHeight(tx, tz);
        if (chunk.getBlock(tx, surface, tz) != BlockType.GRASS) return;

        BlockType logType, leafType;
        int height;
        if (biome == BiomeType.BIRCH_FOREST) {
            logType = BlockType.BIRCH_LOG; leafType = BlockType.BIRCH_LEAVES; height = 5 + r.nextInt(3);
        } else if (biome == BiomeType.JUNGLE) {
            logType = BlockType.JUNGLE_LOG; leafType = BlockType.JUNGLE_LEAVES; height = 10 + r.nextInt(8);
        } else if (biome == BiomeType.TAIGA || biome == BiomeType.SNOWY_TAIGA) {
            logType = BlockType.SPRUCE_LOG; leafType = BlockType.SPRUCE_LEAVES; height = 8 + r.nextInt(4);
        } else if (biome == BiomeType.DARK_FOREST) {
            logType = BlockType.DARK_OAK_LOG; leafType = BlockType.ACACIA_LEAVES; height = 6 + r.nextInt(3);
        } else {
            logType = BlockType.OAK_LOG; leafType = BlockType.OAK_LEAVES; height = 4 + r.nextInt(3);
        }

        for (int y = surface + 1; y <= surface + height; y++) chunk.setBlock(tx, y, tz, logType);
        for (int lx = -2; lx <= 2; lx++) {
            for (int lz = -2; lz <= 2; lz++) {
                for (int ly = -1; ly <= 1; ly++) {
                    int bx = tx + lx, by = surface + height + ly, bz = tz + lz;
                    if (Math.abs(lx) == 2 && Math.abs(lz) == 2 && ly == -1 && r.nextBoolean()) continue;
                    if (chunk.getBlock(bx, by, bz) == BlockType.AIR) chunk.setBlock(bx, by, bz, leafType);
                }
            }
        }
        if (r.nextBoolean()) chunk.setBlock(tx, surface + height + 1, tz, leafType);
    }

    private void generateVillageHouse(Chunk chunk, Random r) {
        int bx = 2 + r.nextInt(8), bz = 2 + r.nextInt(8);
        int surface = chunk.getSurfaceHeight(bx, bz);
        int w = 5 + r.nextInt(4), d = 5 + r.nextInt(4), h = 4;
        for (int x = bx; x < bx + w && x < Chunk.SIZE; x++) {
            for (int z = bz; z < bz + d && z < Chunk.SIZE; z++) {
                for (int y = surface + 1; y <= surface + h; y++) {
                    boolean isWall = (x == bx || x == bx + w - 1 || z == bz || z == bz + d - 1);
                    boolean isRoof = (y == surface + h);
                    if (isWall || isRoof) chunk.setBlock(x, y, z, BlockType.OAK_PLANKS);
                }
                chunk.setBlock(x, surface + h + 1, z, BlockType.OAK_PLANKS);
            }
        }
    }

    private void generateDungeon(Chunk chunk, Random r) {
        int dx = 2 + r.nextInt(10), dz = 2 + r.nextInt(10);
        int dy = 20 + r.nextInt(30);
        for (int x = dx - 3; x <= dx + 3 && x >= 0 && x < Chunk.SIZE; x++) {
            for (int z = dz - 3; z <= dz + 3 && z >= 0 && z < Chunk.SIZE; z++) {
                for (int y = dy; y <= dy + 5; y++) {
                    boolean isWall = (x == dx - 3 || x == dx + 3 || z == dz - 3 || z == dz + 3);
                    if (isWall) chunk.setBlock(x, y, z, BlockType.COBBLESTONE);
                    else chunk.setBlock(x, y, z, BlockType.AIR);
                }
                chunk.setBlock(dx, dy, dz, BlockType.CHEST);
                chunk.setBlock(dx + 1, dy, dz, BlockType.CHEST);
            }
        }
    }

    private void generatePyramid(Chunk chunk, Random r) {
        int px = 3 + r.nextInt(8), pz = 3 + r.nextInt(8);
        int surface = chunk.getSurfaceHeight(px, pz);
        int height = 8;
        for (int y = 0; y < height; y++) {
            int size = height - y;
            for (int x = px - size; x <= px + size && x >= 0 && x < Chunk.SIZE; x++) {
                for (int z = pz - size; z <= pz + size && z >= 0 && z < Chunk.SIZE; z++) {
                    chunk.setBlock(x, surface + y, z, BlockType.SAND);
                }
            }
        }
    }

    private void generateCrystalFormation(Chunk chunk, Random r) {
        int cx = r.nextInt(Chunk.SIZE);
        int cz = r.nextInt(Chunk.SIZE);
        int surface = chunk.getSurfaceHeight(cx, cz);
        int crystalHeight = 3 + r.nextInt(8);
        for (int y = surface + 1; y <= surface + crystalHeight; y++) {
            chunk.setBlock(cx, y, cz, BlockType.CRYSTAL_BLOCK);
        }
        for (int i = 0; i < 4; i++) {
            int ox = cx + r.nextInt(5) - 2, oz = cz + r.nextInt(5) - 2;
            int h = 1 + r.nextInt(4);
            int s = chunk.getSurfaceHeight(ox, oz);
            for (int y = s + 1; y <= s + h; y++) {
                if (ox >= 0 && ox < Chunk.SIZE && oz >= 0 && oz < Chunk.SIZE)
                    chunk.setBlock(ox, y, oz, BlockType.CRYSTAL_BLOCK);
            }
        }
    }

    public BiomeType getBiome(int x, int z) {
        float temp = (float) temperatureNoise.noise(x * 0.002f, z * 0.002f);
        float humid = (float) humidityNoise.noise(x * 0.002f, z * 0.002f);
        temp = (temp + 1) / 2f;
        humid = (humid + 1) / 2f;

        float specialNoise = (float) biomeNoise.noise(x * 0.001f, z * 0.001f);

        if (specialNoise > 0.7f) return BiomeType.CRYSTAL_CAVES;
        if (specialNoise < -0.75f) return BiomeType.VOID_WASTES;
        if (specialNoise > 0.6f && temp > 0.7f) return BiomeType.LAVA_FIELDS;
        if (specialNoise < -0.6f && temp < 0.2f) return BiomeType.ICE_SPIRES;
        if (specialNoise > 0.5f && humid > 0.7f) return BiomeType.SHADOW_FOREST;

        if (temp < 0.1f) return humid > 0.5f ? BiomeType.SNOWY_TAIGA : BiomeType.SNOWY_TUNDRA;
        if (temp < 0.25f) return humid > 0.6f ? BiomeType.TAIGA : BiomeType.MOUNTAINS;
        if (temp < 0.5f) return humid > 0.7f ? BiomeType.FOREST : BiomeType.PLAINS;
        if (temp < 0.75f) {
            if (humid > 0.8f) return BiomeType.JUNGLE;
            if (humid > 0.5f) return BiomeType.BIRCH_FOREST;
            if (humid < 0.2f) return BiomeType.SAVANNA;
            return BiomeType.PLAINS;
        }
        if (humid < 0.1f) return BiomeType.DESERT;
        if (humid < 0.3f) return BiomeType.BADLANDS;
        return BiomeType.FOREST;
    }

    public int getSurfaceHeight(int x, int z, BiomeType biome) {
        float base = biome.getBaseHeight();
        float noise = (float) terrainNoise.noise(x * 0.01f, z * 0.01f);
        float detailNoise = (float) terrainNoise.noise(x * 0.05f, z * 0.05f) * 0.3f;
        float height = base + noise * 20f + detailNoise * 8f;

        if (biome == BiomeType.MOUNTAINS || biome == BiomeType.TITANIUM_PEAKS) {
            float mountainNoise = (float) terrainNoise.noise(x * 0.02f, z * 0.02f);
            height += Math.max(0, mountainNoise) * 60f;
        }
        return Math.max(5, Math.min(Chunk.HEIGHT - 2, (int) height));
    }
}
