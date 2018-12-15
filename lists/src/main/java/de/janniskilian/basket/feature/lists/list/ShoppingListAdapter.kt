package de.janniskilian.basket.feature.lists.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.extension.extern.bindDimen
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.shopping_list_article_item.view.*

class ShoppingListAdapter(context: Context) :
	ListAdapter<ShoppingListAdapter.Item, ShoppingListAdapter.ViewHolder>(
		GenericDiffItemCallback { oldItem, newItem ->
			oldItem.id == newItem.id
		}
	) {

	private val categoryItemMargin by bindDimen(context, R.dimen.four)

	var listItemClickListener: ((shoppingListItem: ShoppingListItem) -> Unit)? = null
	var editButtonClickListener: ((shoppingListItem: ShoppingListItem) -> Unit)? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutRes = when (viewType) {
			VIEW_TYPE_SHOPPING_LIST_ITEM -> R.layout.shopping_list_article_item
			else -> R.layout.shopping_list_category_item
		}

		return ViewHolder(
			LayoutInflater
				.from(parent.context)
				.inflate(layoutRes, parent, false)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		when (val item = getItem(position)) {
			is Item.ListItem -> bindArticleItem(holder, item)
			is Item.Group -> bindCategoryItem(holder, item)
		}
	}

	override fun getItemViewType(position: Int): Int =
		when (getItem(position)) {
			is Item.ListItem -> VIEW_TYPE_SHOPPING_LIST_ITEM
			is Item.Group -> VIEW_TYPE_CATEGORY
		}

	private fun bindArticleItem(holder: ViewHolder, item: Item.ListItem) {
		with(holder.itemView) {
			val shoppingListItem = item.shoppingListItem

			setOnClickListener {
				listItemClickListener?.invoke(shoppingListItem)
			}
			checkbox.setOnCheckedChangeListener { _, isChecked ->
				if (isChecked != shoppingListItem.checked) {
					listItemClickListener?.invoke(shoppingListItem)
				}
			}
			infoButton.setOnClickListener {
				editButtonClickListener?.invoke(shoppingListItem)
			}

			checkbox.isChecked = shoppingListItem.checked
			name.text = shoppingListItem.article.name
			quantity.isVisible = shoppingListItem.quantity.isNotBlank()
			quantity.text = shoppingListItem.quantity
		}
	}

	private fun bindCategoryItem(holder: ViewHolder, item: Item.Group) {
		(holder.itemView as TextView).text = item.name

		val topMargin = if (item.atTop) {
			0
		} else {
			categoryItemMargin
		}
		holder.itemView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
			updateMargins(top = topMargin)
		}
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

	sealed class Item(val id: String) {

		data class ListItem(
			val shoppingListItem: ShoppingListItem
		) : Item(LIST_ITEM_ID_PREFIX + shoppingListItem.id)

		data class Group(
			val groupId: Long,
			val name: String,
			val atTop: Boolean
		) : Item(GROUP_ID_PREFIX + groupId)

		companion object {

			private const val LIST_ITEM_ID_PREFIX = "LIST_ITEM"
			private const val GROUP_ID_PREFIX = "GROUP"
		}
	}

	companion object {

		private const val VIEW_TYPE_SHOPPING_LIST_ITEM = 1
		private const val VIEW_TYPE_CATEGORY = 2
		private const val VIEW_TYPE_SPACE = 3
	}
}