package de.janniskilian.basket.core.util.function

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.ColorInt
import de.janniskilian.basket.core.R
import de.janniskilian.basket.core.util.extension.extern.getThemeColor

@ColorInt
fun createUiListColor(context: Context, @ColorInt color: Int): Int {
    val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

    return if (mode == Configuration.UI_MODE_NIGHT_YES) {
        context.getThemeColor(R.attr.colorSurface)
    } else {
        color
    }
}
