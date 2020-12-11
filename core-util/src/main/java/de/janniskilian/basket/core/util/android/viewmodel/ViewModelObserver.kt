package de.janniskilian.basket.core.util.android.viewmodel

import androidx.lifecycle.ViewModel

abstract class ViewModelObserver<V : ViewModel>(protected val viewModel: V) {

    abstract fun observe()
}
