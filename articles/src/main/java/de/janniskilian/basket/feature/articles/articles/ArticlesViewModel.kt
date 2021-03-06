package de.janniskilian.basket.feature.articles.articles

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.ui.navigation.SearchBarViewModel
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.util.sortedByName
import de.janniskilian.basket.core.util.android.viewmodel.DefaultMutableLiveData

class ArticlesViewModel @ViewModelInject constructor(
    dataClient: DataClient
) : ViewModel(), SearchBarViewModel {

    private val _searchBarVisible = MutableLiveData<Boolean>()

    private val _searchBarInput = DefaultMutableLiveData("")

    val articles: LiveData<List<Article>> =
        searchBarInput
            .switchMap {
                dataClient
                    .article
                    .get(it)
                    .asLiveData()
            }
            .map { it.sortedByName() }

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
