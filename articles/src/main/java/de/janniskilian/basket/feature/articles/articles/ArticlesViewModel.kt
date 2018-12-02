package de.janniskilian.basket.feature.articles.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.navigationcontainer.SearchBarViewModel
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData

class ArticlesViewModel(dataClient: DataClient) : ViewModel(), SearchBarViewModel {

	private val _searchBarVisible = MutableLiveData<Boolean>()

	private val _searchBarInput = DefaultMutableLiveData("")

	val articles: LiveData<List<Article>> =
		Transformations.switchMap(searchBarInput) {
			dataClient.article.get(it)
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
