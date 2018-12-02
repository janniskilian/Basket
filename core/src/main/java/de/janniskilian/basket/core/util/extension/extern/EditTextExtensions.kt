package de.janniskilian.basket.core.util.extension.extern

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.onDone(block: () -> Unit) {
	setOnEditorActionListener { _, actionId, _ ->
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			block()
			true
		} else {
			false
		}
	}
}

fun EditText.onTextChanged(block: (text: String) -> Unit) {
	addTextChangedListener(object : TextWatcher {
		override fun afterTextChanged(s: Editable) {
		}

		override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
		}

		override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
			block(s.toString())
		}
	})
}
