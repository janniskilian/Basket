package de.janniskilian.basket.core.navigationcontainer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData

class DefaultSearchBarViewModel : SearchBarViewModel {

	private val _searchBarVisible = MutableLiveData<Boolean>()

	private val _searchBarInput = DefaultMutableLiveData("")

	override val searchBarVisible: LiveData<Boolean>
		get() = _searchBarVisible

	override val searchBarInput: LiveData<String>
		get() = _searchBarInput

	override fun setSearchBarVisible(visible: Boolean) {
		_searchBarVisible.value = visible
		if (!visible) {
			setSearchBarInput("")
		}
	}

	override fun setSearchBarInput(input: String) {
		_searchBarInput.value = input
	}
}
