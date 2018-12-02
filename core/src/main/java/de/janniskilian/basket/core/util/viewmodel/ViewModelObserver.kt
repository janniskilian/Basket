package de.janniskilian.basket.core.util.viewmodel

import androidx.lifecycle.ViewModel

abstract class ViewModelObserver<V : ViewModel>(protected val viewModel: V) {

	abstract fun observe()
}
