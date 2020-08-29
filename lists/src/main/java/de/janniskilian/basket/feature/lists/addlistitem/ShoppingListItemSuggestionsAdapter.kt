package de.janniskilian.basket.feature.lists.addlistitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.extension.extern.setSelectedImageState
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.shopping_list_item_suggestion_item.view.*

class ShoppingListItemSuggestionsAdapter :
    ListAdapter<ShoppingListItemSuggestionsAdapter.Item, ShoppingListItemSuggestionsAdapter.ViewHolder>(
        GenericDiffItemCallback { oldItem, newItem ->
            oldItem.item.article.id == newItem.item.article.id
        }
    ) {

    var clickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.shopping_list_item_suggestion_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.itemView) {
            setOnClickListener {
                clickListener?.invoke(holder.adapterPosition)
            }

            name.text = formatItemSuggestion(item.item)

            val iconRes = when {
                item.item.existingListItem -> R.drawable.asl_addremove
                item.item.existingArticle -> R.drawable.asl_addremove
                else -> R.drawable.ic_create_24
            }
            icon.setImageResource(iconRes)
            icon.setSelectedImageState(item.item.existingListItem)
        }
    }

    private fun formatItemSuggestion(suggestion: ShoppingListItemSuggestion): String {
        val builder = StringBuilder(suggestion.article.name)

        if (suggestion.quantity.isNotBlank()) {
            builder.append(", ${suggestion.quantity}")
        }

        return builder.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    data class Item(
        val item: ShoppingListItemSuggestion
    )
}
