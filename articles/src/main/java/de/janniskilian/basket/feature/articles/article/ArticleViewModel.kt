package de.janniskilian.basket.feature.articles.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.map
import de.janniskilian.basket.core.util.function.addToFront
import de.janniskilian.basket.core.util.function.createMutableLiveData
import de.janniskilian.basket.core.util.viewmodel.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticleViewModel(
	private val articleId: Long?,
	private val useCases: ArticleFragmentUseCases,
	dataClient: DataClient
) : ViewModel() {

	private val _name = MutableLiveData<String>()

	private val _category = MutableLiveData<Category?>()

	private val _mode = createMutableLiveData(ArticleDialogMode.EDIT)

	private val _error = MutableLiveData<Boolean>()

	private val _dismiss = SingleLiveEvent<Unit>()

	init {
		if (articleId == null) {
			setCategory(null)
		} else {
			GlobalScope.launch(Dispatchers.Main) {
				dataClient.article.get(articleId)?.let {
					setName(it.name)
					setCategory(it.category)
				}
			}
		}
	}

	val name: LiveData<String>
		get() = _name

	val category: LiveData<Category?>
		get() = _category

	val categories = dataClient.category.get().map {
		it.addToFront(null)
	}

	val mode: LiveData<ArticleDialogMode>
		get() = _mode

	val error: LiveData<Boolean>
		get() = _error

	val dismiss: LiveData<Unit>
		get() = _dismiss

	fun setName(name: String) {
		_name.value = name
		_error.value = false
	}

	fun setCategory(category: Category?) {
		_category.value = category
		_mode.value = ArticleDialogMode.EDIT
	}

	fun editCategoryClicked() {
		_mode.value = ArticleDialogMode.SELECT_CATEGORY
	}

	fun backPressed(): Boolean =
		if (_mode.value == ArticleDialogMode.EDIT) {
			false
		} else {
			_mode.value = ArticleDialogMode.EDIT
			true
		}

	fun deleteButtonClicked() {
		if (articleId != null) {
			useCases.deleteArticle(articleId)
			_dismiss.setValue(Unit)
		}
	}

	fun submitButtonClicked() {
		val name = name.value
		if (name.isNullOrBlank()) {
			_error.value = true
		} else {
			if (articleId == null) {
				useCases.createArticle(name, category.value)
			} else {
				useCases.editArticle(articleId, name, category.value)
			}

			_dismiss.setValue(Unit)
		}
	}
}
