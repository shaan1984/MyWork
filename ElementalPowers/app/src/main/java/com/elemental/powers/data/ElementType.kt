package com.elemental.powers.data

enum class ElementType(
    val displayName: String,
    val symbol: String,
    val colorHex: String,
    val description: String,
    val baseHP: Int,
    val baseSpeed: Int,
    val basePower: Int
) {
    FIRE(
        displayName = "Fire",
        symbol = "🔥",
        colorHex = "#FF4500",
        description = "Burns with fierce, unstoppable intensity",
        baseHP = 90,
        baseSpeed = 8,
        basePower = 9
    ),
    WATER(
        displayName = "Water",
        symbol = "💧",
        colorHex = "#1E90FF",
        description = "Flows with adaptable, crushing power",
        baseHP = 100,
        baseSpeed = 7,
        basePower = 7
    ),
    EARTH(
        displayName = "Earth",
        symbol = "🌍",
        colorHex = "#8B6914",
        description = "Stands firm with unyielding strength",
        baseHP = 120,
        baseSpeed = 4,
        basePower = 8
    ),
    ICE(
        displayName = "Ice",
        symbol = "❄️",
        colorHex = "#00BFFF",
        description = "Freezes foes with chilling precision",
        baseHP = 85,
        baseSpeed = 9,
        basePower = 8
    ),
    LIFE(
        displayName = "Life",
        symbol = "🌿",
        colorHex = "#2E8B57",
        description = "Heals and strikes with nature's grace",
        baseHP = 110,
        baseSpeed = 6,
        basePower = 7
    ),
    TECH(
        displayName = "Tech",
        symbol = "⚡",
        colorHex = "#7B2FBE",
        description = "Strikes with technological superiority",
        baseHP = 88,
        baseSpeed = 8,
        basePower = 9
    ),
    HEAT(
        displayName = "Heat",
        symbol = "☀️",
        colorHex = "#FF6347",
        description = "Scorches everything with solar energy",
        baseHP = 92,
        baseSpeed = 7,
        basePower = 9
    ),
    EARTHQUAKE(
        displayName = "Earthquake",
        symbol = "💥",
        colorHex = "#8B5E3C",
        description = "Shakes the very ground beneath foes",
        baseHP = 115,
        baseSpeed = 5,
        basePower = 8
    )
}
