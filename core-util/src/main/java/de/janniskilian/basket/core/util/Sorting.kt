package de.janniskilian.basket.core.util

import de.janniskilian.basket.core.type.domain.NamedItem
import java.text.Collator
import java.text.Normalizer

private val collator = Collator.getInstance()

private val namedItemComparator = Comparator<NamedItem> { item1, item2 ->
    collator.compare(item1.name, item2.name)
}

fun <T : NamedItem> List<T>.sortedByName(): List<T> =
    sortedWith(namedItemComparator)

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
