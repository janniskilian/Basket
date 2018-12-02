package de.janniskilian.basket.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback

class CategoriesAdapter : ListAdapter<Category?, CategoriesAdapter.ViewHolder>(
	GenericDiffItemCallback { oldItem, newItem ->
		oldItem?.id == newItem?.id
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

		with(holder.itemView as TextView) {
			setOnClickListener { clickListener?.invoke(item) }
			text = item?.name ?: context.getString(R.string.category_default)
		}
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
