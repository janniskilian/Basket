package de.janniskilian.basket.feature.lists.createlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData
import de.janniskilian.basket.core.util.viewmodel.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateListViewModel @ViewModelInject constructor(
    val colors: List<Int>,
    private val useCases: CreateListFragmentUseCases,
    private val dataClient: DataClient
) : ViewModel() {

    private var shoppingListId: ShoppingListId? = null

    private val _name = MutableLiveData<String>()

    private val _selectedColor = DefaultMutableLiveData(colors.first())

    private val _error = DefaultMutableLiveData(false)

    private val _startList = SingleLiveEvent<ShoppingListId>()

    private val _dismiss = SingleLiveEvent<Unit>()

    val name: LiveData<String>
        get() = _name

    val selectedColor: LiveData<Int>
        get() = _selectedColor

    val error: LiveData<Boolean>
        get() = _error

    val startList: LiveData<ShoppingListId>
        get() = _startList

    val dismiss: LiveData<Unit>
        get() = _dismiss

    fun setShoppingListId(id: ShoppingListId) {
        shoppingListId = id

        viewModelScope.launch(Dispatchers.Main) {
            dataClient.shoppingList
                .get(id)
                ?.let {
                    setName(it.name)
                    setSelectedColor(it.color)
                }
        }
    }

    fun setName(name: String) {
        _name.value = name
        _error.value = false
    }

    fun setSelectedColor(color: Int) {
        _selectedColor.value = color
    }

    fun submitButtonClicked() {
        val id = shoppingListId
        val name = name.value
        when {
            name.isNullOrBlank() -> _error.value = true

            id == null ->
                viewModelScope.launch {
                    val createdListId = useCases.createList(name, _selectedColor.value)
                    _startList.postValue(createdListId)
                }

            else -> {
                viewModelScope.launch {
                    useCases.updateList(id, name, _selectedColor.value)
                    _dismiss.postValue(Unit)
                }
            }
        }
    }
}
