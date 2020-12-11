package de.janniskilian.basket.core.util.android

import android.view.Window
import android.view.WindowManager

fun Window.keepScreenOn(value: Boolean) {
    if (value) {
        addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    } else {
        clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}
