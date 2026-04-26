package com.minecraft2.android.item;

public enum ItemType {

    // === BLOCKS (as items) ===
    OAK_PLANKS("Oak Planks", 64, false, false, false, false, 0, 0, 0, 0),
    CRAFTING_TABLE("Crafting Table", 64, false, false, false, false, 0, 0, 0, 0),
    TORCH("Torch", 64, false, false, false, false, 0, 0, 0, 0),
    STONE("Stone", 64, false, false, false, false, 0, 0, 0, 0),
    DIRT("Dirt", 64, false, false, false, false, 0, 0, 0, 0),
    SAND("Sand", 64, false, false, false, false, 0, 0, 0, 0),
    GRAVEL("Gravel", 64, false, false, false, false, 0, 0, 0, 0),
    GLASS("Glass", 64, false, false, false, false, 0, 0, 0, 0),
    OAK_LOG("Oak Log", 64, false, false, false, false, 0, 0, 0, 0),
    CHEST("Chest", 64, false, false, false, false, 0, 0, 0, 0),
    FURNACE("Furnace", 64, false, false, false, false, 0, 0, 0, 0),
    TNT("TNT", 64, false, false, false, false, 0, 0, 0, 0),
    BOOKSHELF("Bookshelf", 64, false, false, false, false, 0, 0, 0, 0),
    CRYSTAL_ORE_BLOCK("Crystal Ore Block", 64, false, false, false, false, 0, 0, 0, 0),
    VOID_ORE_BLOCK("Void Ore Block", 64, false, false, false, false, 0, 0, 0, 0),
    SHADOW_ORE_BLOCK("Shadow Ore Block", 64, false, false, false, false, 0, 0, 0, 0),
    POPPY("Poppy", 64, false, false, false, false, 0, 0, 0, 0),

    // === MATERIALS & ORES ===
    COAL("Coal", 64, false, false, false, false, 0, 0, 0, 0),
    IRON_INGOT("Iron Ingot", 64, false, false, false, false, 0, 0, 0, 0),
    GOLD_INGOT("Gold Ingot", 64, false, false, false, false, 0, 0, 0, 0),
    DIAMOND("Diamond", 64, false, false, false, false, 0, 0, 0, 0),
    REDSTONE("Redstone", 64, false, false, false, false, 0, 0, 0, 0),
    LAPIS("Lapis Lazuli", 64, false, false, false, false, 0, 0, 0, 0),
    EMERALD("Emerald", 64, false, false, false, false, 0, 0, 0, 0),
    NETHERITE_INGOT("Netherite Ingot", 16, false, false, false, false, 0, 0, 0, 0),
    QUARTZ("Quartz", 64, false, false, false, false, 0, 0, 0, 0),

    // === NEW MC2 MATERIALS ===
    TITANIUM_INGOT("Titanium Ingot", 64, false, false, false, false, 0, 0, 0, 0),
    TUNGSTEN_INGOT("Tungsten Ingot", 64, false, false, false, false, 0, 0, 0, 0),
    ADAMANTITE_INGOT("Adamantite Ingot", 64, false, false, false, false, 0, 0, 0, 0),
    CRYSTAL_SHARD("Crystal Shard", 64, false, false, false, false, 0, 0, 0, 0),
    VOID_ESSENCE("Void Essence", 16, false, false, false, false, 0, 0, 0, 0),
    STARLIGHT_DUST("Starlight Dust", 64, false, false, false, false, 0, 0, 0, 0),
    SHADOW_POWDER("Shadow Powder", 64, false, false, false, false, 0, 0, 0, 0),
    ICE_SHARD("Ice Shard", 64, false, false, false, false, 0, 0, 0, 0),
    LAVA_CORE("Lava Core", 16, false, false, false, false, 0, 0, 0, 0),
    DRAGON_SCALE("Dragon Scale", 16, false, false, false, false, 0, 0, 0, 0),
    CRYSTAL_SCALE("Crystal Scale", 16, false, false, false, false, 0, 0, 0, 0),
    ICE_DRAGON_WING("Ice Dragon Wing", 4, false, false, false, false, 0, 0, 0, 0),
    CRYSTAL_WEB("Crystal Web", 16, false, false, false, false, 0, 0, 0, 0),
    CRYSTAL_HEART("Crystal Heart", 1, false, false, false, false, 0, 0, 0, 0),

