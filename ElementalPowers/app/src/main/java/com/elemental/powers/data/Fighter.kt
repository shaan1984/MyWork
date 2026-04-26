package com.elemental.powers.data

enum class StatusType(val displayName: String) {
    BURN("Burn"),
    FREEZE("Freeze"),
    WEAKEN("Weaken")
}

data class StatusEffect(
    val type: StatusType,
    var turnsRemaining: Int
)

data class Fighter(
    val name: String,
    val element: ElementType,
    val maxHP: Int,
    var currentHP: Int = maxHP,
    val moves: List<Move>,
    var statusEffect: StatusEffect? = null,
    var defenseBonus: Int = 0
) {
    val isAlive: Boolean get() = currentHP > 0

    fun takeDamage(amount: Int): Int {
        val reduction = defenseBonus / 2
        val actual = maxOf(1, amount - reduction)
        currentHP = maxOf(0, currentHP - actual)
        defenseBonus = 0
        return actual
    }

    fun takeBurnDamage(amount: Int) {
        currentHP = maxOf(0, currentHP - amount)
    }

    fun heal(amount: Int) {
        currentHP = minOf(maxHP, currentHP + amount)
    }

    val hpPercentage: Float get() = currentHP.toFloat() / maxHP.toFloat()
}
