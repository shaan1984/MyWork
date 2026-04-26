package com.elemental.powers

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elemental.powers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        binding.titleText.startAnimation(fadeIn)
        binding.subtitleText.startAnimation(fadeIn)

        binding.btnStartGame.setOnClickListener {
            startActivity(Intent(this, CharacterSelectActivity::class.java))
        }
        binding.btnHowToPlay.setOnClickListener { showHowToPlay() }
    }

    private fun showHowToPlay() {
        AlertDialog.Builder(this)
            .setTitle("How to Play")
            .setMessage(
                "ELEMENTAL POWERS — Battle Guide\n\n" +
                "⚔️  COMBAT\n" +
                "Each fighter has 4 unique moves.\n" +
                "Take turns attacking the enemy.\n" +
                "Reduce the opponent's HP to 0 to win!\n\n" +
                "🔥  ELEMENTS  (8 total)\n" +
                "Fire · Water · Earth · Ice\n" +
                "Life · Tech · Heat · Earthquake\n" +
                "Each element has strengths & weaknesses.\n" +
                "Super Effective: ×1.5 damage\n" +
                "Not Very Effective: ×0.65 damage\n\n" +
                "💡  STATUS EFFECTS\n" +
                "🔥 Burn    — 5 HP damage/turn (3 turns)\n" +
                "❄️  Freeze  — 30% skip-turn (3 turns)\n" +
                "💔  Weaken  — 25% less damage (3 turns)\n\n" +
                "🛡️  DEFENDING\n" +
                "Some moves raise your defense this turn.\n\n" +
                "💚  HEALING\n" +
                "Life element can restore HP mid-battle.\n" +
                "Overgrowth deals damage AND heals you!"
            )
            .setPositiveButton("Got it!") { _, _ -> }
            .show()
    }
}
