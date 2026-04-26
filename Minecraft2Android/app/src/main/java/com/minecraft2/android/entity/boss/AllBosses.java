package com.minecraft2.android.entity.boss;

import com.minecraft2.android.entity.mob.CrystalGolemMinion;
import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// ======== SKELETON KING ========
class SkeletonKing extends Boss {
    private static final Random R = new Random();
    private int minionsSpawned = 0;

    public SkeletonKing(float x, float y, float z) {
        super(x, y, z, BossType.SKELETON_KING);
    }

    @Override
    protected void updateBossAI(float deltaTime, World world) {
        if (phase == 2 && minionsSpawned < 4) {
            for (int i = 0; i < 4; i++) {
                world.getMobs().add(new com.minecraft2.android.entity.mob.Skeleton(
                        x + R.nextFloat() * 10 - 5, y, z + R.nextFloat() * 10 - 5));
            }
            minionsSpawned = 4;
        }
        if (phase == 3 && minionsSpawned < 8) {
            for (int i = 0; i < 4; i++) {
                world.getMobs().add(new com.minecraft2.android.entity.mob.ZombieKnight(
                        x + R.nextFloat() * 10 - 5, y, z + R.nextFloat() * 10 - 5));
            }
            minionsSpawned = 8;
        }
    }

    @Override
    protected void performMainAttack() {
        if (targetPlayer == null) return;
        float damage = type.getAttackDamage() * (enraged ? 1.8f : 1.0f);
        targetPlayer.damage(damage);
        float dx = targetPlayer.getX() - x, dz = targetPlayer.getZ() - z;
        float dist = Math.max(1f, (float) Math.sqrt(dx*dx + dz*dz));
        targetPlayer.setVelX(targetPlayer.getVelX() + (dx/dist) * type.getKnockback());
        targetPlayer.setVelZ(targetPlayer.getVelZ() + (dz/dist) * type.getKnockback());
        targetPlayer.setVelY(targetPlayer.getVelY() + 4f);
    }

    @Override
    protected void performSpecialAttack(World world) {
        if (targetPlayer == null) return;
        // Rain of arrows — multiple hits
        int arrowCount = phase == 1 ? 5 : (phase == 2 ? 10 : 15);
        for (int i = 0; i < arrowCount; i++) {
            float delay = i * 0.1f;
            float hitChance = 0.6f;
            if (R.nextFloat() < hitChance) {
                targetPlayer.damage(type.getSpecialDamage() * 0.4f);
            }
        }
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.SKELETON_KING_CROWN, 1));
        drops.add(new ItemStack(ItemType.BONE, 10 + R.nextInt(10)));
        drops.add(new ItemStack(ItemType.DIAMOND, 5 + R.nextInt(5)));
        drops.add(new ItemStack(ItemType.TITANIUM_INGOT, 3 + R.nextInt(5)));
        if (R.nextFloat() < 0.5f) drops.add(new ItemStack(ItemType.SNIPER_RIFLE, 1));
        return drops;
    }
}

// ======== DRAGON LORD ========
class DragonLord extends Boss {
    private static final Random R = new Random();
    private float fireBreathAngle = 0;
    private boolean flying = true;

    public DragonLord(float x, float y, float z) {
        super(x, y, z + 5, BossType.DRAGON_LORD);
    }

    @Override
    protected void updateBossAI(float deltaTime, World world) {
        if (flying) {
            float targetY = world.getSurfaceHeight((int)x, (int)z) + 12f;
            y += (targetY - y) * 0.03f;
        }
        if (phase == 3 && flying) {
            flying = false;
        }
        fireBreathAngle += deltaTime * 60f;
        if (fireBreathAngle > 360f) fireBreathAngle -= 360f;
    }

    @Override
    protected void performMainAttack() {
        if (targetPlayer == null) return;
        float damage = type.getAttackDamage() * (enraged ? 2.0f : 1.0f);
        targetPlayer.damage(damage);
        targetPlayer.setVelY(targetPlayer.getVelY() + 6f);
    }

