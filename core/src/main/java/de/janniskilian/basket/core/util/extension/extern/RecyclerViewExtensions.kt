package de.janniskilian.basket.core.util.extension.extern

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.getRealViewPosition(view: View): Int =
    if ((view.layoutParams as RecyclerView.LayoutParams).isItemRemoved) {
        getChildViewHolder(view).oldPosition
    } else {
        getChildAdapterPosition(view)
    }

fun RecyclerView.centerItem(
    adapterPosition: Int,
    orientation: Int,
    isSmoothScroll: Boolean = true
) {
    if (adapterPosition == -1) return

    val holder = findViewHolderForAdapterPosition(adapterPosition)
    if (hasPendingAdapterUpdates()
        || holder == null
        || !holder.itemView.isLaidOut
    ) {
        scrollToPosition(adapterPosition)
        post {
            centerItem(adapterPosition, orientation, isSmoothScroll)
        }
    } else {
        val scrollX: Int
        val scrollY: Int
        if (orientation == RecyclerView.VERTICAL) {
            scrollX = 0
            scrollY = -(height - holder.itemView.height) / 2 + holder.itemView.top
        } else {
            scrollX = -(width - holder.itemView.width) / 2 + holder.itemView.left
            scrollY = 0
        }

        if (isSmoothScroll) {
            smoothScrollBy(scrollX, scrollY)
        } else {
            scrollBy(scrollX, scrollY)
        }
    }
}
