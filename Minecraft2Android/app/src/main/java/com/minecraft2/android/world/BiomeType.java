package com.minecraft2.android.world;

import android.graphics.Color;

public enum BiomeType {

    // === MINECRAFT 1 BIOMES ===
    PLAINS("Plains", 0.8f, 0.4f, 64, Color.rgb(86, 168, 54)),
    FOREST("Forest", 0.7f, 0.8f, 70, Color.rgb(60, 140, 40)),
    BIRCH_FOREST("Birch Forest", 0.65f, 0.75f, 68, Color.rgb(100, 160, 60)),
    DARK_FOREST("Dark Forest", 0.7f, 0.9f, 70, Color.rgb(30, 80, 20)),
    JUNGLE("Jungle", 0.95f, 0.9f, 70, Color.rgb(50, 150, 30)),
    DESERT("Desert", 2.0f, 0.0f, 62, Color.rgb(218, 210, 158)),
    SAVANNA("Savanna", 1.2f, 0.0f, 65, Color.rgb(189, 178, 95)),
    BADLANDS("Badlands", 2.0f, 0.0f, 80, Color.rgb(180, 100, 50)),
    TAIGA("Taiga", 0.25f, 0.8f, 66, Color.rgb(80, 120, 80)),
    SNOWY_TUNDRA("Snowy Tundra", 0.0f, 0.5f, 62, Color.rgb(200, 220, 230)),
    SNOWY_TAIGA("Snowy Taiga", -0.5f, 0.8f, 64, Color.rgb(130, 170, 150)),
    MOUNTAINS("Mountains", 0.2f, 0.3f, 100, Color.rgb(120, 120, 120)),
    GRAVELLY_MOUNTAINS("Gravelly Mountains", 0.2f, 0.3f, 110, Color.rgb(130, 130, 130)),
    OCEAN("Ocean", 0.5f, 0.5f, 46, Color.rgb(30, 80, 180)),
    DEEP_OCEAN("Deep Ocean", 0.5f, 0.5f, 30, Color.rgb(10, 50, 150)),
    BEACH("Beach", 0.8f, 0.4f, 62, Color.rgb(210, 195, 140)),
    RIVER("River", 0.5f, 0.5f, 56, Color.rgb(40, 100, 200)),
    SWAMP("Swamp", 0.8f, 0.9f, 60, Color.rgb(80, 110, 70)),
    MUSHROOM_ISLAND("Mushroom Island", 0.9f, 1.0f, 80, Color.rgb(160, 80, 120)),
    NETHER_WASTES("Nether Wastes", 2.0f, 0.0f, 64, Color.rgb(120, 30, 30)),
    SOUL_SAND_VALLEY("Soul Sand Valley", 2.0f, 0.0f, 64, Color.rgb(70, 55, 40)),
    CRIMSON_FOREST("Crimson Forest", 2.0f, 0.0f, 64, Color.rgb(150, 30, 30)),
    WARPED_FOREST("Warped Forest", 2.0f, 0.0f, 64, Color.rgb(30, 120, 110)),
    BASALT_DELTAS("Basalt Deltas", 2.0f, 0.0f, 64, Color.rgb(60, 60, 70)),
    THE_END("The End", 0.5f, 0.5f, 64, Color.rgb(180, 180, 130)),

    // === NEW MC2 BIOMES ===
    CRYSTAL_CAVES("Crystal Caves", 0.5f, 0.5f, 40, Color.rgb(180, 220, 255)),
    VOID_WASTES("Void Wastes", 0.0f, 0.0f, 64, Color.rgb(15, 5, 30)),
    LAVA_FIELDS("Lava Fields", 3.0f, 0.0f, 50, Color.rgb(200, 80, 20)),
    ICE_SPIRES("Ice Spires", -1.0f, 0.0f, 80, Color.rgb(180, 210, 240)),
    SHADOW_FOREST("Shadow Forest", 0.5f, 0.8f, 68, Color.rgb(20, 10, 40)),
    TITANIUM_PEAKS("Titanium Peaks", 0.1f, 0.2f, 130, Color.rgb(140, 170, 200)),
    STARLIGHT_MEADOW("Starlight Meadow", 0.8f, 0.6f, 65, Color.rgb(200, 220, 150)),
    FUNGAL_DEPTHS("Fungal Depths", 0.9f, 1.0f, 45, Color.rgb(100, 60, 100));

    private final String displayName;
    private final float temperature;
    private final float humidity;
    private final int baseHeight;
    private final int grassColor;

    BiomeType(String displayName, float temperature, float humidity, int baseHeight, int grassColor) {
        this.displayName = displayName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.baseHeight = baseHeight;
        this.grassColor = grassColor;
    }

    public String getDisplayName() { return displayName; }
    public float getTemperature() { return temperature; }
    public float getHumidity() { return humidity; }
    public int getBaseHeight() { return baseHeight; }
    public int getGrassColor() { return grassColor; }
    public boolean isNether() { return ordinal() >= NETHER_WASTES.ordinal() && ordinal() <= BASALT_DELTAS.ordinal(); }
    public boolean isEnd() { return this == THE_END; }
    public boolean isNew() { return ordinal() >= CRYSTAL_CAVES.ordinal(); }

    @Override
    public String toString() { return displayName; }
}