    // === TOOLS (MC1) ===
    WOOD_PICKAXE("Wooden Pickaxe", 1, true, false, false, false, 0, 0, 1, 60),
    STONE_PICKAXE("Stone Pickaxe", 1, true, false, false, false, 0, 0, 1, 132),
    IRON_PICKAXE("Iron Pickaxe", 1, true, false, false, false, 0, 0, 1, 251),
    GOLD_PICKAXE("Golden Pickaxe", 1, true, false, false, false, 0, 0, 1, 33),
    DIAMOND_PICKAXE("Diamond Pickaxe", 1, true, false, false, false, 0, 0, 1, 1562),
    NETHERITE_PICKAXE("Netherite Pickaxe", 1, true, false, false, false, 0, 0, 1, 2031),
    TITANIUM_PICKAXE("Titanium Pickaxe", 1, true, false, false, false, 0, 0, 1, 2500),
    TUNGSTEN_PICKAXE("Tungsten Pickaxe", 1, true, false, false, false, 0, 0, 1, 3200),
    ADAMANTITE_PICKAXE("Adamantite Pickaxe", 1, true, false, false, false, 0, 0, 1, 4096),
    VOID_PICKAXE("Void Pickaxe", 1, true, false, false, false, 0, 0, 1, 8192),

    WOOD_AXE("Wooden Axe", 1, true, false, false, false, 0, 0, 1, 60),
    STONE_AXE("Stone Axe", 1, true, false, false, false, 0, 0, 1, 132),
    IRON_AXE("Iron Axe", 1, true, false, false, false, 0, 0, 1, 251),
    DIAMOND_AXE("Diamond Axe", 1, true, false, false, false, 0, 0, 1, 1562),

    WOOD_SHOVEL("Wooden Shovel", 1, true, false, false, false, 0, 0, 1, 60),
    IRON_SHOVEL("Iron Shovel", 1, true, false, false, false, 0, 0, 1, 251),
    DIAMOND_SHOVEL("Diamond Shovel", 1, true, false, false, false, 0, 0, 1, 1562),

    FLINT_AND_STEEL("Flint and Steel", 1, true, false, false, false, 0, 0, 0, 64),
    SHEARS("Shears", 1, true, false, false, false, 0, 0, 0, 238),
    FISHING_ROD("Fishing Rod", 1, true, false, false, false, 0, 0, 0, 64),
    BOW("Bow", 1, true, false, false, false, 0, 0, 0, 385),
    CROSSBOW("Crossbow", 1, true, false, false, false, 0, 0, 0, 465),
    TRIDENT("Trident", 1, true, false, false, false, 9, 0, 0, 250),

    // === SWORDS (MC1) ===
    WOOD_SWORD("Wooden Sword", 1, true, false, false, false, 4, 0, 0, 60),
    STONE_SWORD("Stone Sword", 1, true, false, false, false, 5, 0, 0, 132),
    IRON_SWORD("Iron Sword", 1, true, false, false, false, 6, 0, 0, 251),
    GOLD_SWORD("Golden Sword", 1, true, false, false, false, 4, 0, 0, 33),
    DIAMOND_SWORD("Diamond Sword", 1, true, false, false, false, 7, 0, 0, 1562),
    NETHERITE_SWORD("Netherite Sword", 1, true, false, false, false, 8, 0, 0, 2031),

    // === NEW MC2 SWORDS ===
    TITANIUM_SWORD("Titanium Sword", 1, true, false, false, false, 10, 0, 0, 2500),
    TUNGSTEN_SWORD("Tungsten Sword", 1, true, false, false, false, 12, 0, 0, 3200),
    ADAMANTITE_SWORD("Adamantite Sword", 1, true, false, false, false, 15, 0, 0, 4096),
    CRYSTAL_BLADE("Crystal Blade", 1, true, false, false, false, 13, 0, 0, 3500),
    VOID_BLADE("Void Blade", 1, true, false, false, false, 20, 0, 0, 5000),
    SHADOW_BLADE("Shadow Blade", 1, true, false, false, false, 18, 0, 0, 4800),

