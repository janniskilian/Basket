package de.janniskilian.basket.core.util.extension.extern

import android.graphics.Point
import android.view.View
import android.view.ViewGroup

fun View.positionInParent(parent: ViewGroup): Point {
	var left = 0
	var top = 0
	var view: View? = this

	while (view != null && view !== parent) {
		left += view.left
		top += view.top
		view = view.parent as? View
	}

	return Point(left, top)
}
