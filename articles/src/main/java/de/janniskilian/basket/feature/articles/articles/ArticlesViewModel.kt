package de.janniskilian.basket.feature.articles.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.navigationcontainer.SearchBarViewModel
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.util.sortedByName
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData

class ArticlesViewModel(dataClient: DataClient) : ViewModel(), SearchBarViewModel {

    private val _searchBarVisible = MutableLiveData<Boolean>()

    private val _searchBarInput = DefaultMutableLiveData("")

    val articles: LiveData<List<Article>> =
        searchBarInput.switchMap { input ->
            dataClient.article.get(input).map { articles ->
                articles.sortedByName()
            }
        }

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
