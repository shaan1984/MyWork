package com.elemental.powers.data

enum class MoveType { ATTACK, DEFEND, HEAL }

data class Move(
    val name: String,
    val description: String,
    val power: Int,
    val element: ElementType,
    val moveType: MoveType,
    val statusEffect: StatusType? = null,
    val statusChance: Float = 0f,
    val selfHeal: Int = 0,
    val isDefensive: Boolean = false,
    val defenseBonus: Int = 0
)
