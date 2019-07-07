package de.janniskilian.basket.core.util.function

import android.content.Context
import androidx.annotation.BoolRes
import androidx.annotation.IntegerRes

fun getLong(context: Context, @IntegerRes resId: Int) =
    context.resources.getInteger(resId).toLong()

fun getBoolean(context: Context, @BoolRes resId: Int) =
    context.resources.getBoolean(resId)
