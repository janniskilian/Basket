package de.janniskilian.basket.core.util.android.viewmodel

import androidx.lifecycle.MutableLiveData

class DefaultMutableLiveData<T>(defaultValue: T) : MutableLiveData<T>() {

    init {
        value = defaultValue
    }

    override fun getValue(): T =
        super.getValue()!!
}
