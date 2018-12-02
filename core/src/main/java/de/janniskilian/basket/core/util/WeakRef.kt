package de.janniskilian.basket.core.util

import java.lang.ref.WeakReference

class WeakRef<T>(reference: T) {

	private val weakRef = WeakReference(reference)

	operator fun invoke(): T? = weakRef.get()
}

fun <T : Any> T.weakRef(): WeakRef<T> =
	WeakRef(this)
