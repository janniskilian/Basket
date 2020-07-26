package de.janniskilian.basket.feature.lists.lists

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.util.sortedByName
import de.janniskilian.basket.core.util.viewmodel.SingleLiveEvent
import kotlinx.coroutines.launch

class ListsViewModel @ViewModelInject constructor(
    private val dataClient: DataClient
) : ViewModel() {

    private var _shoppingListDeleted = SingleLiveEvent<ShoppingList>()

    val shoppingLists = dataClient
        .shoppingList
        .getAll()
        .map { it.sortedByName() }

    val shoppingListDeleted: LiveData<ShoppingList>
        get() = _shoppingListDeleted

    fun deleteList(position: Int) {
        shoppingLists.value?.getOrNull(position)?.let {
            viewModelScope.launch { dataClient.shoppingList.delete(it.id) }
            _shoppingListDeleted.setValue(it)
        }
    }

    fun restoreShoppingList() {
        shoppingListDeleted.value?.let {
            viewModelScope.launch {
                dataClient.shoppingList.create(it)
                dataClient.shoppingListItem.create(it.items)
            }
        }
    }
}