    // === GUNS (NEW MC2) ===
    PISTOL("Pistol", 1, true, false, true, false, 8, 0, 0, 500),
    SMG("SMG", 1, true, false, true, false, 6, 0, 0, 500),
    RIFLE("Rifle", 1, true, false, true, false, 14, 0, 0, 600),
    SHOTGUN("Shotgun", 1, true, false, true, false, 20, 0, 0, 600),
    SNIPER_RIFLE("Sniper Rifle", 1, true, false, true, false, 30, 0, 0, 700),
    RPG("RPG", 1, true, false, true, false, 60, 0, 0, 200),
    MINIGUN("Minigun", 1, true, false, true, false, 5, 0, 0, 400),
    VOID_GUN("Void Gun", 1, true, false, true, false, 40, 0, 0, 300),
    FLAMETHROWER("Flamethrower", 1, true, false, true, false, 3, 0, 0, 400),
    LASER_RIFLE("Laser Rifle", 1, true, false, true, false, 25, 0, 0, 600),

    // === AMMO ===
    PISTOL_AMMO("Pistol Ammo", 64, false, false, false, false, 0, 0, 0, 0),
    RIFLE_AMMO("Rifle Ammo", 64, false, false, false, false, 0, 0, 0, 0),
    SHOTGUN_SHELLS("Shotgun Shells", 32, false, false, false, false, 0, 0, 0, 0),
    SNIPER_AMMO("Sniper Ammo", 32, false, false, false, false, 0, 0, 0, 0),
    ROCKET("Rocket", 8, false, false, false, false, 0, 0, 0, 0),
    MINIGUN_BELT("Minigun Belt", 200, false, false, false, false, 0, 0, 0, 0),
    VOID_AMMO("Void Ammo", 16, false, false, false, false, 0, 0, 0, 0),
    ARROW("Arrow", 64, false, false, false, false, 0, 0, 0, 0),
    FUEL_CANISTER("Fuel Canister", 8, false, false, false, false, 0, 0, 0, 0),
    ENERGY_CELL("Energy Cell", 32, false, false, false, false, 0, 0, 0, 0),

    // === ARMOR (MC1) ===
    LEATHER_ARMOR("Leather Armor", 1, false, true, false, false, 0, 1, 0, 55),
    CHAIN_ARMOR("Chainmail Armor", 1, false, true, false, false, 0, 2, 0, 0),
    IRON_HELMET("Iron Helmet", 1, false, true, false, false, 0, 2, 0, 165),
    IRON_CHESTPLATE("Iron Chestplate", 1, false, true, false, false, 0, 6, 0, 240),
    IRON_LEGGINGS("Iron Leggings", 1, false, true, false, false, 0, 5, 0, 225),
    IRON_BOOTS("Iron Boots", 1, false, true, false, false, 0, 2, 0, 195),
    DIAMOND_HELMET("Diamond Helmet", 1, false, true, false, false, 0, 3, 0, 363),
    DIAMOND_CHESTPLATE("Diamond Chestplate", 1, false, true, false, false, 0, 8, 0, 528),
    DIAMOND_LEGGINGS("Diamond Leggings", 1, false, true, false, false, 0, 6, 0, 495),
    DIAMOND_BOOTS("Diamond Boots", 1, false, true, false, false, 0, 3, 0, 429),
    NETHERITE_HELMET("Netherite Helmet", 1, false, true, false, false, 0, 3, 0, 407),
    NETHERITE_CHESTPLATE("Netherite Chestplate", 1, false, true, false, false, 0, 8, 0, 592),
    NETHERITE_LEGGINGS("Netherite Leggings", 1, false, true, false, false, 0, 6, 0, 555),
    NETHERITE_BOOTS("Netherite Boots", 1, false, true, false, false, 0, 3, 0, 481),

    // === NEW MC2 ARMOR SETS ===
    TITANIUM_ARMOR_SET("Titanium Armor Set", 1, false, true, false, false, 0, 10, 0, 1500),
    TUNGSTEN_ARMOR_SET("Tungsten Armor Set", 1, false, true, false, false, 0, 13, 0, 2000),
    ADAMANTITE_ARMOR_SET("Adamantite Armor Set", 1, false, true, false, false, 0, 16, 0, 3000),
    CRYSTAL_ARMOR_SET("Crystal Armor Set", 1, false, true, false, false, 0, 12, 0, 2500),
    VOID_ARMOR_SET("Void Armor Set", 1, false, true, false, false, 0, 20, 0, 4000),
    SHADOW_ARMOR_SET("Shadow Armor Set", 1, false, true, false, false, 0, 15, 0, 3500),
    DRAGON_LORD_ARMOR("Dragon Lord Armor", 1, false, true, false, false, 0, 25, 0, 5000),
    LAVA_TITAN_ARMOR("Lava Titan Armor", 1, false, true, false, false, 0, 22, 0, 4500),

