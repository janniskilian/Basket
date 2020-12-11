package de.janniskilian.basket.core.util.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

fun Lifecycle.doOnEvent(event: Lifecycle.Event, block: () -> Unit) {
    addObserver(
        object : LifecycleEventObserver {

            override fun onStateChanged(source: LifecycleOwner, e: Lifecycle.Event) {
                if (e == event) {
                    source.lifecycle.removeObserver(this)
                    block()
                }
            }
        }
    )
}
