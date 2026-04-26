package com.elemental.powers.engine

import com.elemental.powers.data.ElementType

object ElementalMatrix {

    private val advantages: Map<ElementType, Set<ElementType>> = mapOf(
        ElementType.FIRE       to setOf(ElementType.ICE, ElementType.EARTH),
        ElementType.WATER      to setOf(ElementType.FIRE, ElementType.HEAT),
        ElementType.EARTH      to setOf(ElementType.EARTHQUAKE, ElementType.TECH),
        ElementType.ICE        to setOf(ElementType.WATER, ElementType.HEAT),
        ElementType.LIFE       to setOf(ElementType.WATER, ElementType.EARTH),
        ElementType.TECH       to setOf(ElementType.LIFE, ElementType.ICE),
        ElementType.HEAT       to setOf(ElementType.ICE, ElementType.LIFE),
        ElementType.EARTHQUAKE to setOf(ElementType.FIRE, ElementType.HEAT)
    )

    private val disadvantages: Map<ElementType, Set<ElementType>> = mapOf(
        ElementType.FIRE       to setOf(ElementType.WATER, ElementType.EARTHQUAKE),
        ElementType.WATER      to setOf(ElementType.LIFE, ElementType.EARTH),
        ElementType.EARTH      to setOf(ElementType.FIRE, ElementType.WATER),
        ElementType.ICE        to setOf(ElementType.FIRE, ElementType.TECH),
        ElementType.LIFE       to setOf(ElementType.HEAT, ElementType.TECH),
        ElementType.TECH       to setOf(ElementType.EARTH, ElementType.EARTHQUAKE),
        ElementType.HEAT       to setOf(ElementType.WATER, ElementType.EARTH),
        ElementType.EARTHQUAKE to setOf(ElementType.EARTH, ElementType.TECH)
    )

    fun getMultiplier(attacker: ElementType, defender: ElementType): Float = when {
        attacker == defender -> 0.5f
        advantages[attacker]?.contains(defender) == true -> 1.5f
        disadvantages[attacker]?.contains(defender) == true -> 0.65f
        else -> 1.0f
    }

    fun getEffectivenessText(multiplier: Float): String = when {
        multiplier >= 1.5f -> "Super Effective! (x1.5)"
        multiplier <= 0.5f -> "Resistant! (x0.5)"
        multiplier <= 0.65f -> "Not Very Effective... (x0.65)"
        else -> ""
    }
}
