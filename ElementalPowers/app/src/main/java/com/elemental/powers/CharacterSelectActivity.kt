package com.elemental.powers

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elemental.powers.data.ElementType
import com.elemental.powers.databinding.ActivityCharacterSelectBinding
import com.elemental.powers.databinding.ItemElementCardBinding

class CharacterSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterSelectBinding
    private var selectedElement: ElementType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ElementAdapter(ElementType.values().toList()) { element ->
            selectedElement = element
            binding.btnSelectFighter.isEnabled = true
            binding.btnSelectFighter.text = "Fight as ${element.displayName}!"
        }
        binding.rvElements.layoutManager = GridLayoutManager(this, 2)
        binding.rvElements.adapter = adapter

        binding.btnSelectFighter.isEnabled = false
        binding.btnSelectFighter.setOnClickListener {
            selectedElement?.let { el ->
                startActivity(
                    Intent(this, BattleActivity::class.java)
                        .putExtra(BattleActivity.EXTRA_PLAYER_ELEMENT, el.name)
                )
            }
        }
        binding.btnBack.setOnClickListener { finish() }
    }

    inner class ElementAdapter(
        private val elements: List<ElementType>,
        private val onSelect: (ElementType) -> Unit
    ) : RecyclerView.Adapter<ElementAdapter.VH>() {

        private var selectedPos = -1

        inner class VH(val vb: ItemElementCardBinding) : RecyclerView.ViewHolder(vb.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            VH(ItemElementCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        override fun getItemCount() = elements.size

        override fun onBindViewHolder(holder: VH, position: Int) {
            val el = elements[position]
            with(holder.vb) {
                tvSymbol.text   = el.symbol
                tvName.text     = el.displayName
                tvDesc.text     = el.description
                tvStats.text    = "HP ${el.baseHP}  SPD ${el.baseSpeed}  PWR ${el.basePower}"

                val base = Color.parseColor(el.colorHex)
                cardElement.setCardBackgroundColor(base)

                val sel = holder.bindingAdapterPosition == selectedPos
                cardElement.cardElevation  = if (sel) 18f else 4f
                cardElement.strokeWidth    = if (sel) 5 else 0
                cardElement.strokeColor    = if (sel) Color.WHITE else Color.TRANSPARENT

                root.setOnClickListener {
                    val prev = selectedPos
                    selectedPos = holder.bindingAdapterPosition
                    notifyItemChanged(prev)
                    notifyItemChanged(selectedPos)
                    onSelect(el)
                }
            }
        }
    }
}
