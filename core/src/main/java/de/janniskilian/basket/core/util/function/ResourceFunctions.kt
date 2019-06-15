package de.janniskilian.basket.core.util.function

import android.content.Context
import androidx.annotation.IntegerRes

fun getLong(context: Context, @IntegerRes resId: Int) =
    context.resources.getInteger(resId).toLong()
