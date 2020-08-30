package de.janniskilian.basket.core.util.extension.extern

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager

val Activity.contentView: ViewGroup
    get() = findViewById(android.R.id.content)

fun Activity.showKeyboard(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        view.windowInsetsController?.show(WindowInsets.Type.ime())
        view.requestFocus()
    } else {
        inputMethodManager.showSoftInput(
            view,
            InputMethodManager.SHOW_IMPLICIT
        )
    }
}

fun Activity.hideKeyboard() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        contentView.windowInsetsController?.hide(WindowInsets.Type.ime())
    } else {
        inputMethodManager.hideSoftInputFromWindow(
            contentView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}
