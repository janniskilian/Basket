package de.janniskilian.basket.core.util.function

import androidx.appcompat.app.AppCompatDelegate

fun setDayNightMode(isSystemDayNightMode: Boolean, isDayNightMode: Boolean) {
    val mode = when {
        isSystemDayNightMode -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        isDayNightMode -> AppCompatDelegate.MODE_NIGHT_YES
        else -> AppCompatDelegate.MODE_NIGHT_NO
    }

    AppCompatDelegate.setDefaultNightMode(mode)
}
