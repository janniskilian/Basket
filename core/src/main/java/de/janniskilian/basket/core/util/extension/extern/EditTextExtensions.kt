package de.janniskilian.basket.core.util.extension.extern

import android.view.inputmethod.EditorInfo
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
