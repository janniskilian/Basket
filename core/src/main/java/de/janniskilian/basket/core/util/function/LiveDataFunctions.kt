package de.janniskilian.basket.core.util.function

import androidx.lifecycle.MutableLiveData

fun <T> createMutableLiveData(initialValue: T): MutableLiveData<T> =
    MutableLiveData<T>().apply {
        value = initialValue
    }
