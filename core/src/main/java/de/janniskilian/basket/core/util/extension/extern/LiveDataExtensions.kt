package de.janniskilian.basket.core.util.extension.extern

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(
    timeOutMilliseconds: Long = 2000
): T {
    var value: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T?) {
            value = t
            latch.countDown()
            removeObserver(this)
        }
    }

    observeForever(observer)

    if (!latch.await(timeOutMilliseconds, TimeUnit.MILLISECONDS)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return value as T
}