    @Override
    protected void performSpecialAttack(World world) {
        if (targetPlayer == null) return;
        // Dragon breath cone attack
        float dist = distanceTo(this, targetPlayer);
        if (dist < 20f) {
            float fireCount = phase == 1 ? 3 : (phase == 2 ? 6 : 10);
            for (int i = 0; i < fireCount; i++) {
                if (R.nextFloat() < 0.7f) {
                    targetPlayer.damage(type.getSpecialDamage() / fireCount);
                }
            }
        }
        world.getParticleSystem().spawnExplosionParticles(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ());

        // Phase 3: shockwave
        if (phase == 3) {
            float shockRadius = 10f;
            if (dist < shockRadius) {
                targetPlayer.damage(type.getSpecialDamage() * 0.5f);
                float dx = targetPlayer.getX() - x, dz = targetPlayer.getZ() - z;
                float d = Math.max(1f, (float) Math.sqrt(dx*dx + dz*dz));
                targetPlayer.setVelX(targetPlayer.getVelX() + (dx/d) * 8f);
                targetPlayer.setVelY(6f);
                targetPlayer.setVelZ(targetPlayer.getVelZ() + (dz/d) * 8f);
            }
        }
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.DRAGON_SCALE, 5 + R.nextInt(8)));
        drops.add(new ItemStack(ItemType.DRAGON_EGG, 1));
        drops.add(new ItemStack(ItemType.ADAMANTITE_INGOT, 4 + R.nextInt(6)));
        drops.add(new ItemStack(ItemType.DIAMOND, 8 + R.nextInt(8)));
        if (R.nextFloat() < 0.4f) drops.add(new ItemStack(ItemType.RPG, 1));
        if (R.nextFloat() < 0.3f) drops.add(new ItemStack(ItemType.DRAGON_LORD_ARMOR, 1));
        return drops;
    }
}

// ======== CRYSTAL GOLEM ========
class CrystalGolemBoss extends Boss {
    private static final Random R = new Random();
    private float crystalShieldTimer = 0;
    private boolean shieldActive = false;

    public CrystalGolemBoss(float x, float y, float z) {
        super(x, y, z, BossType.CRYSTAL_GOLEM);
    }

    @Override
    protected void updateBossAI(float deltaTime, World world) {
        crystalShieldTimer -= deltaTime;
        if (crystalShieldTimer <= 0 && !shieldActive) {
            shieldActive = true;
            crystalShieldTimer = 8f;
        } else if (shieldActive && crystalShieldTimer <= 6f) {
            shieldActive = false;
        }

        // Summon crystal minions in phase 2+
        if (phase >= 2 && phaseTimer > 20f) {
            phaseTimer = 0;
            for (int i = 0; i < 3; i++) {
                world.getMobs().add(new CrystalGolemMinion(
                        x + R.nextFloat() * 8 - 4, y, z + R.nextFloat() * 8 - 4));
            }
        }
    }

    @Override
    public void damage(float amount) {
        if (shieldActive) amount *= 0.1f;
        super.damage(amount);
    }

    @Override
    protected void performMainAttack() {
        if (targetPlayer == null) return;
        targetPlayer.damage(type.getAttackDamage() * (enraged ? 1.5f : 1.0f));
        world_ref.getParticleSystem().spawnCrystalParticles(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ());
    }
    private World world_ref;

    @Override
    protected void performSpecialAttack(World world) {
        this.world_ref = world;
        if (targetPlayer == null) return;
        // Crystal spike barrage
        int spikeCount = phase * 4;
        for (int i = 0; i < spikeCount; i++) {
            if (R.nextFloat() < 0.65f) {
                targetPlayer.damage(type.getSpecialDamage() / spikeCount);
            }
        }
        world.getParticleSystem().spawnCrystalParticles(x, y, z);
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.CRYSTAL_ORE_BLOCK, 5 + R.nextInt(10)));
        drops.add(new ItemStack(ItemType.CRYSTAL_SHARD, 15 + R.nextInt(20)));
        drops.add(new ItemStack(ItemType.CRYSTAL_HEART, 1));
        drops.add(new ItemStack(ItemType.ADAMANTITE_INGOT, 3 + R.nextInt(4)));
        if (R.nextFloat() < 0.35f) drops.add(new ItemStack(ItemType.CRYSTAL_ARMOR_SET, 1));
        return drops;
    }
}

