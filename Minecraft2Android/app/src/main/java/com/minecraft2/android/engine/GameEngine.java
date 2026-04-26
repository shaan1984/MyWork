package com.minecraft2.android.engine;

import android.content.Context;
import com.minecraft2.android.audio.SoundManager;
import com.minecraft2.android.entity.Player;
import com.minecraft2.android.physics.PhysicsEngine;
import com.minecraft2.android.save.SaveManager;
import com.minecraft2.android.world.World;

public class GameEngine {

    public static final int TARGET_FPS = 60;
    public static final float GRAVITY = -20.0f;
    public static final float TERMINAL_VELOCITY = -50.0f;

    private final Context context;
    private final String worldName;
    private final long seed;

    private World world;
    private Player player;
    private PhysicsEngine physicsEngine;
    private SoundManager soundManager;
    private SaveManager saveManager;
    private GameLoop gameLoop;

    private volatile boolean running = false;
    private volatile boolean paused = false;
    private int tickCount = 0;

    public GameEngine(Context context, String worldName, long seed) {
        this.context = context;
        this.worldName = worldName;
        this.seed = seed;
        initialize();
    }

    private void initialize() {
        soundManager = new SoundManager(context);
        saveManager = new SaveManager(context);
        physicsEngine = new PhysicsEngine();

        SaveManager.WorldData worldData = saveManager.loadWorld(worldName);
        if (worldData != null) {
            world = new World(worldData.seed, worldName);
            world.generate();
            world.loadFromData(worldData);
            player = new Player(worldData.playerX, worldData.playerY, worldData.playerZ);
            player.loadFromData(worldData);
        } else {
            world = new World(seed, worldName);
            world.generate();
            float[] spawnPos = world.findSafeSpawn();
            player = new Player(spawnPos[0], spawnPos[1], spawnPos[2]);
        }

        world.setPlayer(player);
        running = true;
    }

    public void start(GameLoop loop) {
        this.gameLoop = loop;
        running = true;
    }

    public void update(float deltaTime) {
        if (paused || !running) return;

        world.update(deltaTime);
        physicsEngine.update(player, world, deltaTime);
        player.update(deltaTime, world);

        tickCount++;
        if (tickCount % 20 == 0) {
            world.randomTick();
        }
    }

    public void pause() { paused = true; }
    public void resume() { paused = false; }

    public void destroy() {
        running = false;
        soundManager.release();
    }

    public void saveWorld() {
        if (world != null && player != null) {
            saveManager.saveWorld(worldName, world, player);
        }
    }

    public boolean isRunning() { return running; }
    public boolean isPaused() { return paused; }
    public World getWorld() { return world; }
    public Player getPlayer() { return player; }
    public SoundManager getSoundManager() { return soundManager; }
    public PhysicsEngine getPhysicsEngine() { return physicsEngine; }
    public Context getContext() { return context; }
}
