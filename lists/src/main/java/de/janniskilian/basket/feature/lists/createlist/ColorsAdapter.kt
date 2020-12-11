package de.janniskilian.basket.feature.lists.createlist

import android.graphics.drawable.LayerDrawable
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.android.view.layoutInflater
import de.janniskilian.basket.core.util.android.createTintColorFilter
import de.janniskilian.basket.core.util.android.view.recyclerview.GenericDiffItemCallback
import de.janniskilian.basket.feature.lists.databinding.ColorItemBinding

class ColorsAdapter : ListAdapter<ColorsAdapter.Item, ColorsAdapter.ViewHolder>(
    GenericDiffItemCallback { oldItem, newItem ->
        oldItem.color == newItem.color
    }
) {

    var itemClickListener: ((color: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ColorItemBinding.inflate(parent.layoutInflater, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            root.setOnClickListener { itemClickListener?.invoke(item.color) }

            (backgroundView.background as LayerDrawable)
                .getDrawable(0)
                .colorFilter = createTintColorFilter(item.color)
            backgroundView.isSelected = item.isSelected
            checkedIcon.isVisible = item.isSelected
        }
    }

    class ViewHolder(val binding: ColorItemBinding) : RecyclerView.ViewHolder(binding.root)

    data class Item(
        val color: Int,
        val isSelected: Boolean
    )
}
