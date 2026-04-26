package com.elemental.powers.engine

import com.elemental.powers.data.*
import kotlin.random.Random

data class BattleResult(
    val log: String,
    val damageDealt: Int = 0,
    val healAmount: Int = 0,
    val effectivenessText: String = "",
    val statusApplied: StatusType? = null,
    val isDefending: Boolean = false,
    val isBattleOver: Boolean = false,
    val playerWon: Boolean? = null
)

class BattleEngine(val player: Fighter, val enemy: Fighter) {

    private val rng = Random.Default

    fun executePlayerTurn(moveIndex: Int): BattleResult =
        executeMove(player, enemy, player.moves[moveIndex])

    fun executeEnemyTurn(): BattleResult =
        executeMove(enemy, player, selectAIMove())

    private fun selectAIMove(): Move {
        val hpRatio = enemy.currentHP.toFloat() / enemy.maxHP
        if (enemy.element == ElementType.LIFE && hpRatio < 0.4f) {
            enemy.moves.find { it.moveType == MoveType.HEAL }?.let { return it }
        }
        if (hpRatio < 0.3f && rng.nextFloat() < 0.4f) {
            enemy.moves.find { it.isDefensive }?.let { return it }
        }
        val attacks = enemy.moves.filter { it.moveType == MoveType.ATTACK }
        return if (rng.nextFloat() < 0.6f) attacks.maxByOrNull { it.power }!! else attacks.random()
    }

    private fun executeMove(attacker: Fighter, defender: Fighter, move: Move): BattleResult {
        val log = StringBuilder()

        // Freeze check — 30% chance to skip
        val frozen = attacker.statusEffect?.takeIf { it.type == StatusType.FREEZE }
        if (frozen != null && rng.nextFloat() < 0.30f) {
            log.append("${attacker.name} is frozen solid and can't move!")
            tickStatus(attacker)
            return BattleResult(log = log.toString())
        }

        var damage = 0
        var healed = 0
        var statusApplied: StatusType? = null
        var effectText = ""

        when (move.moveType) {
            MoveType.ATTACK -> {
                val multiplier = ElementalMatrix.getMultiplier(move.element, defender.element)
                effectText = ElementalMatrix.getEffectivenessText(multiplier)
                val weakenMod = if (attacker.statusEffect?.type == StatusType.WEAKEN) 0.75f else 1.0f
                val raw = (move.power * multiplier * weakenMod).toInt()
                val variance = maxOf(1, (raw * 0.10f).toInt())
                val final = raw + rng.nextInt(-variance, variance + 1)
                damage = defender.takeDamage(final)
                log.append("${attacker.name} used ${move.name}!")
                if (effectText.isNotEmpty()) log.append("\n$effectText")
                log.append("\nDealt $damage damage!")

                if (move.statusEffect != null && defender.statusEffect == null
                    && rng.nextFloat() < move.statusChance) {
                    defender.statusEffect = StatusEffect(move.statusEffect, 3)
                    statusApplied = move.statusEffect
                    log.append("\n${defender.name} is now ${move.statusEffect.displayName}ed!")
                }
                if (move.selfHeal > 0) {
                    attacker.heal(move.selfHeal)
                    healed = move.selfHeal
                    log.append("\n${attacker.name} recovered $healed HP!")
                }
            }

            MoveType.DEFEND -> {
                attacker.defenseBonus = move.defenseBonus
                log.append("${attacker.name} used ${move.name}!")
                log.append("\n${attacker.name} braces for impact! (+${move.defenseBonus} defense)")
            }

            MoveType.HEAL -> {
                attacker.heal(move.selfHeal)
                healed = move.selfHeal
                log.append("${attacker.name} used ${move.name}!")
                log.append("\n${attacker.name} recovered $healed HP!")
            }
        }

        tickStatus(attacker)

        val over = !player.isAlive || !enemy.isAlive
        val won: Boolean? = if (over) player.isAlive else null

        return BattleResult(
            log = log.toString(),
            damageDealt = damage,
            healAmount = healed,
            effectivenessText = effectText,
            statusApplied = statusApplied,
            isDefending = move.isDefensive,
            isBattleOver = over,
            playerWon = won
        )
    }

    private fun tickStatus(fighter: Fighter) {
        val effect = fighter.statusEffect ?: return
        if (effect.type == StatusType.BURN) {
            fighter.takeBurnDamage(5)
        }
        effect.turnsRemaining--
        if (effect.turnsRemaining <= 0) fighter.statusEffect = null
    }
}
