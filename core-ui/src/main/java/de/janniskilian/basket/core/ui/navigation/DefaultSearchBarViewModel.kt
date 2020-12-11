package de.janniskilian.basket.core.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.janniskilian.basket.core.util.android.viewmodel.DefaultMutableLiveData

class DefaultSearchBarViewModel : SearchBarViewModel {

    private val _searchBarVisible = MutableLiveData<Boolean>()

    private val _searchBarInput = DefaultMutableLiveData("")

    override val searchBarVisible: LiveData<Boolean>
        get() = _searchBarVisible

    override val searchBarInput: LiveData<String>
        get() = _searchBarInput

    override fun setSearchBarVisible(isVisible: Boolean) {
        _searchBarVisible.value = isVisible
        if (!isVisible) {
            setSearchBarInput("")
        }
    }

    override fun setSearchBarInput(input: String) {
        _searchBarInput.value = input
    }
}
