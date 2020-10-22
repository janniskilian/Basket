package de.janniskilian.basket.feature.lists.list

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.extension.extern.layoutInflater
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback
import de.janniskilian.basket.feature.lists.R
import de.janniskilian.basket.feature.lists.databinding.ShoppingListGroupHeaderItemBinding
import de.janniskilian.basket.feature.lists.databinding.ShoppingListItemItemBinding

class ShoppingListAdapter(private val isDisplayCompact: Boolean) :
    ListAdapter<ShoppingListAdapter.Item, RecyclerView.ViewHolder>(
        GenericDiffItemCallback { oldItem, newItem ->
            oldItem.id == newItem.id
        }
    ) {

    var listItemClickListener: ((shoppingListItem: ShoppingListItem) -> Unit)? = null
    var editButtonClickListener: ((shoppingListItem: ShoppingListItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_LIST_ITEM -> ListItemViewHolder(
                ShoppingListItemItemBinding.inflate(
                    parent.layoutInflater,
                    parent,
                    false
                )
            )

            VIEW_TYPE_GROUP_HEADER -> GroupHeaderViewHolder(
                ShoppingListGroupHeaderItemBinding.inflate(
                    parent.layoutInflater,
                    parent,
                    false
                )
            )

            else -> throw NoWhenBranchMatchedException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Item.ListItem -> bindListItemItem(holder as ListItemViewHolder, item)
            is Item.GroupHeader -> bindGroupHeaderItem(holder as GroupHeaderViewHolder, item)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Item.ListItem -> VIEW_TYPE_LIST_ITEM
            is Item.GroupHeader -> VIEW_TYPE_GROUP_HEADER
        }

    private fun bindListItemItem(holder: ListItemViewHolder, item: Item.ListItem) {
        with(holder.binding) {
            val shoppingListItem = item.shoppingListItem

            root.setOnClickListener {
                listItemClickListener?.invoke(shoppingListItem)
            }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked != shoppingListItem.isChecked) {
                    listItemClickListener?.invoke(shoppingListItem)
                }
            }
            infoButton.setOnClickListener {
                editButtonClickListener?.invoke(shoppingListItem)
            }

            checkbox.isChecked = shoppingListItem.isChecked
            name.text = shoppingListItem.article.name
            quantity.isVisible = shoppingListItem.quantity.isNotBlank()
            quantity.text = shoppingListItem.quantity
            comment.isVisible = shoppingListItem.comment.isNotBlank()
            comment.text = shoppingListItem.comment
        }
    }

    private fun bindGroupHeaderItem(holder: GroupHeaderViewHolder, item: Item.GroupHeader) {
        holder.binding.root.text = item.name

        val topMargin = if (item.isAtTop) {
            0
        } else {
            val resId = if (isDisplayCompact) {
                R.dimen.list_spacing_compact
            } else {
                R.dimen.list_spacing_default
            }
            holder.itemView.context.resources.getDimensionPixelSize(resId)
        }
        holder.binding.root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            updateMargins(top = topMargin)
        }
    }

    class ListItemViewHolder(
        val binding: ShoppingListItemItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class GroupHeaderViewHolder(
        val binding: ShoppingListGroupHeaderItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    sealed class Item(val id: String) {

        data class ListItem(
            val shoppingListItem: ShoppingListItem
        ) : Item(LIST_ITEM_ID_PREFIX + shoppingListItem.id)

        data class GroupHeader(
            val groupId: Long,
            val name: String,
            val isAtTop: Boolean
        ) : Item(GROUP_ID_PREFIX + groupId)

        companion object {

            private const val LIST_ITEM_ID_PREFIX = "LIST_ITEM"
            private const val GROUP_ID_PREFIX = "GROUP"
        }
    }

    companion object {

        const val VIEW_TYPE_LIST_ITEM = 1
        const val VIEW_TYPE_GROUP_HEADER = 2
    }
}
