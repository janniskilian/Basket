package de.janniskilian.basket.core.util.extension.extern

import java.text.Normalizer

private val specialCharsRegex by lazy {
    "[\\p{InCombiningDiacriticalMarks}]+".toRegex()
}

fun String.withoutSpecialChars(): String =
    String(
        Normalizer
            .normalize(this, Normalizer.Form.NFKD)
            .replace(specialCharsRegex, "")
            .toByteArray(Charsets.US_ASCII),
        Charsets.US_ASCII
    )