// ======== VOID WALKER ========
class VoidWalkerBoss extends Boss {
    private static final Random R = new Random();
    private float teleportTimer = 0;
    private float voidRiftTimer = 0;

    public VoidWalkerBoss(float x, float y, float z) {
        super(x, y, z, BossType.VOID_WALKER);
    }

    @Override
    protected void updateBossAI(float deltaTime, World world) {
        teleportTimer -= deltaTime;
        voidRiftTimer -= deltaTime;

        // Phase 2: teleport frequently
        if (phase >= 2 && teleportTimer <= 0 && targetPlayer != null) {
            float teleportDist = 5f + R.nextFloat() * 10f;
            float angle = R.nextFloat() * 360f;
            x = targetPlayer.getX() + (float) Math.cos(Math.toRadians(angle)) * teleportDist;
            z = targetPlayer.getZ() + (float) Math.sin(Math.toRadians(angle)) * teleportDist;
            teleportTimer = phase == 2 ? 4f : 2f;
        }
    }

    @Override
    protected void performMainAttack() {
        if (targetPlayer == null) return;
        float damage = type.getAttackDamage() * (enraged ? 2.0f : 1.0f);
        targetPlayer.damage(damage);
    }

    @Override
    protected void performSpecialAttack(World world) {
        if (targetPlayer == null) return;
        if (voidRiftTimer <= 0) {
            // Void rift — massive damage
            float rifts = phase;
            for (int i = 0; i < rifts; i++) {
                targetPlayer.damage(type.getSpecialDamage() * 0.5f);
            }
            world.getParticleSystem().spawnExplosionParticles(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ());
            voidRiftTimer = 6f;
        }
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.VOID_ESSENCE, 10 + R.nextInt(10)));
        drops.add(new ItemStack(ItemType.VOID_ORE_BLOCK, 3 + R.nextInt(5)));
        drops.add(new ItemStack(ItemType.VOID_ARMOR_SET, 1));
        drops.add(new ItemStack(ItemType.VOID_BLADE, 1));
        drops.add(new ItemStack(ItemType.DIAMOND, 10 + R.nextInt(10)));
        return drops;
    }
}

// ======== LAVA TITAN ========
class LavaTitan extends Boss {
    private static final Random R = new Random();
    private float eruptionTimer = 0;

    public LavaTitan(float x, float y, float z) {
        super(x, y, z, BossType.LAVA_TITAN);
    }

    @Override
    protected void updateBossAI(float deltaTime, World world) {
        eruptionTimer -= deltaTime;
    }

    @Override
    public void damage(float amount) {
        // Immune to fire damage; weak to water
        super.damage(amount * 0.8f);
    }

    @Override
    protected void performMainAttack() {
        if (targetPlayer == null) return;
        targetPlayer.damage(type.getAttackDamage() * (enraged ? 1.7f : 1.0f));
    }

    @Override
    protected void performSpecialAttack(World world) {
        if (targetPlayer == null || eruptionTimer > 0) return;
        // Lava eruption around the boss
        float dist = distanceTo(this, targetPlayer);
        if (dist < 15f) {
            targetPlayer.damage(type.getSpecialDamage() * (1f - dist / 15f));
        }
        world.getParticleSystem().spawnExplosionParticles(x, y, z);
        eruptionTimer = 8f;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.LAVA_CORE, 3 + R.nextInt(5)));
        drops.add(new ItemStack(ItemType.NETHERITE_INGOT, 3 + R.nextInt(5)));
        drops.add(new ItemStack(ItemType.LAVA_TITAN_ARMOR, 1));
        if (R.nextFloat() < 0.3f) drops.add(new ItemStack(ItemType.MINIGUN, 1));
        return drops;
    }
}

