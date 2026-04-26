package com.minecraft2.android.block;

import android.graphics.Color;

public enum BlockType {

    // === AIR ===
    AIR(0, "Air", Color.TRANSPARENT, 0, false, false),

    // === MINECRAFT 1 NATURAL BLOCKS ===
    GRASS(1, "Grass", Color.rgb(86, 168, 54), 1.0f, true, false),
    DIRT(2, "Dirt", Color.rgb(134, 96, 67), 0.8f, true, false),
    STONE(3, "Stone", Color.rgb(128, 128, 128), 4.0f, true, false),
    COBBLESTONE(4, "Cobblestone", Color.rgb(100, 100, 100), 3.5f, true, false),
    SAND(5, "Sand", Color.rgb(218, 210, 158), 0.6f, true, false),
    GRAVEL(6, "Gravel", Color.rgb(150, 140, 130), 0.9f, true, false),
    BEDROCK(7, "Bedrock", Color.rgb(50, 50, 50), -1f, true, false),
    WATER(8, "Water", Color.argb(180, 30, 100, 200), 100f, false, true),
    LAVA(9, "Lava", Color.rgb(207, 87, 20), 100f, false, true),
    SNOW(10, "Snow", Color.rgb(240, 248, 255), 0.3f, true, false),
    ICE(11, "Ice", Color.argb(200, 140, 200, 255), 0.5f, true, false),
    CLAY(12, "Clay", Color.rgb(162, 172, 182), 1.0f, true, false),
    SPONGE(13, "Sponge", Color.rgb(195, 190, 65), 1.5f, true, false),

    // === LOGS & LEAVES ===
    OAK_LOG(14, "Oak Log", Color.rgb(100, 73, 45), 3.0f, true, false),
    BIRCH_LOG(15, "Birch Log", Color.rgb(200, 195, 170), 3.0f, true, false),
    SPRUCE_LOG(16, "Spruce Log", Color.rgb(60, 40, 25), 3.0f, true, false),
    JUNGLE_LOG(17, "Jungle Log", Color.rgb(80, 60, 30), 3.0f, true, false),
    ACACIA_LOG(18, "Acacia Log", Color.rgb(150, 90, 50), 3.0f, true, false),
    DARK_OAK_LOG(19, "Dark Oak Log", Color.rgb(45, 28, 12), 3.0f, true, false),
    OAK_LEAVES(20, "Oak Leaves", Color.argb(220, 60, 120, 40), 0.3f, true, false),
    BIRCH_LEAVES(21, "Birch Leaves", Color.argb(220, 130, 160, 60), 0.3f, true, false),
    SPRUCE_LEAVES(22, "Spruce Leaves", Color.argb(220, 50, 90, 50), 0.3f, true, false),
    JUNGLE_LEAVES(23, "Jungle Leaves", Color.argb(220, 60, 140, 30), 0.3f, true, false),
    ACACIA_LEAVES(24, "Acacia Leaves", Color.argb(220, 100, 140, 40), 0.3f, true, false),

    // === PLANKS ===
    OAK_PLANKS(25, "Oak Planks", Color.rgb(162, 130, 78), 3.0f, true, false),
    BIRCH_PLANKS(26, "Birch Planks", Color.rgb(210, 196, 145), 3.0f, true, false),
    SPRUCE_PLANKS(27, "Spruce Planks", Color.rgb(102, 76, 48), 3.0f, true, false),
    JUNGLE_PLANKS(28, "Jungle Planks", Color.rgb(160, 115, 75), 3.0f, true, false),
    ACACIA_PLANKS(29, "Acacia Planks", Color.rgb(168, 90, 50), 3.0f, true, false),
    DARK_OAK_PLANKS(30, "Dark Oak Planks", Color.rgb(66, 43, 20), 3.0f, true, false),

    // === ORES (MC1) ===
    COAL_ORE(31, "Coal Ore", Color.rgb(60, 60, 60), 5.0f, true, false),
    IRON_ORE(32, "Iron Ore", Color.rgb(136, 110, 90), 5.0f, true, false),
    GOLD_ORE(33, "Gold Ore", Color.rgb(180, 160, 60), 5.0f, true, false),
    DIAMOND_ORE(34, "Diamond Ore", Color.rgb(60, 180, 200), 5.0f, true, false),
    REDSTONE_ORE(35, "Redstone Ore", Color.rgb(160, 30, 30), 5.0f, true, false),
    LAPIS_ORE(36, "Lapis Ore", Color.rgb(50, 80, 170), 5.0f, true, false),
    EMERALD_ORE(37, "Emerald Ore", Color.rgb(50, 190, 100), 5.0f, true, false),
    COPPER_ORE(38, "Copper Ore", Color.rgb(180, 120, 80), 5.0f, true, false),
    NETHERITE_ORE(39, "Netherite Ore", Color.rgb(80, 60, 60), 8.0f, true, false),

