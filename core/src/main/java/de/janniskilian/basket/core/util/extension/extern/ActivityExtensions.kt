package de.janniskilian.basket.core.util.extension.extern

import android.app.Activity
import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

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