// ======== ICE QUEEN ========
class IceQueen extends Boss {
    private static final Random R = new Random();
    private float blizzardTimer = 0;
    private float iceWallTimer = 0;

    public IceQueen(float x, float y, float z) {
        super(x, y, z, BossType.ICE_QUEEN);
    }

    @Override
    protected void updateBossAI(float deltaTime, World world) {
        blizzardTimer -= deltaTime;
        iceWallTimer -= deltaTime;
    }

    @Override
    protected void performMainAttack() {
        if (targetPlayer == null) return;
        targetPlayer.damage(type.getAttackDamage());
        targetPlayer.setVelX(targetPlayer.getVelX() * 0.4f);
        targetPlayer.setVelZ(targetPlayer.getVelZ() * 0.4f);
    }

    @Override
    protected void performSpecialAttack(World world) {
        if (targetPlayer == null) return;
        if (blizzardTimer <= 0) {
            for (int i = 0; i < 8; i++) {
                if (R.nextFloat() < 0.7f) targetPlayer.damage(type.getSpecialDamage() / 8f);
            }
            targetPlayer.setVelX(targetPlayer.getVelX() * 0.2f);
            targetPlayer.setVelZ(targetPlayer.getVelZ() * 0.2f);
            world.getParticleSystem().spawnCrystalParticles(x, y, z);
            blizzardTimer = 6f;
        }
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.ICE_CROWN, 1));
        drops.add(new ItemStack(ItemType.ICE_SHARD, 20 + R.nextInt(20)));
        drops.add(new ItemStack(ItemType.TUNGSTEN_INGOT, 5 + R.nextInt(5)));
        if (R.nextFloat() < 0.35f) drops.add(new ItemStack(ItemType.SHOTGUN, 1));
        return drops;
    }
}

// ======== SHADOW EMPEROR ========
class ShadowEmperor extends Boss {
    private static final Random R = new Random();
    private float darkPortalTimer = 0;

    public ShadowEmperor(float x, float y, float z) {
        super(x, y, z, BossType.SHADOW_EMPEROR);
    }

    @Override
    protected void updateBossAI(float deltaTime, World world) {
        darkPortalTimer -= deltaTime;
        // Phase 3: becomes semi-invisible (represented mechanically)
        if (phase == 3) detectionRange = 100f;
    }
    private float detectionRange = 60f;

    @Override
    protected void performMainAttack() {
        if (targetPlayer == null) return;
        float damage = type.getAttackDamage() * (enraged ? 2.5f : 1.0f);
        targetPlayer.damage(damage);
    }

    @Override
    protected void performSpecialAttack(World world) {
        if (targetPlayer == null) return;
        if (darkPortalTimer <= 0) {
            // Dark portal — teleport player randomly
            targetPlayer.setX(targetPlayer.getX() + R.nextFloat() * 20 - 10);
            targetPlayer.setZ(targetPlayer.getZ() + R.nextFloat() * 20 - 10);
            targetPlayer.damage(type.getSpecialDamage() * 0.3f);
            // Summon shadow minions
            for (int i = 0; i < phase * 2; i++) {
                world.getMobs().add(new com.minecraft2.android.entity.mob.VoidStalker(
                        x + R.nextFloat() * 12 - 6, y, z + R.nextFloat() * 12 - 6));
            }
            darkPortalTimer = 10f;
        }
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ItemType.SHADOW_CROWN, 1));
        drops.add(new ItemStack(ItemType.VOID_ESSENCE, 15 + R.nextInt(15)));
        drops.add(new ItemStack(ItemType.SHADOW_ORE_BLOCK, 5 + R.nextInt(8)));
        drops.add(new ItemStack(ItemType.VOID_ARMOR_SET, 1));
        drops.add(new ItemStack(ItemType.SHADOW_BLADE, 1));
        if (R.nextFloat() < 0.25f) drops.add(new ItemStack(ItemType.VOID_GUN, 1));
        return drops;
    }
}
