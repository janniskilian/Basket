package de.janniskilian.basket.feature.categories.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.util.viewmodel.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryId: Long?,
    private val useCases: CategoryFragmentUseCases,
    dataClient: DataClient
) : ViewModel() {

    private val _name = MutableLiveData<String>()

    private val _error = MutableLiveData<Boolean>()

    private val _dismiss = SingleLiveEvent<Unit>()

    init {
        if (categoryId != null) {
            GlobalScope.launch(Dispatchers.Main) {
                dataClient.category.getSuspend(categoryId)?.let {
                    setName(it.name)
                }
            }
        }
    }

    val name: LiveData<String>
        get() = _name

    val error: LiveData<Boolean>
        get() = _error

    val dismiss: LiveData<Unit>
        get() = _dismiss

    fun setName(name: String) {
        _name.value = name
        _error.value = false
    }

    fun deleteButtonClicked() {
        if (categoryId != null) {
            useCases.deleteCategory(categoryId)
            _dismiss.setValue(Unit)
        }
    }

    fun submitButtonClicked() {
        val name = name.value
        if (name.isNullOrBlank()) {
            _error.value = true
        } else {
            if (categoryId == null) {
                useCases.createCategory(name)
            } else {
                useCases.editCategory(categoryId, name)
            }

            _dismiss.setValue(Unit)
        }
    }
}
