package de.janniskilian.basket.feature.lists.createlist

import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.function.createTintColorFilter
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.color_item.view.*

class ColorsAdapter : ListAdapter<ColorsAdapter.Item, ColorsAdapter.ViewHolder>(
    GenericDiffItemCallback { oldItem, newItem ->
        oldItem.color == newItem.color
    }
) {

    var clickListener: ((color: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.color_item,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.itemView) {
            (backgroundView.background as LayerDrawable)
                .getDrawable(0)
                .colorFilter = createTintColorFilter(item.color)
            backgroundView.isSelected = item.selected
            checkedIcon.isVisible = item.selected

            setOnClickListener { clickListener?.invoke(item.color) }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    data class Item(
        val color: Int,
        val selected: Boolean
    )
}
