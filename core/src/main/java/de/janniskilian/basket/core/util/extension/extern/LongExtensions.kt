package de.janniskilian.basket.core.util.extension.extern

fun Long.minusOneAsNull(): Long? =
    if (this == -1L) {
        null
    } else {
        this
    }
