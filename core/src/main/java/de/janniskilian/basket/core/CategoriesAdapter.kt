package de.janniskilian.basket.core

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.databinding.CategoryItemBinding
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.layoutInflater
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback

class CategoriesAdapter : ListAdapter<CategoriesAdapter.Item, CategoriesAdapter.ViewHolder>(
    GenericDiffItemCallback { oldItem, newItem ->
        oldItem?.category?.id == newItem?.category?.id
    }
) {

    var clickListener: ((category: Category?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CategoryItemBinding.inflate(parent.layoutInflater, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            root.setOnClickListener { clickListener?.invoke(item.category) }

            name.text = item?.category?.name ?: root.context.getString(R.string.category_default)
            checkmarkIcon.isVisible = item.isSelected
        }
    }

    class ViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    class Item(val category: Category?, val isSelected: Boolean = false)
}
