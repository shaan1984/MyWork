package com.minecraft2.android.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    public enum SoundEffect {
        BLOCK_BREAK, BLOCK_PLACE, FOOTSTEP_GRASS, FOOTSTEP_STONE,
        FOOTSTEP_SAND, FOOTSTEP_WOOD, PLAYER_HURT, PLAYER_DEATH,
        ZOMBIE_GROAN, SKELETON_RATTLE, CREEPER_HISS, CREEPER_EXPLODE,
        SPIDER_HISS, ENDERMAN_STARE, BOSS_ROAR, BOSS_DEATH,
        PISTOL_SHOOT, SMG_SHOOT, RIFLE_SHOOT, SHOTGUN_SHOOT,
        SNIPER_SHOOT, RPG_SHOOT, MINIGUN_SHOOT, EXPLOSION,
        GUN_RELOAD, GUN_EMPTY, BULLET_IMPACT, BULLET_WALL,
        AMBIENT_DAY, AMBIENT_NIGHT, AMBIENT_CAVE, AMBIENT_RAIN,
        ITEM_PICKUP, ITEM_DROP, CRAFTING, EATING,
        XP_PICKUP, LEVEL_UP, BOSS_INCOMING, UI_CLICK,
        ICE_CRACK, CRYSTAL_BREAK, VOID_WHOOSH, FIRE_CRACKLE,
        THUNDER, WIND
    }

    private final SoundPool soundPool;
    private final Map<SoundEffect, Integer> soundIds = new HashMap<>();
    private final Map<SoundEffect, Integer> activeSounds = new HashMap<>();
    private float masterVolume = 1.0f;
    private float sfxVolume = 1.0f;
    private float musicVolume = 0.6f;
    private boolean enabled = true;

    public SoundManager(Context context) {
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(16)
                .setAudioAttributes(attrs)
                .build();

        // In a real implementation, sound files would be loaded from res/raw
        // loadSounds(context);
    }

    public void playSound(SoundEffect effect) {
        playSound(effect, 1.0f, 1.0f);
    }

    public void playSound(SoundEffect effect, float volume, float pitch) {
        if (!enabled) return;
        Integer soundId = soundIds.get(effect);
        if (soundId != null) {
            float vol = volume * sfxVolume * masterVolume;
            soundPool.play(soundId, vol, vol, 1, 0, pitch);
        }
    }

    public void playGunSound(String gunType) {
        switch (gunType) {
            case "PISTOL": playSound(SoundEffect.PISTOL_SHOOT, 0.8f, 1.0f); break;
            case "SMG": playSound(SoundEffect.SMG_SHOOT, 0.7f, 1.1f); break;
            case "RIFLE": playSound(SoundEffect.RIFLE_SHOOT, 0.9f, 1.0f); break;
            case "SHOTGUN": playSound(SoundEffect.SHOTGUN_SHOOT, 1.0f, 0.9f); break;
            case "SNIPER_RIFLE": playSound(SoundEffect.SNIPER_SHOOT, 1.0f, 0.8f); break;
            case "RPG": playSound(SoundEffect.RPG_SHOOT, 0.8f, 1.0f); playSound(SoundEffect.EXPLOSION); break;
            case "MINIGUN": playSound(SoundEffect.MINIGUN_SHOOT, 0.6f, 1.2f); break;
        }
    }

    public void playFootstep(com.minecraft2.android.block.BlockType surface) {
        SoundEffect sound;
        if (surface == com.minecraft2.android.block.BlockType.GRASS || surface == com.minecraft2.android.block.BlockType.DIRT) {
            sound = SoundEffect.FOOTSTEP_GRASS;
        } else if (surface == com.minecraft2.android.block.BlockType.SAND || surface == com.minecraft2.android.block.BlockType.GRAVEL) {
            sound = SoundEffect.FOOTSTEP_SAND;
        } else if (surface == com.minecraft2.android.block.BlockType.OAK_PLANKS || surface == com.minecraft2.android.block.BlockType.BIRCH_PLANKS || surface == com.minecraft2.android.block.BlockType.SPRUCE_PLANKS) {
            sound = SoundEffect.FOOTSTEP_WOOD;
        } else {
            sound = SoundEffect.FOOTSTEP_STONE;
        }
        playSound(sound, 0.4f, 0.9f + (float)(Math.random() * 0.2f));
    }

    public void playBlockBreak(com.minecraft2.android.block.BlockType block) {
        playSound(SoundEffect.BLOCK_BREAK, 0.8f, 0.8f + (float)(Math.random() * 0.4f));
    }

    public void playBlockPlace(com.minecraft2.android.block.BlockType block) {
        playSound(SoundEffect.BLOCK_PLACE, 0.8f, 0.9f + (float)(Math.random() * 0.2f));
    }

    public void setMasterVolume(float v) { masterVolume = Math.max(0, Math.min(1, v)); }
    public void setSFXVolume(float v) { sfxVolume = Math.max(0, Math.min(1, v)); }
    public void setMusicVolume(float v) { musicVolume = Math.max(0, Math.min(1, v)); }
    public void setEnabled(boolean e) { enabled = e; }
    public boolean isEnabled() { return enabled; }

    public void release() {
        soundPool.release();
    }
}
