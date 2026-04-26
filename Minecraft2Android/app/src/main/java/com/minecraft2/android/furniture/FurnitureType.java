package com.minecraft2.android.furniture;

import android.graphics.Color;
import com.minecraft2.android.item.ItemType;

public enum FurnitureType {

    // === SEATING ===
    CHAIR("Chair", ItemType.CHAIR, 1, 1, 1, Color.rgb(120, 80, 40), true, false),
    SOFA("Sofa", ItemType.SOFA, 2, 1, 1, Color.rgb(100, 60, 140), true, false),
    ARMCHAIR("Armchair", ItemType.ARMCHAIR, 1, 1, 1, Color.rgb(140, 100, 60), true, false),

    // === SLEEPING ===
    BED("Bed", ItemType.BED, 2, 1, 1, Color.rgb(180, 50, 50), false, true),

    // === STORAGE ===
    WARDROBE("Wardrobe", ItemType.WARDROBE, 1, 2, 1, Color.rgb(100, 70, 40), false, false),
    SHELF("Shelf", ItemType.SHELF, 1, 1, 1, Color.rgb(130, 90, 50), false, false),
    BOOKCASE("Bookcase", ItemType.BOOKCASE, 1, 2, 1, Color.rgb(110, 75, 40), false, false),
    DESK("Desk", ItemType.DESK, 2, 1, 1, Color.rgb(140, 100, 60), false, false),

    // === LIGHTING ===
    LAMP("Lamp", ItemType.LAMP, 1, 2, 1, Color.rgb(240, 220, 120), false, false),
    CHANDELIER("Chandelier", ItemType.CHANDELIER, 2, 1, 2, Color.rgb(220, 190, 80), false, false),
    FIREPLACE("Fireplace", ItemType.FIREPLACE, 2, 2, 1, Color.rgb(180, 80, 30), false, false),

    // === KITCHEN ===
    STOVE("Stove", ItemType.STOVE, 1, 1, 1, Color.rgb(60, 60, 60), false, false),
    FRIDGE("Fridge", ItemType.FRIDGE, 1, 2, 1, Color.rgb(220, 220, 220), false, false),
    SINK("Sink", ItemType.SINK, 1, 1, 1, Color.rgb(200, 220, 230), false, false),

    // === BATHROOM ===
    TOILET("Toilet", ItemType.TOILET, 1, 1, 1, Color.rgb(240, 240, 240), false, false),
    BATHTUB("Bathtub", ItemType.BATHTUB, 2, 1, 1, Color.rgb(230, 240, 250), false, false),

    // === DECOR ===
    TABLE("Table", ItemType.TABLE, 2, 1, 1, Color.rgb(150, 110, 60), false, false),
    PAINTING("Painting", ItemType.PAINTING, 1, 1, 1, Color.rgb(200, 160, 120), false, false),
    FLOWER_POT("Flower Pot", ItemType.FLOWER_POT, 1, 1, 1, Color.rgb(150, 90, 60), false, false),
    CURTAIN("Curtain", ItemType.CURTAIN, 1, 2, 1, Color.rgb(180, 120, 120), false, false),
    RUG("Rug", ItemType.RUG, 2, 1, 2, Color.rgb(180, 80, 80), false, false),

    // === ELECTRONICS ===
    COMPUTER("Computer", ItemType.COMPUTER, 1, 1, 1, Color.rgb(40, 40, 40), false, false),
    TELEVISION("Television", ItemType.TELEVISION, 2, 1, 1, Color.rgb(20, 20, 20), false, false);

    private final String displayName;
    private final ItemType itemType;
    private final int widthBlocks, heightBlocks, depthBlocks;
    private final int color;
    private final boolean isSeat;
    private final boolean isBed;

    FurnitureType(String displayName, ItemType itemType, int widthBlocks, int heightBlocks, int depthBlocks,
                  int color, boolean isSeat, boolean isBed) {
        this.displayName = displayName;
        this.itemType = itemType;
        this.widthBlocks = widthBlocks;
        this.heightBlocks = heightBlocks;
        this.depthBlocks = depthBlocks;
        this.color = color;
        this.isSeat = isSeat;
        this.isBed = isBed;
    }

    public String getDisplayName() { return displayName; }
    public ItemType getItemType() { return itemType; }
    public int getWidthBlocks() { return widthBlocks; }
    public int getHeightBlocks() { return heightBlocks; }
    public int getDepthBlocks() { return depthBlocks; }
    public int getColor() { return color; }
    public boolean isSeat() { return isSeat; }
    public boolean isBed() { return isBed; }
}
