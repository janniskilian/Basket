package de.janniskilian.basket.core.testing

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class TestLifecycleOwner : LifecycleOwner {

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle = registry

    fun emit(event: Lifecycle.Event) {
        registry.handleLifecycleEvent(event)
    }

    companion object {

        fun resumed(): TestLifecycleOwner = TestLifecycleOwner().apply {
            emit(Lifecycle.Event.ON_RESUME)
        }
    }
}
