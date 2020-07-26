package de.janniskilian.basket.feature.lists.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import kotlinx.coroutines.launch

class ListViewModel @ViewModelInject constructor(
    private val dataClient: DataClient
) : ViewModel() {

    private val shoppingListId = MutableLiveData<ShoppingListId>()

    val shoppingList = shoppingListId.switchMap(dataClient.shoppingList::getLiveData)

    fun setShoppingListId(id: ShoppingListId) {
        shoppingListId.value = id
    }

    fun listItemClicked(shoppingListItem: ShoppingListItem) {
        viewModelScope.launch {
            dataClient.shoppingListItem.update(
                shoppingListItem.copy(checked = !shoppingListItem.checked)
            )
        }
    }

    fun setAllListItemsChecked(checked: Boolean) {
        shoppingListId.value?.let {
            viewModelScope.launch {
                dataClient.shoppingListItem.setAllCheckedForShoppingList(it, checked)
            }
        }
    }

    fun removeAllListItems() {
        shoppingListId.value?.let {
            viewModelScope.launch {
                dataClient.shoppingListItem.deleteAllForShoppingList(it)
            }
        }
    }

    fun removeAllCheckedListItems() {
        shoppingListId.value?.let {
            viewModelScope.launch {
                dataClient.shoppingListItem.deleteAllCheckedForShoppingList(it)
            }
        }
    }
}
