package de.janniskilian.basket.core.util.extension.extern

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
	observe(owner, Observer { observer(it) })
}

fun <T, R> LiveData<T>.map(transform: (T) -> R): LiveData<R> =
	Transformations.map(this, transform)
