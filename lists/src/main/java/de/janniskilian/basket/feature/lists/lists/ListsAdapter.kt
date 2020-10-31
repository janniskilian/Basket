package de.janniskilian.basket.feature.lists.lists

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.layoutInflater
import de.janniskilian.basket.core.util.extension.extern.setContainerTransformTransitionName
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback
import de.janniskilian.basket.feature.lists.R
import de.janniskilian.basket.feature.lists.databinding.ShoppingListItemBinding

class ListsAdapter : ListAdapter<ShoppingList, ListsAdapter.ViewHolder>(
    GenericDiffItemCallback { oldItem, newItem -> oldItem.id == newItem.id }
) {

    var itemClickListener: ((shoppingListId: ShoppingListId) -> Unit)? = null
    var moreButtonClickListener: ((shoppingListId: ShoppingListId) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ShoppingListItemBinding.inflate(parent.layoutInflater, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            root.setOnClickListener { itemClickListener?.invoke(item.id) }

            moreButton.setOnClickListener {
                moreButtonClickListener?.invoke(item.id)
            }

            root.setCardBackgroundColor(item.color)

            nameTextView.text = item.name
            itemsTotalTextView.text = root.resources.getQuantityString(
                R.plurals.shopping_list_items_total,
                item.items.size,
                item.items.size
            )
            itemsCheckedTextView.text = root.resources.getQuantityString(
                R.plurals.shopping_list_items_checked,
                item.checkedItemCount,
                item.checkedItemCount
            )

            root.setContainerTransformTransitionName(item.id.value)
        }
    }

    class ViewHolder(val binding: ShoppingListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
