package de.janniskilian.basket.core.util

import java.lang.ref.WeakReference

class WeakRef<T>(reference: T) {

    private val internalWeakRef = WeakReference(reference)

    operator fun invoke(): T? = internalWeakRef.get()
}

fun <T : Any> T.weakRef(): WeakRef<T> =
	WeakRef(this)
