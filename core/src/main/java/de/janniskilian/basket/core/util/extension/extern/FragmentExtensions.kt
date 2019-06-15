package de.janniskilian.basket.core.util.extension.extern

import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
	view?.let {
		requireContext().inputMethodManager.hideSoftInputFromWindow(
			it.windowToken,
			InputMethodManager.HIDE_NOT_ALWAYS
		)
	}
}
