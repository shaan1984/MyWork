package com.minecraft2.android.armor;

import android.graphics.Color;
import com.minecraft2.android.item.ItemType;

public enum ArmorSet {

    // === MINECRAFT 1 ARMOR SETS ===
    LEATHER("Leather", new ItemType[]{ItemType.LEATHER_ARMOR, ItemType.LEATHER_ARMOR, ItemType.LEATHER_ARMOR, ItemType.LEATHER_ARMOR},
            5, 0, 0, 0f, Color.rgb(160, 100, 60),
            "Basic protection. Easy to get early game."),

    IRON("Iron", new ItemType[]{ItemType.IRON_HELMET, ItemType.IRON_CHESTPLATE, ItemType.IRON_LEGGINGS, ItemType.IRON_BOOTS},
            15, 0, 0, 0f, Color.rgb(200, 200, 210),
            "Solid mid-game armor. Good all-rounder."),

    DIAMOND("Diamond", new ItemType[]{ItemType.DIAMOND_HELMET, ItemType.DIAMOND_CHESTPLATE, ItemType.DIAMOND_LEGGINGS, ItemType.DIAMOND_BOOTS},
            20, 2, 0, 0f, Color.rgb(50, 200, 220),
            "Best vanilla armor. High protection and durability."),

    NETHERITE("Netherite", new ItemType[]{ItemType.NETHERITE_HELMET, ItemType.NETHERITE_CHESTPLATE, ItemType.NETHERITE_LEGGINGS, ItemType.NETHERITE_BOOTS},
            20, 3, 0, 0.1f, Color.rgb(70, 50, 50),
            "Upgraded Diamond. Fire resistant and knockback immune."),

    // === NEW MC2 ARMOR SETS ===
    TITANIUM("Titanium", new ItemType[]{ItemType.TITANIUM_ARMOR_SET, ItemType.TITANIUM_ARMOR_SET, ItemType.TITANIUM_ARMOR_SET, ItemType.TITANIUM_ARMOR_SET},
            25, 2, 5, 0.1f, Color.rgb(150, 180, 220),
            "Strong lightweight armor. Boosts movement speed by 10%."),

    TUNGSTEN("Tungsten", new ItemType[]{ItemType.TUNGSTEN_ARMOR_SET, ItemType.TUNGSTEN_ARMOR_SET, ItemType.TUNGSTEN_ARMOR_SET, ItemType.TUNGSTEN_ARMOR_SET},
            30, 4, 0, 0.2f, Color.rgb(80, 100, 120),
            "Extremely heavy but very protective. Reduces movement by 5%."),

    ADAMANTITE("Adamantite", new ItemType[]{ItemType.ADAMANTITE_ARMOR_SET, ItemType.ADAMANTITE_ARMOR_SET, ItemType.ADAMANTITE_ARMOR_SET, ItemType.ADAMANTITE_ARMOR_SET},
            32, 4, 8, 0.3f, Color.rgb(180, 50, 180),
            "Magical properties. Enemies have 20% chance to miss."),

    CRYSTAL("Crystal", new ItemType[]{ItemType.CRYSTAL_ARMOR_SET, ItemType.CRYSTAL_ARMOR_SET, ItemType.CRYSTAL_ARMOR_SET, ItemType.CRYSTAL_ARMOR_SET},
            28, 3, 10, 0.15f, Color.rgb(180, 240, 255),
            "Reflects 15% of damage back. Glows in the dark."),

    VOID("Void", new ItemType[]{ItemType.VOID_ARMOR_SET, ItemType.VOID_ARMOR_SET, ItemType.VOID_ARMOR_SET, ItemType.VOID_ARMOR_SET},
            40, 5, 12, 0.4f, Color.rgb(20, 0, 40),
            "Ultimate armor. Immunity to void damage. Teleport on low HP."),

    SHADOW("Shadow", new ItemType[]{ItemType.SHADOW_ARMOR_SET, ItemType.SHADOW_ARMOR_SET, ItemType.SHADOW_ARMOR_SET, ItemType.SHADOW_ARMOR_SET},
            35, 4, 8, 0.25f, Color.rgb(30, 15, 50),
            "Grants partial invisibility. Improves stealth attacks."),

    DRAGON_LORD("Dragon Lord", new ItemType[]{ItemType.DRAGON_LORD_ARMOR, ItemType.DRAGON_LORD_ARMOR, ItemType.DRAGON_LORD_ARMOR, ItemType.DRAGON_LORD_ARMOR},
            45, 5, 15, 0.35f, Color.rgb(160, 20, 20),
            "Fire immunity. Massive damage resistance. Intimidates weaker mobs."),

    LAVA_TITAN("Lava Titan", new ItemType[]{ItemType.LAVA_TITAN_ARMOR, ItemType.LAVA_TITAN_ARMOR, ItemType.LAVA_TITAN_ARMOR, ItemType.LAVA_TITAN_ARMOR},
            40, 5, 10, 0.3f, Color.rgb(200, 50, 10),
            "Fire and lava immunity. Walking on lava leaves fire trails.");

    private final String displayName;
    private final ItemType[] pieces;
    private final int armorPoints;
    private final int armorToughness;
    private final int setBonus;
    private final float knockbackResistance;
    private final int color;
    private final String description;

    ArmorSet(String displayName, ItemType[] pieces, int armorPoints, int armorToughness,
             int setBonus, float knockbackResistance, int color, String description) {
        this.displayName = displayName;
        this.pieces = pieces;
        this.armorPoints = armorPoints;
        this.armorToughness = armorToughness;
        this.setBonus = setBonus;
        this.knockbackResistance = knockbackResistance;
        this.color = color;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public ItemType[] getPieces() { return pieces; }
    public int getArmorPoints() { return armorPoints; }
    public int getArmorToughness() { return armorToughness; }
    public int getSetBonus() { return setBonus; }
    public float getKnockbackResistance() { return knockbackResistance; }
    public int getColor() { return color; }
    public String getDescription() { return description; }
    public boolean isNew() { return ordinal() >= TITANIUM.ordinal(); }
    public float getDamageReduction() { return Math.min(0.8f, armorPoints * 0.04f + knockbackResistance * 0.1f); }
}
