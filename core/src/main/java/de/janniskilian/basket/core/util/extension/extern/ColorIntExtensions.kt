@file:Suppress("WRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET_ON_TYPE")

package de.janniskilian.basket.core.util.extension.extern

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.luminance

fun @receiver:ColorInt Int.createStrokeColor(): Int {
	return if (isDark) {
		changeBrightness(STROKE_DARK_MULTIPLIER)
	} else {
		changeBrightness(STROKE_LIGHT_MULTIPLIER)
	}
}

fun @receiver:ColorInt Int.changeBrightness(factor: Float): Int {
	val hsv = FloatArray(HSV_COMPONENTS_COUNT)
	Color.colorToHSV(this, hsv)
	hsv[hsv.lastIndex] *= factor
	return Color.HSVToColor(hsv)
}

val @receiver:ColorInt Int.isDark: Boolean
	get() = luminance < DARK_LUMINANCE_THRESHOLD

private const val DARK_LUMINANCE_THRESHOLD = 0.75f
private const val STROKE_DARK_MULTIPLIER = 0.75f
private const val STROKE_LIGHT_MULTIPLIER = 1.25f
private const val HSV_COMPONENTS_COUNT = 3
