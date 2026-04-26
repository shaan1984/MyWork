package com.minecraft2.android.weapon;

import com.minecraft2.android.entity.Player;
import com.minecraft2.android.entity.mob.Mob;
import com.minecraft2.android.entity.boss.Boss;
import com.minecraft2.android.world.World;
import java.util.List;
import java.util.Random;

public class Gun {

    private final GunType type;
    private int currentMag;
    private int reserveAmmo;
    private float shootCooldown = 0;
    private float reloadTimer = -1;
    private boolean reloading = false;
    private final Random random = new Random();

    // Recoil
    private float recoilX = 0, recoilY = 0;
    private float recoilDecay = 8f;

    public Gun(GunType type, int reserveAmmo) {
        this.type = type;
        this.currentMag = type.getMagazineSize();
        this.reserveAmmo = reserveAmmo;
    }

    public void update(float deltaTime) {
        if (shootCooldown > 0) shootCooldown -= deltaTime;
        if (reloading) {
            reloadTimer -= deltaTime;
            if (reloadTimer <= 0) {
                finishReload();
            }
        }
        // Decay recoil
        recoilX *= Math.max(0, 1f - recoilDecay * deltaTime);
        recoilY *= Math.max(0, 1f - recoilDecay * deltaTime);
    }

    public boolean shoot(Player player, World world) {
        if (reloading || shootCooldown > 0) return false;
        if (currentMag <= 0) {
            if (reserveAmmo > 0) startReload();
            return false;
        }

        float px = player.getX(), py = player.getY() + 1.6f, pz = player.getZ();
        float yaw = (float) Math.toRadians(player.getYaw());
        float pitch = (float) Math.toRadians(player.getPitch());

        float cosP = (float) Math.cos(pitch);
        float dirX = (float) Math.sin(yaw) * cosP;
        float dirY = (float) Math.sin(pitch);
        float dirZ = (float) Math.cos(yaw) * cosP;

        for (int p = 0; p < type.getPellets(); p++) {
            float spreadX = (random.nextFloat() - 0.5f) * type.getSpread() * 0.05f;
            float spreadY = (random.nextFloat() - 0.5f) * type.getSpread() * 0.05f;
            float spreadZ = (random.nextFloat() - 0.5f) * type.getSpread() * 0.05f;

            float finalDirX = dirX + spreadX;
            float finalDirY = dirY + spreadY;
            float finalDirZ = dirZ + spreadZ;

            performRaycast(px, py, pz, finalDirX, finalDirY, finalDirZ, world, player);
        }

        currentMag -= type.getBulletsPerShot();
        shootCooldown = type.getFireRate();

        // Apply recoil
        recoilY += type.getSpread() * 0.2f;
        recoilX += (random.nextFloat() - 0.5f) * type.getSpread() * 0.1f;

        world.getParticleSystem().spawnGunSmoke(px + dirX, py + dirY, pz + dirZ);

        if (currentMag <= 0 && reserveAmmo > 0) startReload();
        return true;
    }

    private void performRaycast(float ox, float oy, float oz,
                                 float dx, float dy, float dz,
                                 World world, Player player) {
        float len = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
        dx /= len; dy /= len; dz /= len;

        List<Mob> mobs = world.getNearbyMobs(ox, oy, oz, type.getRange());
        List<Boss> bosses = world.getActiveBosses();

        for (Mob mob : mobs) {
            if (intersectsBounds(ox, oy, oz, dx, dy, dz, mob.getX(), mob.getY(), mob.getZ(),
                    mob.getBoundingBox().getWidth(), mob.getBoundingBox().getHeight())) {
                float dist = (float) Math.sqrt(Math.pow(mob.getX()-ox,2)+Math.pow(mob.getZ()-oz,2));
                float falloff = Math.max(0.2f, 1f - dist / type.getRange());
                float damage = type.getDamage() * falloff;
                mob.damage(damage);
                world.getParticleSystem().spawnBloodParticles(mob.getX(), mob.getY() + mob.getBoundingBox().getHeight()/2, mob.getZ());

                if (type.isExplosive()) {
                    handleExplosion(mob.getX(), mob.getY(), mob.getZ(), world, player);
                }
                return;
            }
        }

        for (Boss boss : bosses) {
            if (intersectsBounds(ox, oy, oz, dx, dy, dz, boss.getX(), boss.getY(), boss.getZ(),
                    boss.getType().getWidth(), boss.getType().getHeight())) {
                float dist = (float) Math.sqrt(Math.pow(boss.getX()-ox,2)+Math.pow(boss.getZ()-oz,2));
                float falloff = Math.max(0.3f, 1f - dist / type.getRange());
                boss.damage(type.getDamage() * falloff);
                if (type.isExplosive()) {
                    handleExplosion(boss.getX(), boss.getY(), boss.getZ(), world, player);
                }
                return;
            }
        }

        // Hit terrain
        if (type.isExplosive()) {
            float hitX = ox + dx * type.getRange();
            float hitY = oy + dy * type.getRange();
            float hitZ = oz + dz * type.getRange();
            handleExplosion(hitX, hitY, hitZ, world, player);
        }
    }

