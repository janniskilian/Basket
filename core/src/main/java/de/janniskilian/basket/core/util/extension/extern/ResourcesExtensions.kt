package de.janniskilian.basket.core.util.extension.extern

import android.content.res.Resources
import androidx.annotation.IntegerRes

fun Resources.getLong(@IntegerRes res: Int) = getInteger(res).toLong()
