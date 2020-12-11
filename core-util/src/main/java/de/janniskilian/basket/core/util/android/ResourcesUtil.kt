package de.janniskilian.basket.core.util.android

import android.content.Context
import android.content.res.Resources
import androidx.annotation.BoolRes
import androidx.annotation.IntegerRes

fun Resources.getLong(@IntegerRes res: Int) = getInteger(res).toLong()

fun getInt(context: Context, @IntegerRes resId: Int) =
    context.resources.getInteger(resId)

fun getLong(context: Context, @IntegerRes resId: Int) =
    context.resources.getLong(resId)

fun getBoolean(context: Context, @BoolRes resId: Int) =
    context.resources.getBoolean(resId)
