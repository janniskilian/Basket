package de.janniskilian.basket.core.navigationcontainer

import androidx.lifecycle.LiveData

interface SearchBarViewModel {

    val searchBarVisible: LiveData<Boolean>

    val searchBarInput: LiveData<String>

    fun setSearchBarVisible(visible: Boolean)

    fun setSearchBarInput(input: String)
}
