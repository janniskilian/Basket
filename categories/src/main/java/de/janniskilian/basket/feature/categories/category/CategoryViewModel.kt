package de.janniskilian.basket.feature.categories.category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.CategoryId
import de.janniskilian.basket.core.util.viewmodel.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel @ViewModelInject constructor(
    private val useCases: CategoryFragmentUseCases,
    private val dataClient: DataClient
) : ViewModel() {

    private var categoryId: CategoryId? = null

    private val _name = MutableLiveData<String>()

    private val _error = MutableLiveData<Boolean>()

    private val _dismiss = SingleLiveEvent<Unit>()

    val name: LiveData<String>
        get() = _name

    val error: LiveData<Boolean>
        get() = _error

    val dismiss: LiveData<Unit>
        get() = _dismiss

    fun setCategoryId(id: CategoryId) {
        categoryId = id

        viewModelScope.launch(Dispatchers.Main) {
            dataClient.category
                .getSuspend(id)
                ?.let {
                    setName(it.name)
                }
        }
    }

    fun setName(name: String) {
        _name.value = name
        _error.value = false
    }

    fun deleteButtonClicked() {
        categoryId?.let {
            viewModelScope.launch {
                useCases.deleteCategory(it)
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
                val id = categoryId
                if (id == null) {
                    useCases.createCategory(name)
                } else {
                    useCases.editCategory(id, name)
                }

                _dismiss.postValue(Unit)
            }
        }
    }
}
