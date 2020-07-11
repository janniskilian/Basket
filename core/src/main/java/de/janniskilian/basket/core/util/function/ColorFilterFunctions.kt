package de.janniskilian.basket.core.util.function

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.ColorInt

fun createTintColorFilter(@ColorInt color: Int): ColorFilter =
    PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)

