package de.janniskilian.basket.core.util.android.view.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EndSpacingDecoration(
    private val startSpacing: Int,
    private val endSpacing: Int,
    private val direction: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter = parent.adapter ?: return

        val position = parent.getRealViewPosition(view)

        val firstRow = position == 0
        val lastRow = position == adapter.itemCount - 1

        if (firstRow) {
            if (direction == RecyclerView.VERTICAL) {
                outRect.top = startSpacing
            } else if (direction == RecyclerView.HORIZONTAL) {
                outRect.left = startSpacing
            }
        }

        if (lastRow) {
            if (direction == RecyclerView.VERTICAL) {
                outRect.bottom = endSpacing
            } else if (direction == RecyclerView.HORIZONTAL) {
                outRect.right = endSpacing
            }
        }
    }
}


