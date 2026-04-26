package com.minecraft2.android.save;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.world.World;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {

    private final Context context;
    private final Gson gson;
    private static final String WORLDS_DIR = "worlds";
    private static final String WORLD_FILE = "world.json";
    private static final String PREFS_NAME = "minecraft2_prefs";

    public static class WorldData {
        public String worldName;
        public long seed;
        public long worldTick;
        public float timeOfDay;
        public float playerX, playerY, playerZ;
        public float playerHealth, playerHunger;
        public float playerXP;
        public int playerXPLevel;
        public float playerYaw, playerPitch;
        public String[] inventoryTypes;
        public int[] inventoryCounts;
        public boolean raining;
        public long createdAt;
        public long lastPlayed;
        public int playtimeMinutes;
        public String[] spawnedBosses;
    }

    public SaveManager(Context context) {
        this.context = context;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void saveWorld(String worldName, World world, Player player) {
        WorldData data = new WorldData();
        data.worldName = worldName;
        data.seed = world.getSeed();
        data.worldTick = world.getWorldTick();
        data.timeOfDay = world.getTimeOfDay();
        data.playerX = player.getX();
        data.playerY = player.getY();
        data.playerZ = player.getZ();
        data.playerHealth = player.getHealth();
        data.playerHunger = player.getHunger();
        data.playerXP = player.getXp();
        data.playerXPLevel = player.getXpLevel();
        data.playerYaw = player.getYaw();
        data.playerPitch = player.getPitch();
        data.lastPlayed = System.currentTimeMillis();

        // Serialize inventory
        com.minecraft2.android.item.ItemStack[] slots = player.getInventory().getSlots();
        data.inventoryTypes = new String[slots.length];
        data.inventoryCounts = new int[slots.length];
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && !slots[i].isEmpty()) {
                data.inventoryTypes[i] = slots[i].getType().name();
                data.inventoryCounts[i] = slots[i].getCount();
            } else {
                data.inventoryTypes[i] = "AIR";
                data.inventoryCounts[i] = 0;
            }
        }

        try {
            File dir = new File(context.getFilesDir(), WORLDS_DIR + File.separator + worldName);
            dir.mkdirs();
            File file = new File(dir, WORLD_FILE);
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(data, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save to prefs for quick listing
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString("world_" + worldName, worldName).apply();
    }

    public WorldData loadWorld(String worldName) {
        try {
            File file = new File(context.getFilesDir(),
                    WORLDS_DIR + File.separator + worldName + File.separator + WORLD_FILE);
            if (!file.exists()) return null;
            try (FileReader reader = new FileReader(file)) {
                return gson.fromJson(reader, WorldData.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] listWorlds() {
        File dir = new File(context.getFilesDir(), WORLDS_DIR);
        if (!dir.exists()) return new String[0];
        File[] files = dir.listFiles(File::isDirectory);
        if (files == null) return new String[0];
        List<String> names = new ArrayList<>();
        for (File f : files) {
            File worldFile = new File(f, WORLD_FILE);
            if (worldFile.exists()) names.add(f.getName());
        }
        return names.toArray(new String[0]);
    }

    public boolean deleteWorld(String worldName) {
        File dir = new File(context.getFilesDir(), WORLDS_DIR + File.separator + worldName);
        return deleteRecursive(dir);
    }

    private boolean deleteRecursive(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) deleteRecursive(child);
            }
        }
        return file.delete();
    }

    public void saveSettings(String key, String value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putString("setting_" + key, value).apply();
    }

    public String loadSetting(String key, String defaultValue) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString("setting_" + key, defaultValue);
    }
}
