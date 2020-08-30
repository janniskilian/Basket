package de.janniskilian.basket.core.util.extension.extern

import android.os.Build
import android.view.WindowInsets
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doOnTextChanged

inline fun EditText.onDone(crossinline action: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            action()
            true
        } else {
            false
        }
    }
}

inline fun EditText.doOnTextChanged(crossinline action: (text: String) -> Unit) {
    doOnTextChanged { text, _, _, _ -> action(text.toString()) }
}

fun EditText.toggleSoftKeyboard(isVisible: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val insetsType = WindowInsets.Type.ime()

        if (isVisible) {
            windowInsetsController?.show(insetsType)
            requestFocus()
        } else {
            windowInsetsController?.hide(insetsType)
            clearFocus()
        }
    } else {
        val inputMethodManager = context.inputMethodManager

        if (isVisible) {
            requestFocus()
            inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        } else {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            clearFocus()
        }
    }
}
