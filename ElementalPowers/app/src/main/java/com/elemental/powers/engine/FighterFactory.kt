package com.elemental.powers.engine

import com.elemental.powers.data.*

object FighterFactory {

    fun createFighter(element: ElementType): Fighter = Fighter(
        name = element.displayName,
        element = element,
        maxHP = element.baseHP,
        moves = buildMoves(element)
    )

    private fun buildMoves(element: ElementType): List<Move> = when (element) {
        ElementType.FIRE -> listOf(
            Move("Flame Strike",  "A fierce fire slash",              30, element, MoveType.ATTACK),
            Move("Inferno",       "Overwhelming wall of fire",        52, element, MoveType.ATTACK),
            Move("Ember Burst",   "Rapid burst of burning embers",    24, element, MoveType.ATTACK,
                statusEffect = StatusType.BURN, statusChance = 0.40f),
            Move("Heat Surge",    "A burning surge that lingers",     36, element, MoveType.ATTACK,
                statusEffect = StatusType.BURN, statusChance = 0.60f)
        )
        ElementType.WATER -> listOf(
            Move("Tidal Surge",   "A crushing ocean wave",            28, element, MoveType.ATTACK),
            Move("Flood",         "Unleash a devastating flood",      50, element, MoveType.ATTACK),
            Move("Steam Cloud",   "Scalding steam weakens the foe",   22, element, MoveType.ATTACK,
                statusEffect = StatusType.WEAKEN, statusChance = 0.45f),
            Move("Aqua Jet",      "High-speed water blast",           38, element, MoveType.ATTACK)
        )
        ElementType.EARTH -> listOf(
            Move("Rock Throw",    "Hurl massive boulders",            32, element, MoveType.ATTACK),
            Move("Landslide",     "Bury the foe in crashing earth",   54, element, MoveType.ATTACK),
            Move("Stone Wall",    "Raise a protective stone barrier",  0, element, MoveType.DEFEND,
                isDefensive = true, defenseBonus = 22),
            Move("Seismic Pound", "Shaking strike that weakens",      40, element, MoveType.ATTACK,
                statusEffect = StatusType.WEAKEN, statusChance = 0.40f)
        )
        ElementType.ICE -> listOf(
            Move("Frost Bite",    "A freezing bite attack",           28, element, MoveType.ATTACK,
                statusEffect = StatusType.FREEZE, statusChance = 0.30f),
            Move("Blizzard",      "A devastating ice storm",          48, element, MoveType.ATTACK),
            Move("Ice Spike",     "Sharp ice projectiles",            22, element, MoveType.ATTACK,
                statusEffect = StatusType.FREEZE, statusChance = 0.40f),
            Move("Frozen Tomb",   "Encase the enemy in solid ice",    36, element, MoveType.ATTACK,
                statusEffect = StatusType.FREEZE, statusChance = 0.55f)
        )
        ElementType.LIFE -> listOf(
            Move("Vine Whip",       "Lash out with living vines",     26, element, MoveType.ATTACK),
            Move("Nature's Wrath",  "Channel raw nature's fury",      46, element, MoveType.ATTACK),
            Move("Healing Touch",   "Restore HP with life energy",     0, element, MoveType.HEAL,
                selfHeal = 35),
            Move("Overgrowth",      "Burst of growth — deals & heals",34, element, MoveType.ATTACK,
                selfHeal = 15)
        )
        ElementType.TECH -> listOf(
            Move("Laser Beam",      "Precise focused energy beam",    34, element, MoveType.ATTACK),
            Move("EMP Blast",       "Electromagnetic pulse attack",   50, element, MoveType.ATTACK,
                statusEffect = StatusType.WEAKEN, statusChance = 0.50f),
            Move("Shield Drone",    "Deploy defensive nanodrones",     0, element, MoveType.DEFEND,
                isDefensive = true, defenseBonus = 20),
            Move("Circuit Overload","Overload with raw electric power",42, element, MoveType.ATTACK)
        )
        ElementType.HEAT -> listOf(
            Move("Scorch",        "Intense concentrated heat",        30, element, MoveType.ATTACK,
                statusEffect = StatusType.BURN, statusChance = 0.35f),
            Move("Solar Flare",   "Blinding solar energy blast",      50, element, MoveType.ATTACK),
            Move("Combustion",    "Explosive combustion burst",       36, element, MoveType.ATTACK,
                statusEffect = StatusType.BURN, statusChance = 0.55f),
            Move("Magma Burst",   "Molten rock eruption attack",      44, element, MoveType.ATTACK)
        )
        ElementType.EARTHQUAKE -> listOf(
            Move("Ground Shake",    "Violent seismic tremor",         30, element, MoveType.ATTACK),
            Move("Seismic Wave",    "Devastating seismic energy",     50, element, MoveType.ATTACK),
            Move("Rock Avalanche",  "Crushing avalanche of rock",     36, element, MoveType.ATTACK,
                statusEffect = StatusType.WEAKEN, statusChance = 0.40f),
            Move("Fault Line",      "Tear the ground apart",          42, element, MoveType.ATTACK,
                statusEffect = StatusType.WEAKEN, statusChance = 0.30f)
        )
    }
}
