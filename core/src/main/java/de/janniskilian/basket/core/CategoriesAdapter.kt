package de.janniskilian.basket.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback
import kotlinx.android.synthetic.main.category_item.view.*

class CategoriesAdapter : ListAdapter<CategoriesAdapter.Item, CategoriesAdapter.ViewHolder>(
    GenericDiffItemCallback { oldItem, newItem ->
        oldItem?.category?.id == newItem?.category?.id
    }
) {

    var clickListener: ((category: Category?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.category_item,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.itemView) {
            setOnClickListener { clickListener?.invoke(item.category) }

            name.text = item?.category?.name ?: context.getString(R.string.category_default)
            selectedIcon.isVisible = item.selected
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class Item(val category: Category?, val selected: Boolean = false)
}
