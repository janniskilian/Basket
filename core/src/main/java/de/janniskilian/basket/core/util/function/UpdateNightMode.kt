package de.janniskilian.basket.core.util.function

import androidx.appcompat.app.AppCompatDelegate

fun setDayNightMode(systemDayNightMode: Boolean, dayNightMode: Boolean) {
    val mode = when {
        systemDayNightMode -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        dayNightMode -> AppCompatDelegate.MODE_NIGHT_YES
        else -> AppCompatDelegate.MODE_NIGHT_NO
    }

    AppCompatDelegate.setDefaultNightMode(mode)
}
