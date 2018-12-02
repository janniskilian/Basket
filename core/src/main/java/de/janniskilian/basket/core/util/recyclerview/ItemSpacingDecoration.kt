package de.janniskilian.basket.core.util.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.extension.extern.getRealViewPosition

class ItemSpacingDecoration(
	private val space: Int,
	private val direction: Int
) : RecyclerView.ItemDecoration() {

	override fun getItemOffsets(
		outRect: Rect,
		view: View,
		parent: RecyclerView,
		state: RecyclerView.State
	) {
		if (parent.getRealViewPosition(view) > 0) {
			if (direction == RecyclerView.VERTICAL) {
				outRect.top = space
			} else if (direction == RecyclerView.HORIZONTAL) {
				outRect.left = space
			}
		}
	}
}
