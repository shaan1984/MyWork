package com.elemental.powers

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elemental.powers.data.ElementType
import com.elemental.powers.data.MoveType
import com.elemental.powers.databinding.ActivityBattleBinding
import com.elemental.powers.engine.BattleEngine
import com.elemental.powers.engine.FighterFactory

class BattleActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PLAYER_ELEMENT = "player_element"
    }

    private lateinit var binding: ActivityBattleBinding
    private lateinit var engine: BattleEngine
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBattleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBattle()
        setupMoveButtons()
        refreshUI()

        val p = engine.player
        val e = engine.enemy
        logLine("⚔️  Battle Start!")
        logLine("${p.element.symbol} ${p.name}  VS  ${e.element.symbol} ${e.name}")
        logLine(matchupHint(p.element, e.element))
    }

    private fun initBattle() {
        val playerEl = ElementType.valueOf(
            intent.getStringExtra(EXTRA_PLAYER_ELEMENT) ?: ElementType.FIRE.name
        )
        val enemyEl = ElementType.values().filter { it != playerEl }.random()
        engine = BattleEngine(FighterFactory.createFighter(playerEl), FighterFactory.createFighter(enemyEl))

        binding.playerPanel.setBackgroundColor(tint(Color.parseColor(playerEl.colorHex), 0.25f))
        binding.enemyPanel.setBackgroundColor(tint(Color.parseColor(enemyEl.colorHex), 0.25f))
    }

    private fun setupMoveButtons() {
        val btns = listOf(binding.btnMove1, binding.btnMove2, binding.btnMove3, binding.btnMove4)
        engine.player.moves.forEachIndexed { i, move ->
            btns[i].text = when (move.moveType) {
                MoveType.DEFEND -> "${move.name}\n[DEFEND]"
                MoveType.HEAL   -> "${move.name}\n[HEAL]"
                else            -> "${move.name}\n(${move.power} PWR)"
            }
            btns[i].setOnClickListener { onPlayerMove(i) }
        }
    }

    private fun onPlayerMove(index: Int) {
        movesEnabled(false)
        val res = engine.executePlayerTurn(index)
        logLine("\n▶ YOU: ${res.log}")
        refreshUI()

        if (res.isBattleOver) {
            handler.postDelayed({ showResult(res.playerWon == true) }, 800)
            return
        }

        handler.postDelayed({
            val er = engine.executeEnemyTurn()
            logLine("\n▶ ENEMY: ${er.log}")
            refreshUI()
            if (er.isBattleOver) {
                handler.postDelayed({ showResult(er.playerWon == true) }, 800)
            } else {
                movesEnabled(true)
            }
        }, 1200)
    }

    private fun refreshUI() {
        val p = engine.player
        val e = engine.enemy

        binding.tvPlayerSprite.text = p.element.symbol
        binding.tvPlayerName.text   = p.name
        binding.tvPlayerHp.text     = "HP  ${p.currentHP} / ${p.maxHP}"
        binding.playerHpBar.max      = p.maxHP
        binding.playerHpBar.progress = p.currentHP
        binding.playerHpBar.progressTintList = ColorStateList.valueOf(hpColor(p.hpPercentage))
        binding.tvPlayerStatus.text = p.statusEffect?.let { "${it.type.displayName} (${it.turnsRemaining})" } ?: ""

        binding.tvEnemySprite.text = e.element.symbol
        binding.tvEnemyName.text   = e.name
        binding.tvEnemyHp.text     = "HP  ${e.currentHP} / ${e.maxHP}"
        binding.enemyHpBar.max      = e.maxHP
        binding.enemyHpBar.progress = e.currentHP
        binding.enemyHpBar.progressTintList = ColorStateList.valueOf(hpColor(e.hpPercentage))
        binding.tvEnemyStatus.text = e.statusEffect?.let { "${it.type.displayName} (${it.turnsRemaining})" } ?: ""
    }

    private fun logLine(text: String) {
        val current = binding.tvBattleLog.text
        binding.tvBattleLog.text = if (current.isEmpty()) text else "$current\n$text"
        binding.scrollBattleLog.post { binding.scrollBattleLog.fullScroll(View.FOCUS_DOWN) }
    }

    private fun movesEnabled(on: Boolean) {
        listOf(binding.btnMove1, binding.btnMove2, binding.btnMove3, binding.btnMove4)
            .forEach { it.isEnabled = on }
    }

    private fun showResult(playerWon: Boolean) {
        val title = if (playerWon) "⚡ Victory!" else "💀 Defeat!"
        val msg = if (playerWon)
            "You defeated ${engine.enemy.name}!\nYour elemental mastery is supreme!"
        else
            "${engine.enemy.name} has defeated you...\nChoose a new element and fight again!"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Play Again") { _, _ ->
                finish()
                startActivity(Intent(this, CharacterSelectActivity::class.java))
            }
            .setNegativeButton("Main Menu") { _, _ ->
                finishAffinity()
                startActivity(Intent(this, MainActivity::class.java))
            }
            .setCancelable(false)
            .show()
    }

    private fun hpColor(pct: Float): Int = when {
        pct > 0.50f -> Color.parseColor("#4CAF50")
        pct > 0.25f -> Color.parseColor("#FFC107")
        else        -> Color.parseColor("#F44336")
    }

    private fun tint(color: Int, alpha: Float): Int =
        Color.argb((255 * alpha).toInt(), Color.red(color), Color.green(color), Color.blue(color))

    private fun matchupHint(player: ElementType, enemy: ElementType): String {
        val advantages = mapOf(
            ElementType.FIRE to setOf(ElementType.ICE, ElementType.EARTH),
            ElementType.WATER to setOf(ElementType.FIRE, ElementType.HEAT),
            ElementType.EARTH to setOf(ElementType.EARTHQUAKE, ElementType.TECH),
            ElementType.ICE to setOf(ElementType.WATER, ElementType.HEAT),
            ElementType.LIFE to setOf(ElementType.WATER, ElementType.EARTH),
            ElementType.TECH to setOf(ElementType.LIFE, ElementType.ICE),
            ElementType.HEAT to setOf(ElementType.ICE, ElementType.LIFE),
            ElementType.EARTHQUAKE to setOf(ElementType.FIRE, ElementType.HEAT)
        )
        return when {
            advantages[player]?.contains(enemy) == true ->
                "✅ ${player.displayName} is strong against ${enemy.displayName}!"
            advantages[enemy]?.contains(player) == true ->
                "⚠️  ${enemy.displayName} has the type advantage — be careful!"
            else ->
                "⚖️  Neutral matchup. Skill decides the winner!"
        }
    }
}
