package com.minecraft2.android.save;

import android.content.Context;
import android.content.SharedPreferences;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import com.minecraft2.android.world.World;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {

    private final Context context;
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
    }

    public SaveManager(Context context) {
        this.context = context;
    }

    public void saveWorld(String worldName, World world, Player player) {
        try {
            JSONObject data = new JSONObject();
            data.put("worldName", worldName);
            data.put("seed", world.getSeed());
            data.put("worldTick", world.getWorldTick());
            data.put("timeOfDay", world.getTimeOfDay());
            data.put("playerX", player.getX());
            data.put("playerY", player.getY());
            data.put("playerZ", player.getZ());
            data.put("playerHealth", player.getHealth());
            data.put("playerHunger", player.getHunger());
            data.put("playerXP", player.getXp());
            data.put("playerXPLevel", player.getXpLevel());
            data.put("lastPlayed", System.currentTimeMillis());

            JSONArray invTypes = new JSONArray();
            JSONArray invCounts = new JSONArray();
            ItemStack[] slots = player.getInventory().getSlots();
            for (ItemStack slot : slots) {
                if (slot != null && !slot.isEmpty()) {
                    invTypes.put(slot.getType().name());
                    invCounts.put(slot.getCount());
                } else {
                    invTypes.put("AIR");
                    invCounts.put(0);
                }
            }
            data.put("inventoryTypes", invTypes);
            data.put("inventoryCounts", invCounts);

            File dir = new File(context.getFilesDir(), WORLDS_DIR + File.separator + worldName);
            dir.mkdirs();
            File file = new File(dir, WORLD_FILE);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(data.toString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putString("world_" + worldName, worldName).apply();
    }

    public WorldData loadWorld(String worldName) {
        try {
            File file = new File(context.getFilesDir(),
                    WORLDS_DIR + File.separator + worldName + File.separator + WORLD_FILE);
            if (!file.exists()) return null;

            StringBuilder sb = new StringBuilder();
            try (FileReader reader = new FileReader(file)) {
                char[] buf = new char[4096];
                int n;
                while ((n = reader.read(buf)) != -1) sb.append(buf, 0, n);
            }

            JSONObject json = new JSONObject(sb.toString());
            WorldData data = new WorldData();
            data.worldName = json.optString("worldName", worldName);
            data.seed = json.optLong("seed", System.currentTimeMillis());
            data.worldTick = json.optLong("worldTick", 0);
            data.timeOfDay = (float) json.optDouble("timeOfDay", 0.3);
            data.playerX = (float) json.optDouble("playerX", 0);
            data.playerY = (float) json.optDouble("playerY", 70);
            data.playerZ = (float) json.optDouble("playerZ", 0);
            data.playerHealth = (float) json.optDouble("playerHealth", 20);
            data.playerHunger = (float) json.optDouble("playerHunger", 20);
            data.playerXP = (float) json.optDouble("playerXP", 0);
            data.playerXPLevel = json.optInt("playerXPLevel", 0);
            return data;
        } catch (Exception e) {
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
            if (new File(f, WORLD_FILE).exists()) names.add(f.getName());
        }
        return names.toArray(new String[0]);
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
