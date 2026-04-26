package com.minecraft2.android.weapon;

import com.minecraft2.android.item.ItemType;

public enum GunType {

    PISTOL("Pistol", ItemType.PISTOL, ItemType.PISTOL_AMMO,
            8f, 0.4f, 15, 15, 50f, 1, 1, false, 2.0f, 0.8f),

    SMG("SMG", ItemType.SMG, ItemType.PISTOL_AMMO,
            6f, 0.1f, 30, 30, 40f, 1, 3, false, 3.0f, 0.4f),

    RIFLE("Rifle", ItemType.RIFLE, ItemType.RIFLE_AMMO,
            14f, 0.5f, 20, 20, 80f, 1, 1, false, 1.5f, 1.5f),

    SHOTGUN("Shotgun", ItemType.SHOTGUN, ItemType.SHOTGUN_SHELLS,
            20f, 0.8f, 6, 6, 20f, 8, 1, true, 1.0f, 2.0f),

    SNIPER_RIFLE("Sniper Rifle", ItemType.SNIPER_RIFLE, ItemType.SNIPER_AMMO,
            30f, 1.2f, 10, 10, 150f, 1, 1, false, 0.5f, 4.0f),

    RPG("RPG", ItemType.RPG, ItemType.ROCKET,
            60f, 3.0f, 3, 3, 60f, 1, 1, true, 0.5f, 6.0f),

    MINIGUN("Minigun", ItemType.MINIGUN, ItemType.MINIGUN_BELT,
            5f, 0.08f, 200, 200, 60f, 1, 1, false, 4.0f, 0.3f),

    VOID_GUN("Void Gun", ItemType.VOID_GUN, ItemType.VOID_AMMO,
            40f, 0.6f, 20, 20, 100f, 1, 1, false, 1.0f, 3.5f),

    FLAMETHROWER("Flamethrower", ItemType.FLAMETHROWER, ItemType.FUEL_CANISTER,
            3f, 0.05f, 100, 100, 8f, 1, 10, false, 4.0f, 0.3f),

    LASER_RIFLE("Laser Rifle", ItemType.LASER_RIFLE, ItemType.ENERGY_CELL,
            25f, 0.3f, 30, 30, 120f, 1, 1, false, 1.2f, 2.5f);

    private final String displayName;
    private final ItemType itemType;
    private final ItemType ammoType;
    private final float damage;
    private final float fireRate;
    private final int magazineSize;
    private final int currentAmmo;
    private final float range;
    private final int pellets;
    private final int bulletsPerShot;
    private final boolean explosive;
    private final float reloadTime;
    private final float spread;

    GunType(String displayName, ItemType itemType, ItemType ammoType,
            float damage, float fireRate, int magazineSize, int currentAmmo,
            float range, int pellets, int bulletsPerShot, boolean explosive,
            float reloadTime, float spread) {
        this.displayName = displayName;
        this.itemType = itemType;
        this.ammoType = ammoType;
        this.damage = damage;
        this.fireRate = fireRate;
        this.magazineSize = magazineSize;
        this.currentAmmo = currentAmmo;
        this.range = range;
        this.pellets = pellets;
        this.bulletsPerShot = bulletsPerShot;
        this.explosive = explosive;
        this.reloadTime = reloadTime;
        this.spread = spread;
    }

    public String getDisplayName() { return displayName; }
    public ItemType getItemType() { return itemType; }
    public ItemType getAmmoType() { return ammoType; }
    public float getDamage() { return damage; }
    public float getFireRate() { return fireRate; }
    public int getMagazineSize() { return magazineSize; }
    public float getRange() { return range; }
    public int getPellets() { return pellets; }
    public int getBulletsPerShot() { return bulletsPerShot; }
    public boolean isExplosive() { return explosive; }
    public float getReloadTime() { return reloadTime; }
    public float getSpread() { return spread; }
}
