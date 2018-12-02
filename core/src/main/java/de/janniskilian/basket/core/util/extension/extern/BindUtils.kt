package de.janniskilian.basket.core.util.extension.extern

import android.content.Context
import androidx.annotation.DimenRes

fun getDimen(context: Context, @DimenRes resId: Int): Int =
	context.resources.getDimensionPixelSize(resId)

fun bindDimen(context: Context, @DimenRes resId: Int): Lazy<Int> =
	lazy { getDimen(context, resId) }
