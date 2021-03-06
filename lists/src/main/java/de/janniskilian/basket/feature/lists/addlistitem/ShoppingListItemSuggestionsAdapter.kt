package de.janniskilian.basket.feature.lists.addlistitem

import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.android.view.layoutInflater
import de.janniskilian.basket.core.util.android.view.setSelectedImageState
import de.janniskilian.basket.core.util.android.view.recyclerview.GenericDiffItemCallback
import de.janniskilian.basket.feature.lists.R
import de.janniskilian.basket.feature.lists.databinding.ShoppingListItemSuggestionItemBinding

class ShoppingListItemSuggestionsAdapter :
    ListAdapter<ShoppingListItemSuggestionsAdapter.Item, ShoppingListItemSuggestionsAdapter.ViewHolder>(
        GenericDiffItemCallback { oldItem, newItem ->
            oldItem.item.article.id == newItem.item.article.id
        }
    ) {

    var clickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ShoppingListItemSuggestionItemBinding.inflate(
                parent.layoutInflater,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            root.setOnClickListener {
                clickListener?.invoke(holder.adapterPosition)
            }

            name.text = formatItemSuggestion(item.item)

            @DrawableRes val iconRes: Int
            @StringRes val contentDescriptionRes: Int
            when {
                item.item.isExistingListItem -> {
                    iconRes = R.drawable.asl_addremove
                    contentDescriptionRes = R.string.existing_list_item_icon_desc
                }
                item.item.isExistingArticle -> {
                    iconRes = R.drawable.asl_addremove
                    contentDescriptionRes = R.string.existing_article_icon_desc
                }
                else -> {
                    iconRes = R.drawable.ic_create_24
                    contentDescriptionRes = R.string.create_new_article_icon_desc
                }
            }
            icon.setImageResource(iconRes)
            icon.setSelectedImageState(item.item.isExistingListItem)
            icon.contentDescription = icon.context.getString(contentDescriptionRes)
        }
    }

    private fun formatItemSuggestion(suggestion: ShoppingListItemSuggestion): String {
        val builder = StringBuilder(suggestion.article.name)

        if (suggestion.quantity.isNotBlank()) {
            builder.append(", ${suggestion.quantity}")
        }

        return builder.toString()
    }

    class ViewHolder(val binding: ShoppingListItemSuggestionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    data class Item(
        val item: ShoppingListItemSuggestion
    )
}
