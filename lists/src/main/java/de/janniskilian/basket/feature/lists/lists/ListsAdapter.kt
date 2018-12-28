package de.janniskilian.basket.feature.lists.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.util.extension.extern.createStrokeColor
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.shopping_list_item.view.*

class ListsAdapter : ListAdapter<ShoppingList, ListsAdapter.ViewHolder>(
	GenericDiffItemCallback { oldItem, newItem -> oldItem.id == newItem.id }
) {

	var itemClickListener: ((position: Int) -> Unit)? = null
	var moreButtonClickListener: ((position: Int) -> Unit)? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ViewHolder(
			LayoutInflater
				.from(parent.context)
				.inflate(
					R.layout.shopping_list_item,
					parent,
					false
				)
		)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)

        with(holder.itemView as MaterialCardView) {
			setOnClickListener { itemClickListener?.invoke(holder.adapterPosition) }
			moreButton.setOnClickListener {
				moreButtonClickListener?.invoke(holder.adapterPosition)
			}

			setCardBackgroundColor(item.color)
            strokeColor = item.color.createStrokeColor()

			nameTextView.text = item.name
			itemsTotalTextView.text = resources.getQuantityString(
				R.plurals.shopping_list_items_total,
				item.items.size,
				item.items.size
			)
			itemsCheckedTextView.text = resources.getQuantityString(
				R.plurals.shopping_list_items_checked,
				item.checkedItemCount,
				item.checkedItemCount
			)
		}
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