    private boolean intersectsBounds(float ox, float oy, float oz,
                                      float dx, float dy, float dz,
                                      float ex, float ey, float ez,
                                      float w, float h) {
        float halfW = w / 2f;
        float minX = ex - halfW, maxX = ex + halfW;
        float minY = ey, maxY = ey + h;
        float minZ = ez - halfW, maxZ = ez + halfW;

        float tMin = 0f, tMax = type.getRange();

        for (int axis = 0; axis < 3; axis++) {
            float o, d, min, max;
            if (axis == 0) { o = ox; d = dx; min = minX; max = maxX; }
            else if (axis == 1) { o = oy; d = dy; min = minY; max = maxY; }
            else { o = oz; d = dz; min = minZ; max = maxZ; }

            if (Math.abs(d) < 1e-8f) {
                if (o < min || o > max) return false;
            } else {
                float t1 = (min - o) / d;
                float t2 = (max - o) / d;
                if (t1 > t2) { float tmp = t1; t1 = t2; t2 = tmp; }
                tMin = Math.max(tMin, t1);
                tMax = Math.min(tMax, t2);
                if (tMin > tMax) return false;
            }
        }
        return tMin >= 0;
    }

    private void handleExplosion(float x, float y, float z, World world, Player player) {
        float radius = 5f;
        world.getParticleSystem().spawnExplosionParticles(x, y, z);

        // Block damage
        for (int bx = (int)x - (int)radius; bx <= (int)x + (int)radius; bx++) {
            for (int by = (int)y - (int)radius; by <= (int)y + (int)radius; by++) {
                for (int bz = (int)z - (int)radius; bz <= (int)z + (int)radius; bz++) {
                    float d = (float)Math.sqrt(Math.pow(bx-x,2)+Math.pow(by-y,2)+Math.pow(bz-z,2));
                    if (d < radius && random.nextFloat() < 0.6f) {
                        com.minecraft2.android.block.BlockType block = world.getBlock(bx, by, bz);
                        if (block != null && block != com.minecraft2.android.block.BlockType.AIR &&
                                block.isBreakable() && block.getBlastResistance() < 60f) {
                            world.setBlock(bx, by, bz, com.minecraft2.android.block.BlockType.AIR);
                        }
                    }
                }
            }
        }

        // Player knockback / damage
        float playerDist = (float) Math.sqrt(Math.pow(player.getX()-x,2)+Math.pow(player.getZ()-z,2));
        if (playerDist < radius) {
            float damage = type.getDamage() * 0.5f * (1f - playerDist / radius);
            player.damage(damage);
            float dx = player.getX() - x, dz = player.getZ() - z;
            float d = Math.max(0.1f, (float)Math.sqrt(dx*dx+dz*dz));
            player.setVelX(player.getVelX() + (dx/d) * 10f);
            player.setVelY(player.getVelY() + 8f);
            player.setVelZ(player.getVelZ() + (dz/d) * 10f);
        }
    }

    public void startReload() {
        if (!reloading && reserveAmmo > 0 && currentMag < type.getMagazineSize()) {
            reloading = true;
            reloadTimer = type.getReloadTime();
        }
    }

    private void finishReload() {
        int needed = type.getMagazineSize() - currentMag;
        int reloadAmount = Math.min(needed, reserveAmmo);
        currentMag += reloadAmount;
        reserveAmmo -= reloadAmount;
        reloading = false;
        reloadTimer = -1;
    }

    public void addReserveAmmo(int amount) { reserveAmmo += amount; }
    public GunType getType() { return type; }
    public int getCurrentMag() { return currentMag; }
    public int getReserveAmmo() { return reserveAmmo; }
    public boolean isReloading() { return reloading; }
    public float getReloadProgress() { return reloading ? 1f - (reloadTimer / type.getReloadTime()) : 0; }
    public float getRecoilX() { return recoilX; }
    public float getRecoilY() { return recoilY; }
}
