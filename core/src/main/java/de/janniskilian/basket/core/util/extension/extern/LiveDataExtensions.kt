package de.janniskilian.basket.core.util.extension.extern

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.delay
import org.jetbrains.annotations.TestOnly
import kotlin.math.roundToLong
import kotlin.math.sqrt

@TestOnly
suspend fun <T> LiveData<T>.nextValueOptional(timeoutMillis: Long = DEFAULT_TIMEOUT_MILLIS): T? {
    val observer = Observer<T> {}.also { observeForever(it) }

    val currentValue = value
    val t0 = System.currentTimeMillis()
    val delayMillis = sqrt(timeoutMillis.toFloat()).roundToLong()

    while (value == currentValue
        && System.currentTimeMillis() - t0 < timeoutMillis
    ) {
        delay(delayMillis)
    }

    removeObserver(observer)

    return value
}

@TestOnly
suspend fun <T> LiveData<T>.nextValue(timeoutMillis: Long = DEFAULT_TIMEOUT_MILLIS): T =
    nextValueOptional(timeoutMillis)!!

@TestOnly
suspend fun <T> LiveData<T>.nextValue(
    timeoutMillis: Long = DEFAULT_TIMEOUT_MILLIS,
    block: (value: T) -> Unit
) {
    block(nextValue(timeoutMillis))
}

private const val DEFAULT_TIMEOUT_MILLIS = 500L
