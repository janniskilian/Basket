package de.janniskilian.basket.core.util.extension.extern

import android.content.Context
import android.view.inputmethod.InputMethodManager

val Context.inputMethodManager: InputMethodManager
	get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
