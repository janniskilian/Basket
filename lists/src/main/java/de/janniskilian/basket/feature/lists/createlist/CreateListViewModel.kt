package de.janniskilian.basket.feature.lists.createlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.function.createMutableLiveData
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData
import de.janniskilian.basket.core.util.viewmodel.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateListViewModel(
    private val shoppingListId: ShoppingListId?,
    val colors: List<Int>,
    private val useCases: CreateListFragmentUseCases,
    dataClient: DataClient
) : ViewModel() {

    private val _name = MutableLiveData<String>()

    private val _selectedColor =
        DefaultMutableLiveData(colors.first())

    private val _error = createMutableLiveData(false)

    private val _startList = SingleLiveEvent<ShoppingListId>()

    private val _dismiss = SingleLiveEvent<Unit>()

    init {
        if (shoppingListId != null) {
            viewModelScope.launch(Dispatchers.Main) {
                dataClient.shoppingList.get(shoppingListId)?.let {
                    setName(it.name)
                    setSelectedColor(it.color)
                }
            }
        }
    }

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

    fun setName(name: String) {
        _name.value = name
        _error.value = false
    }

    fun setSelectedColor(color: Int) {
        _selectedColor.value = color
    }

    fun submitButtonClicked() {
        val name = name.value
        when {
            name.isNullOrBlank() -> _error.value = true

            shoppingListId == null ->
                viewModelScope.launch {
                    val id = useCases.createList(name, _selectedColor.value)
                    _startList.postValue(id)
                }

            else -> {
                viewModelScope.launch {
                    useCases.updateList(shoppingListId, name, _selectedColor.value)
                    _dismiss.postValue(Unit)
                }
            }
        }
    }
}
