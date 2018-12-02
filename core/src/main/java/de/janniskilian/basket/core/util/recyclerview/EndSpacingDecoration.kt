package de.janniskilian.basket.core.util.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.extension.extern.getRealViewPosition

class EndSpacingDecoration(
	private val space: Int,
	private val direction: Int,
	private val where: Position
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

		if (firstRow
			&& (where == Position.START
				|| where == Position.START_AND_END)
		) {
			if (direction == RecyclerView.VERTICAL) {
				outRect.top = space
			} else if (direction == RecyclerView.HORIZONTAL) {
				outRect.left = space
			}
		}

		if (lastRow
			&& (where == Position.END
				|| where == Position.START_AND_END)
		) {
			if (direction == RecyclerView.VERTICAL) {
				outRect.bottom = space
			} else if (direction == RecyclerView.HORIZONTAL) {
				outRect.right = space
			}
		}
	}

	enum class Position {
		START,
		END,
		START_AND_END
	}
}


