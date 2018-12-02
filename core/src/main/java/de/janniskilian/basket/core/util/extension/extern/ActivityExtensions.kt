package de.janniskilian.basket.core.util.extension.extern

import android.app.Activity
import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes

fun Activity.bindDimen(@DimenRes res: Int): Lazy<Float> =
	lazy { resources.getDimension(res) }

fun Activity.bindDimenPixel(@DimenRes res: Int): Lazy<Int> =
	lazy { resources.getDimensionPixelSize(res) }

fun Activity.bindInt(@IntegerRes res: Int): Lazy<Int> =
	lazy { resources.getInteger(res) }

fun Activity.bindLong(@IntegerRes res: Int): Lazy<Long> =
	lazy { resources.getInteger(res).toLong() }

val Activity.contentView: ViewGroup
	get() = findViewById(android.R.id.content)

fun Activity.showKeyboard(view: View) {
	inputMethodManager.showSoftInput(
		view,
		InputMethodManager.SHOW_IMPLICIT
	)
}

fun Activity.hideKeyboard() {
	inputMethodManager.hideSoftInputFromWindow(
		contentView.windowToken,
		InputMethodManager.HIDE_NOT_ALWAYS
	)
}

val Activity.hasHardwareKeyboard
	get() = resources.configuration.keyboard != Configuration.KEYBOARD_NOKEYS
