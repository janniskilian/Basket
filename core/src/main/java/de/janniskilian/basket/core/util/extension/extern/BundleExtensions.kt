package de.janniskilian.basket.core.util.extension.extern

import android.os.Bundle

fun Bundle.getIntOrNull(key: String): Int? {
    val value = getInt(key, Int.MIN_VALUE)
    return if (value == Int.MIN_VALUE) {
        null
    } else {
        value
    }
}
