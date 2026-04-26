package com.minecraft2.android.world;

import com.minecraft2.android.block.BlockType;
import java.util.Random;

public class Chunk {

    public static final int SIZE = 16;
    public static final int HEIGHT = 256;

    private final int chunkX, chunkZ;
    private final BlockType[][][] blocks;
    private final int[] surfaceHeights;
    private boolean dirty = false;
    private boolean generated = false;

    public Chunk(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.blocks = new BlockType[SIZE][HEIGHT][SIZE];
        this.surfaceHeights = new int[SIZE * SIZE];
        fill(BlockType.AIR);
    }

    private void fill(BlockType type) {
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < HEIGHT; y++)
                for (int z = 0; z < SIZE; z++)
                    blocks[x][y][z] = type;
    }

    public BlockType getBlock(int x, int y, int z) {
        if (outOfBounds(x, y, z)) return BlockType.AIR;
        BlockType b = blocks[x][y][z];
        return b == null ? BlockType.AIR : b;
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        if (outOfBounds(x, y, z)) return;
        blocks[x][y][z] = type;
        updateSurfaceHeight(x, y, z, type);
    }

    private void updateSurfaceHeight(int x, int y, int z, BlockType type) {
        int idx = x * SIZE + z;
        if (type != BlockType.AIR && y > surfaceHeights[idx]) {
            surfaceHeights[idx] = y;
        } else if (type == BlockType.AIR && y == surfaceHeights[idx]) {
            for (int ny = y - 1; ny >= 0; ny--) {
                if (blocks[x][ny][z] != BlockType.AIR) {
                    surfaceHeights[idx] = ny;
                    break;
                }
            }
        }
    }

    public int getSurfaceHeight(int x, int z) {
        if (x < 0 || x >= SIZE || z < 0 || z >= SIZE) return 64;
        return surfaceHeights[x * SIZE + z];
    }

    public void computeSurfaceHeights() {
        for (int x = 0; x < SIZE; x++) {
            for (int z = 0; z < SIZE; z++) {
                int idx = x * SIZE + z;
                surfaceHeights[idx] = 0;
                for (int y = HEIGHT - 1; y >= 0; y--) {
                    if (blocks[x][y][z] != BlockType.AIR) {
                        surfaceHeights[idx] = y;
                        break;
                    }
                }
            }
        }
    }

    public void randomTick(World world, Random random) {
        int worldX = chunkX * SIZE;
        int worldZ = chunkZ * SIZE;
        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(SIZE);
            int y = random.nextInt(HEIGHT);
            int z = random.nextInt(SIZE);
            BlockType block = blocks[x][y][z];
            if (block == BlockType.GRASS) {
                if (y + 1 < HEIGHT && blocks[x][y+1][z] == BlockType.AIR) {
                    // Stay grass
                } else {
                    blocks[x][y][z] = BlockType.DIRT;
                }
            } else if (block == BlockType.DIRT) {
                if (y + 1 < HEIGHT && blocks[x][y+1][z] == BlockType.AIR) {
                    blocks[x][y][z] = BlockType.GRASS;
                }
            } else if (block == BlockType.SAND) {
                if (y - 1 >= 0 && blocks[x][y-1][z] == BlockType.AIR) {
                    blocks[x][y][z] = BlockType.AIR;
                    if (y - 1 >= 0) blocks[x][y-1][z] = BlockType.SAND;
                }
            }
        }
    }

    public void markDirty() { dirty = true; }
    public boolean isDirty() { return dirty; }
    public void clearDirty() { dirty = false; }
    public boolean isGenerated() { return generated; }
    public void setGenerated(boolean g) { this.generated = g; }
    public int getChunkX() { return chunkX; }
    public int getChunkZ() { return chunkZ; }
    public BlockType[][][] getBlocks() { return blocks; }

    private boolean outOfBounds(int x, int y, int z) {
        return x < 0 || x >= SIZE || y < 0 || y >= HEIGHT || z < 0 || z >= SIZE;
    }
}
