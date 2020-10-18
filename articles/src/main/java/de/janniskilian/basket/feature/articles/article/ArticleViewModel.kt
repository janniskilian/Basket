package de.janniskilian.basket.feature.articles.article

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.function.addToFront
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData
import de.janniskilian.basket.core.util.viewmodel.SingleLiveEvent
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ArticleViewModel @ViewModelInject constructor(
    private val useCases: ArticleFragmentUseCases,
    private val dataClient: DataClient
) : ViewModel() {

    private var articleId: ArticleId? = null

    private val _name = MutableLiveData<String>()

    private val _category = MutableLiveData<Category?>()

    private val _mode = DefaultMutableLiveData(ArticleFragmentMode.EDIT)

    private val _error = MutableLiveData<Boolean>()

    private val _dismiss = SingleLiveEvent<Unit>()

    val name: LiveData<String>
        get() = _name

    val category: LiveData<Category?>
        get() = _category

    val categories = dataClient.category
        .get()
        .map {
            it.addToFront(null)
        }
        .asLiveData()

    val mode: LiveData<ArticleFragmentMode>
        get() = _mode

    val error: LiveData<Boolean>
        get() = _error

    val dismiss: LiveData<Unit>
        get() = _dismiss

    fun setArticleId(id: ArticleId) {
        articleId = id

        viewModelScope.launch {
            dataClient
                .article
                .get(id)
                ?.let {
                    setName(it.name)
                    setCategory(it.category)
                }
        }
    }

    fun setName(name: String) {
        _name.value = name
        _error.value = false
    }

    fun setCategory(category: Category?) {
        _category.value = category
        _mode.value = ArticleFragmentMode.EDIT
    }

    fun editCategoryClicked() {
        _mode.value = ArticleFragmentMode.SELECT_CATEGORY
    }

    fun backPressed(): Boolean =
        if (_mode.value == ArticleFragmentMode.EDIT) {
            false
        } else {
            _mode.value = ArticleFragmentMode.EDIT
            true
        }

    fun deleteButtonClicked() {
        articleId?.let {
            viewModelScope.launch {
                useCases.deleteArticle(it)
                _dismiss.postValue(Unit)
            }
        }
    }

    fun submitButtonClicked() {
        val name = name.value
        if (name.isNullOrBlank()) {
            _error.value = true
        } else {
            viewModelScope.launch {
                val id = articleId
                if (id == null) {
                    useCases.createArticle(name, category.value)
                } else {
                    useCases.editArticle(id, name, category.value)
                }

                _dismiss.postValue(Unit)
            }
        }
    }
}
