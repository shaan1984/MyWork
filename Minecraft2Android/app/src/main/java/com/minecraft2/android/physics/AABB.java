package com.minecraft2.android.physics;

public class AABB {

    private float minX, minY, minZ, maxX, maxY, maxZ;

    public AABB(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX; this.minY = minY; this.minZ = minZ;
        this.maxX = maxX; this.maxY = maxY; this.maxZ = maxZ;
    }

    public void set(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX; this.minY = minY; this.minZ = minZ;
        this.maxX = maxX; this.maxY = maxY; this.maxZ = maxZ;
    }

    public boolean intersects(AABB other) {
        return minX < other.maxX && maxX > other.minX &&
               minY < other.maxY && maxY > other.minY &&
               minZ < other.maxZ && maxZ > other.minZ;
    }

    public boolean contains(float x, float y, float z) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    public AABB expand(float dx, float dy, float dz) {
        float newMinX = dx < 0 ? minX + dx : minX;
        float newMinY = dy < 0 ? minY + dy : minY;
        float newMinZ = dz < 0 ? minZ + dz : minZ;
        float newMaxX = dx > 0 ? maxX + dx : maxX;
        float newMaxY = dy > 0 ? maxY + dy : maxY;
        float newMaxZ = dz > 0 ? maxZ + dz : maxZ;
        return new AABB(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
    }

    public AABB offset(float dx, float dy, float dz) {
        return new AABB(minX+dx, minY+dy, minZ+dz, maxX+dx, maxY+dy, maxZ+dz);
    }

    public float getWidth() { return maxX - minX; }
    public float getHeight() { return maxY - minY; }
    public float getDepth() { return maxZ - minZ; }
    public float getCenterX() { return (minX + maxX) / 2; }
    public float getCenterY() { return (minY + maxY) / 2; }
    public float getCenterZ() { return (minZ + maxZ) / 2; }

    public float getMinX() { return minX; }
    public float getMinY() { return minY; }
    public float getMinZ() { return minZ; }
    public float getMaxX() { return maxX; }
    public float getMaxY() { return maxY; }
    public float getMaxZ() { return maxZ; }
}