    // === BOSS DROPS ===
    SKELETON_KING_CROWN("Skeleton King Crown", 1, false, true, false, false, 0, 8, 0, 9999),
    DRAGON_EGG("Dragon Egg", 1, false, false, false, false, 0, 0, 0, 0),
    ICE_CROWN("Ice Crown", 1, false, true, false, false, 0, 6, 0, 9999),
    SHADOW_CROWN("Shadow Crown", 1, false, true, false, false, 0, 10, 0, 9999),

    // === FOOD ===
    APPLE("Apple", 64, false, false, false, true, 0, 0, 4, 2),
    BREAD("Bread", 64, false, false, false, true, 0, 0, 5, 3),
    BEEF("Raw Beef", 64, false, false, false, true, 0, 0, 3, 2),
    COOKED_BEEF("Cooked Beef", 64, false, false, false, true, 0, 0, 8, 5),
    PORK("Raw Pork", 64, false, false, false, true, 0, 0, 3, 2),
    COOKED_PORK("Cooked Pork", 64, false, false, false, true, 0, 0, 8, 5),
    MUTTON("Raw Mutton", 64, false, false, false, true, 0, 0, 2, 1),
    COOKED_MUTTON("Cooked Mutton", 64, false, false, false, true, 0, 0, 6, 4),
    CHICKEN_MEAT("Raw Chicken", 64, false, false, false, true, 0, 0, 2, 1),
    COOKED_CHICKEN("Cooked Chicken", 64, false, false, false, true, 0, 0, 6, 4),
    COD("Raw Cod", 64, false, false, false, true, 0, 0, 2, 1),
    COOKED_COD("Cooked Cod", 64, false, false, false, true, 0, 0, 5, 3),
    CARROT("Carrot", 64, false, false, false, true, 0, 0, 3, 2),
    GOLDEN_CARROT("Golden Carrot", 64, false, false, false, true, 0, 0, 6, 14),
    MELON("Melon", 64, false, false, false, true, 0, 0, 2, 1),
    PUMPKIN_PIE("Pumpkin Pie", 64, false, false, false, true, 0, 0, 8, 5),
    MUSHROOM_STEW("Mushroom Stew", 1, false, false, false, true, 0, 0, 6, 4),
    RABBIT_MEAT("Rabbit", 64, false, false, false, true, 0, 0, 3, 2),
    GOLDEN_APPLE("Golden Apple", 64, false, false, false, true, 0, 0, 4, 14),
    ENCHANTED_GOLDEN_APPLE("Enchanted Golden Apple", 64, false, false, false, true, 0, 0, 4, 14),
    ROTTEN_FLESH("Rotten Flesh", 64, false, false, false, true, 0, 0, 4, 1),

    // === MISC ITEMS ===
    STRING("String", 64, false, false, false, false, 0, 0, 0, 0),
    FEATHER("Feather", 64, false, false, false, false, 0, 0, 0, 0),
    BONE("Bone", 64, false, false, false, false, 0, 0, 0, 0),
    GUNPOWDER("Gunpowder", 64, false, false, false, false, 0, 0, 0, 0),
    SPIDER_EYE("Spider Eye", 64, false, false, false, false, 0, 0, 0, 0),
    BLAZE_ROD("Blaze Rod", 64, false, false, false, false, 0, 0, 0, 0),
    BLAZE_POWDER("Blaze Powder", 64, false, false, false, false, 0, 0, 0, 0),
    GHAST_TEAR("Ghast Tear", 64, false, false, false, false, 0, 0, 0, 0),
    MAGMA_CREAM("Magma Cream", 64, false, false, false, false, 0, 0, 0, 0),
    SLIMEBALL("Slimeball", 64, false, false, false, false, 0, 0, 0, 0),
    INK_SAC("Ink Sac", 64, false, false, false, false, 0, 0, 0, 0),
    PHANTOM_MEMBRANE("Phantom Membrane", 64, false, false, false, false, 0, 0, 0, 0),
    PRISMARINE_SHARD("Prismarine Shard", 64, false, false, false, false, 0, 0, 0, 0),
    GLOWSTONE_DUST("Glowstone Dust", 64, false, false, false, false, 0, 0, 0, 0),
    LEATHER("Leather", 64, false, false, false, false, 0, 0, 0, 0),
    WOOL("Wool", 64, false, false, false, false, 0, 0, 0, 0),
    RABBIT_FOOT("Rabbit's Foot", 64, false, false, false, false, 0, 0, 0, 0),
    MUSIC_DISC("Music Disc", 1, false, false, false, false, 0, 0, 0, 0),