    // === NEW ORES (MC2) ===
    TITANIUM_ORE(40, "Titanium Ore", Color.rgb(150, 180, 220), 8.0f, true, false),
    TUNGSTEN_ORE(41, "Tungsten Ore", Color.rgb(80, 100, 120), 9.0f, true, false),
    ADAMANTITE_ORE(42, "Adamantite Ore", Color.rgb(180, 50, 180), 10.0f, true, false),
    CRYSTAL_ORE(43, "Crystal Ore", Color.rgb(200, 240, 255), 7.0f, true, false),
    VOID_ORE(44, "Void Ore", Color.rgb(20, 0, 40), 12.0f, true, false),
    STARLIGHT_ORE(45, "Starlight Ore", Color.rgb(255, 230, 100), 6.0f, true, false),
    SHADOW_ORE(46, "Shadow Ore", Color.rgb(40, 20, 60), 9.5f, true, false),

    // === BUILDING BLOCKS ===
    BRICKS(47, "Bricks", Color.rgb(152, 82, 60), 4.0f, true, false),
    STONE_BRICKS(48, "Stone Bricks", Color.rgb(100, 100, 100), 4.5f, true, false),
    MOSSY_STONE_BRICKS(49, "Mossy Stone Bricks", Color.rgb(80, 110, 80), 4.5f, true, false),
    CHISELED_STONE_BRICKS(50, "Chiseled Stone Bricks", Color.rgb(110, 110, 110), 4.5f, true, false),
    GLASS(51, "Glass", Color.argb(120, 200, 230, 255), 0.5f, true, true),
    GLASS_PANE(52, "Glass Pane", Color.argb(100, 200, 230, 255), 0.5f, true, true),
    OBSIDIAN(53, "Obsidian", Color.rgb(20, 10, 30), 50.0f, true, false),
    NETHER_BRICKS(54, "Nether Bricks", Color.rgb(68, 28, 28), 4.0f, true, false),
    QUARTZ_BLOCK(55, "Quartz Block", Color.rgb(235, 225, 215), 4.0f, true, false),
    PURPUR_BLOCK(56, "Purpur Block", Color.rgb(170, 100, 170), 4.0f, true, false),
    END_STONE(57, "End Stone", Color.rgb(220, 220, 160), 4.5f, true, false),
    GLOWSTONE(58, "Glowstone", Color.rgb(220, 190, 100), 0.5f, true, false),
    SEA_LANTERN(59, "Sea Lantern", Color.rgb(160, 200, 210), 0.5f, true, false),
    PRISMARINE(60, "Prismarine", Color.rgb(90, 170, 150), 4.5f, true, false),
    NETHERRACK(61, "Netherrack", Color.rgb(100, 30, 30), 1.0f, true, false),
    SOUL_SAND(62, "Soul Sand", Color.rgb(80, 60, 45), 2.0f, true, false),
    MAGMA(63, "Magma Block", Color.rgb(160, 70, 20), 1.5f, true, false),
    BASALT(64, "Basalt", Color.rgb(70, 70, 80), 4.0f, true, false),
    BLACKSTONE(65, "Blackstone", Color.rgb(35, 30, 40), 6.0f, true, false),
    DEEPSLATE(66, "Deepslate", Color.rgb(60, 65, 70), 6.5f, true, false),

    // === NEW MC2 BLOCKS ===
    CRYSTAL_BLOCK(67, "Crystal Block", Color.argb(200, 180, 240, 255), 5.0f, true, true),
    VOID_STONE(68, "Void Stone", Color.rgb(10, 0, 25), 15.0f, true, false),
    STARLIGHT_GLASS(69, "Starlight Glass", Color.argb(160, 255, 240, 100), 0.5f, true, true),
    TITANIUM_BLOCK(70, "Titanium Block", Color.rgb(160, 195, 230), 8.0f, true, false),
    TUNGSTEN_BLOCK(71, "Tungsten Block", Color.rgb(70, 90, 110), 10.0f, true, false),
    ADAMANTITE_BLOCK(72, "Adamantite Block", Color.rgb(190, 60, 190), 12.0f, true, false),
    LAVA_STONE(73, "Lava Stone", Color.rgb(180, 60, 10), 5.0f, true, false),
    ICE_CRYSTAL(74, "Ice Crystal", Color.argb(220, 180, 230, 255), 2.0f, true, true),
    SHADOW_BLOCK(75, "Shadow Block", Color.rgb(15, 5, 30), 14.0f, true, false),

