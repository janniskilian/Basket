package de.janniskilian.basket.core.util.function

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified S : ViewModel> createViewModel(
	fragment: Fragment,
	crossinline creator: () -> S
): S {
	@Suppress("UNCHECKED_CAST")
	val factory = object : ViewModelProvider.Factory {
		override fun <T : ViewModel?> create(modelClass: Class<T>): T =
			creator() as T
	}

	return ViewModelProviders
		.of(fragment, factory)
		.get(S::class.java)
}
