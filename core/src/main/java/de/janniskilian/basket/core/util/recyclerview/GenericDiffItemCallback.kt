package de.janniskilian.basket.core.util.recyclerview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * @param T Has to implement equals().
 * @property areItemsIdentical Has to return true if two given items are identical.
 */
class GenericDiffItemCallback<T>(
    private val areItemsIdentical: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsIdentical(oldItem, newItem)

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem
}
