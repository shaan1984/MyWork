package com.minecraft2.android.ore;

import android.graphics.Color;
import com.minecraft2.android.block.BlockType;
import com.minecraft2.android.item.ItemType;

public enum OreType {

    // === MINECRAFT 1 ORES ===
    COAL(BlockType.COAL_ORE, ItemType.COAL, "Coal", 0, 128, 17, 1, Color.rgb(50, 50, 50), 1),
    IRON(BlockType.IRON_ORE, ItemType.IRON_INGOT, "Iron", 0, 64, 9, 1, Color.rgb(200, 160, 130), 2),
    COPPER(BlockType.COPPER_ORE, ItemType.IRON_INGOT, "Copper", 0, 96, 10, 1, Color.rgb(180, 110, 70), 2),
    GOLD(BlockType.GOLD_ORE, ItemType.GOLD_INGOT, "Gold", 0, 32, 9, 1, Color.rgb(210, 180, 30), 3),
    LAPIS(BlockType.LAPIS_ORE, ItemType.LAPIS, "Lapis", 0, 30, 7, 4, Color.rgb(60, 80, 180), 2),
    REDSTONE(BlockType.REDSTONE_ORE, ItemType.REDSTONE, "Redstone", 0, 16, 8, 4, Color.rgb(180, 30, 30), 2),
    DIAMOND(BlockType.DIAMOND_ORE, ItemType.DIAMOND, "Diamond", 0, 16, 8, 1, Color.rgb(50, 210, 230), 3),
    EMERALD(BlockType.EMERALD_ORE, ItemType.EMERALD, "Emerald", 0, 28, 1, 1, Color.rgb(30, 200, 80), 3),
    NETHERITE(BlockType.NETHERITE_ORE, ItemType.NETHERITE_INGOT, "Netherite", 0, 15, 3, 1, Color.rgb(70, 50, 50), 4),

    // === NEW MC2 ORES ===
    TITANIUM(BlockType.TITANIUM_ORE, ItemType.TITANIUM_INGOT, "Titanium",
            10, 60, 6, 1, Color.rgb(150, 180, 220), 4,
            "Smelted into Titanium Ingots. Used for mid-tier armor and tools."),
    TUNGSTEN(BlockType.TUNGSTEN_ORE, ItemType.TUNGSTEN_INGOT, "Tungsten",
            20, 50, 4, 1, Color.rgb(80, 100, 120), 4,
            "Incredibly dense. Makes the strongest melee weapons."),
    ADAMANTITE(BlockType.ADAMANTITE_ORE, ItemType.ADAMANTITE_INGOT, "Adamantite",
            30, 30, 3, 1, Color.rgb(180, 50, 180), 5,
            "Rare magical ore with innate enchanting properties."),
    CRYSTAL(BlockType.CRYSTAL_ORE, ItemType.CRYSTAL_SHARD, "Crystal",
            40, 60, 4, 2, Color.rgb(200, 240, 255), 3,
            "Refracts light. Used in Crystal Armor and weapons."),
    VOID(BlockType.VOID_ORE, ItemType.VOID_ESSENCE, "Void",
            5, 10, 1, 1, Color.rgb(20, 0, 40), 5,
            "Found only in Void biomes. Extremely rare and powerful."),
    STARLIGHT(BlockType.STARLIGHT_ORE, ItemType.STARLIGHT_DUST, "Starlight",
            50, 80, 3, 3, Color.rgb(255, 230, 100), 3,
            "Glows like starlight. Used in Starlight equipment."),
    SHADOW(BlockType.SHADOW_ORE, ItemType.SHADOW_POWDER, "Shadow",
            5, 20, 2, 1, Color.rgb(40, 20, 60), 5,
            "Dark as night. Core material for Shadow-tier gear.");

    private final BlockType blockType;
    private final ItemType dropItem;
    private final String displayName;
    private final int minY, maxY;
    private final int veinSize;
    private final int dropAmount;
    private final int color;
    private final int miningLevel;
    private final String description;

    OreType(BlockType blockType, ItemType dropItem, String displayName,
             int minY, int maxY, int veinSize, int dropAmount, int color, int miningLevel) {
        this(blockType, dropItem, displayName, minY, maxY, veinSize, dropAmount, color, miningLevel, "");
    }

    OreType(BlockType blockType, ItemType dropItem, String displayName,
             int minY, int maxY, int veinSize, int dropAmount, int color, int miningLevel,
             String description) {
        this.blockType = blockType;
        this.dropItem = dropItem;
        this.displayName = displayName;
        this.minY = minY;
        this.maxY = maxY;
        this.veinSize = veinSize;
        this.dropAmount = dropAmount;
        this.color = color;
        this.miningLevel = miningLevel;
        this.description = description;
    }

    public BlockType getBlockType() { return blockType; }
    public ItemType getDropItem() { return dropItem; }
    public String getDisplayName() { return displayName; }
    public int getMinY() { return minY; }
    public int getMaxY() { return maxY; }
    public int getVeinSize() { return veinSize; }
    public int getDropAmount() { return dropAmount; }
    public int getColor() { return color; }
    public int getMiningLevel() { return miningLevel; }
    public String getDescription() { return description; }
    public boolean isNew() { return ordinal() >= TITANIUM.ordinal(); }
}