    // === FURNITURE ITEMS ===
    CHAIR("Chair", 64, false, false, false, false, 0, 0, 0, 0),
    TABLE("Table", 64, false, false, false, false, 0, 0, 0, 0),
    BED("Bed", 1, false, false, false, false, 0, 0, 0, 0),
    SOFA("Sofa", 4, false, false, false, false, 0, 0, 0, 0),
    LAMP("Lamp", 64, false, false, false, false, 0, 0, 0, 0),
    SHELF("Shelf", 64, false, false, false, false, 0, 0, 0, 0),
    WARDROBE("Wardrobe", 1, false, false, false, false, 0, 0, 0, 0),
    FRIDGE("Fridge", 1, false, false, false, false, 0, 0, 0, 0),
    STOVE("Stove", 1, false, false, false, false, 0, 0, 0, 0),
    SINK("Sink", 4, false, false, false, false, 0, 0, 0, 0),
    TOILET("Toilet", 1, false, false, false, false, 0, 0, 0, 0),
    BATHTUB("Bathtub", 1, false, false, false, false, 0, 0, 0, 0),
    PAINTING("Painting", 64, false, false, false, false, 0, 0, 0, 0),
    FLOWER_POT("Flower Pot", 64, false, false, false, false, 0, 0, 0, 0),
    CURTAIN("Curtain", 64, false, false, false, false, 0, 0, 0, 0),
    RUG("Rug", 64, false, false, false, false, 0, 0, 0, 0),
    DESK("Desk", 4, false, false, false, false, 0, 0, 0, 0),
    COMPUTER("Computer", 1, false, false, false, false, 0, 0, 0, 0),
    TELEVISION("Television", 1, false, false, false, false, 0, 0, 0, 0),
    CHANDELIER("Chandelier", 4, false, false, false, false, 0, 0, 0, 0),
    ARMCHAIR("Armchair", 4, false, false, false, false, 0, 0, 0, 0),
    BOOKCASE("Bookcase", 16, false, false, false, false, 0, 0, 0, 0),
    FIREPLACE("Fireplace", 4, false, false, false, false, 0, 0, 0, 0);

    private final String displayName;
    private final int maxStack;
    private final boolean isTool;
    private final boolean isArmor;
    private final boolean isGun;
    private final boolean isFood;
    private final int meleeDamage;
    private final int armorValue;
    private final int foodValue;
    private final int durability;

    ItemType(String displayName, int maxStack, boolean isTool, boolean isArmor,
             boolean isGun, boolean isFood, int meleeDamage, int armorValue,
             int foodValue, int durability) {
        this.displayName = displayName;
        this.maxStack = maxStack;
        this.isTool = isTool;
        this.isArmor = isArmor;
        this.isGun = isGun;
        this.isFood = isFood;
        this.meleeDamage = meleeDamage;
        this.armorValue = armorValue;
        this.foodValue = foodValue;
        this.durability = durability;
    }

    public String getDisplayName() { return displayName; }
    public int getMaxStack() { return maxStack; }
    public boolean isTool() { return isTool; }
    public boolean isArmor() { return isArmor; }
    public boolean isGun() { return isGun; }
    public boolean isFood() { return isFood; }
    public int getMeleeDamage() { return meleeDamage; }
    public int getArmorValue() { return armorValue; }
    public int getFoodValue() { return foodValue; }
    public int getSaturationValue() { return Math.max(1, foodValue / 2); }
    public int getDurability() { return durability; }
    public boolean isWeapon() { return isTool && meleeDamage > 0; }
}
