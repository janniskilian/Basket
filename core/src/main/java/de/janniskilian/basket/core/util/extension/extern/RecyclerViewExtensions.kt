package de.janniskilian.basket.core.util.extension.extern

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.getRealViewPosition(view: View): Int =
	if ((view.layoutParams as RecyclerView.LayoutParams).isItemRemoved) {
		getChildViewHolder(view).oldPosition
	} else {
		getChildAdapterPosition(view)
	}
