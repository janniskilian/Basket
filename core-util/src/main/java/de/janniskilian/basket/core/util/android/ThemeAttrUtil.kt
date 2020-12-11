package de.janniskilian.basket.core.util.android

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.use

@ColorInt
fun Context.getThemeColor(@AttrRes resId: Int): Int =
    getThemeValue(resId) {
        getColor(0, Color.MAGENTA)
    }

fun Context.getThemeResource(@AttrRes resId: Int): Int =
    getThemeValue(resId) {
        getResourceId(0, 0)
    }

fun Context.getThemeDimen(@AttrRes resId: Int): Int =
    getThemeValue(resId) {
        getDimensionPixelSize(0, 0)
    }

inline fun Context.getThemeValue(@AttrRes resId: Int, block: TypedArray.() -> Int): Int =
    obtainStyledAttributes(intArrayOf(resId)).use {
        it.block()
    }
