package de.janniskilian.basket.core.util.android

import android.content.Context
import android.content.res.Configuration

val Context.hasHardwareKeyboard
    get() = resources.configuration.keyboard != Configuration.KEYBOARD_NOKEYS
