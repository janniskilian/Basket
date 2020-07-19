package de.janniskilian.basket.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText

class EnhancedEditText(context: Context?, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {

    var backPressedListener: (() -> Unit)? = null

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP
            && keyCode == KeyEvent.KEYCODE_BACK
        ) {
            backPressedListener?.invoke()
            return backPressedListener != null
        }

        return super.onKeyPreIme(keyCode, event)
    }
}