    // === FUNCTIONAL BLOCKS ===
    CRAFTING_TABLE(76, "Crafting Table", Color.rgb(140, 100, 60), 3.0f, true, false),
    FURNACE(77, "Furnace", Color.rgb(110, 100, 100), 4.0f, true, false),
    CHEST(78, "Chest", Color.rgb(160, 115, 60), 3.0f, true, false),
    ENDER_CHEST(79, "Ender Chest", Color.rgb(20, 70, 90), 22.5f, true, false),
    ANVIL(80, "Anvil", Color.rgb(80, 80, 80), 15.0f, true, false),
    ENCHANTING_TABLE(81, "Enchanting Table", Color.rgb(80, 20, 100), 5.0f, true, false),
    BEACON(82, "Beacon", Color.argb(200, 100, 230, 200), 3.0f, true, true),
    CONDUIT(83, "Conduit", Color.rgb(100, 170, 180), 3.0f, true, false),
    BREWING_STAND(84, "Brewing Stand", Color.rgb(70, 65, 80), 0.5f, true, false),
    CAULDRON(85, "Cauldron", Color.rgb(60, 60, 60), 4.0f, true, false),
    TNT(86, "TNT", Color.rgb(200, 50, 50), 0f, true, false),
    DISPENSER(87, "Dispenser", Color.rgb(100, 95, 95), 4.5f, true, false),
    PISTON(88, "Piston", Color.rgb(130, 120, 100), 1.5f, true, false),
    STICKY_PISTON(89, "Sticky Piston", Color.rgb(100, 130, 90), 1.5f, true, false),
    HOPPER(90, "Hopper", Color.rgb(65, 65, 65), 4.5f, true, false),
    DROPPER(91, "Dropper", Color.rgb(90, 85, 85), 4.5f, true, false),
    JUKEBOX(92, "Jukebox", Color.rgb(100, 60, 35), 3.5f, true, false),
    NOTE_BLOCK(93, "Note Block", Color.rgb(120, 80, 50), 3.0f, true, false),
    BOOKSHELF(94, "Bookshelf", Color.rgb(130, 100, 60), 2.5f, true, false),
    TORCH(95, "Torch", Color.rgb(255, 200, 50), 0f, false, false),
    LANTERN(96, "Lantern", Color.rgb(220, 160, 50), 0f, false, false),
    CAMPFIRE(97, "Campfire", Color.rgb(200, 100, 30), 0f, false, false),

    // === GUN CRAFTING STATION ===
    GUN_WORKBENCH(98, "Gun Workbench", Color.rgb(60, 60, 80), 5.0f, true, false),
    AMMO_BOX(99, "Ammo Box", Color.rgb(80, 70, 50), 3.0f, true, false),

    // === PLANTS ===
    GRASS_PLANT(100, "Grass Plant", Color.rgb(80, 160, 50), 0f, false, false),
    FERN(101, "Fern", Color.rgb(60, 140, 50), 0f, false, false),
    FLOWER_RED(102, "Red Flower", Color.rgb(200, 50, 50), 0f, false, false),
    FLOWER_YELLOW(103, "Yellow Flower", Color.rgb(220, 200, 50), 0f, false, false),
    CACTUS(104, "Cactus", Color.rgb(60, 140, 60), 0.8f, true, false),
    SUGAR_CANE(105, "Sugar Cane", Color.rgb(100, 170, 70), 0f, false, false),
    WHEAT(106, "Wheat", Color.rgb(200, 180, 80), 0f, false, false),
    MUSHROOM_RED(107, "Red Mushroom", Color.rgb(180, 50, 50), 0f, false, false),
    MUSHROOM_BROWN(108, "Brown Mushroom", Color.rgb(130, 90, 60), 0f, false, false),
    CRYSTAL_FLOWER(109, "Crystal Flower", Color.rgb(180, 240, 255), 0f, false, false),
    VOID_VINE(110, "Void Vine", Color.rgb(30, 0, 60), 0f, false, false);

    private final int id;
    private final String name;
    private final int color;
    private final float hardness;
    private final boolean solid;
    private final boolean transparent;

    BlockType(int id, String name, int color, float hardness, boolean solid, boolean transparent) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.hardness = hardness;
        this.solid = solid;
        this.transparent = transparent;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getColor() { return color; }
    public float getHardness() { return hardness; }
    public boolean isSolid() { return solid; }
    public boolean isTransparent() { return transparent; }
    public boolean isLiquid() { return this == WATER || this == LAVA; }
    public boolean isPlant() { return id >= 100 && id <= 110; }
    public boolean isOre() { return (id >= 31 && id <= 46); }
    public boolean emitsLight() {
        return this == GLOWSTONE || this == SEA_LANTERN || this == TORCH ||
               this == LANTERN || this == CAMPFIRE || this == CRYSTAL_BLOCK ||
               this == STARLIGHT_GLASS || this == BEACON || this == LAVA;
    }
    public int getLightLevel() {
        return switch (this) {
            case GLOWSTONE, SEA_LANTERN -> 15;
            case TORCH, CAMPFIRE, LAVA -> 14;
            case LANTERN -> 13;
            case CRYSTAL_BLOCK -> 10;
            case BEACON -> 15;
            case STARLIGHT_GLASS -> 8;
            default -> 0;
        };
    }
    public boolean isBreakable() { return hardness >= 0; }
    public float getBlastResistance() { return hardness * 3.0f; }

    public static BlockType fromId(int id) {
        for (BlockType bt : values()) {
            if (bt.id == id) return bt;
        }
        return AIR;
    }
}
