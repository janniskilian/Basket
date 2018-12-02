package de.janniskilian.basket.feature.articles.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.util.recyclerview.GenericDiffItemCallback
import de.janniskilian.basket.feature.articles.R
import kotlinx.android.synthetic.main.article_item.view.*

class ArticlesAdapter : ListAdapter<Article, ArticlesAdapter.ViewHolder>(
	GenericDiffItemCallback { oldItem, newItem ->
		oldItem.id == newItem.id
	}
) {

	var clickListener: ((position: Int) -> Unit)? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ViewHolder(
			LayoutInflater
				.from(parent.context)
				.inflate(
					R.layout.article_item,
					parent,
					false
				)
		)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)

		with(holder.itemView) {
			setOnClickListener {
				clickListener?.invoke(holder.adapterPosition)
			}

			articleName.text = item.name
			categoryName.text = item.category?.name ?: context.getString(R.string.category_default)
		}
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
