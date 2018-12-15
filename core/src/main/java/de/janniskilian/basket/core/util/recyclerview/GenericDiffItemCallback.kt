package de.janniskilian.basket.core.util.recyclerview

import androidx.recyclerview.widget.DiffUtil

class GenericDiffItemCallback<T>(
	private val areItemsIdentical: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {

	override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
		areItemsIdentical(oldItem, newItem)

	override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
		oldItem == newItem
}