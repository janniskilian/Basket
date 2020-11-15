package de.janniskilian.basket.feature.lists.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.viewmodel.SingleLiveEvent
import kotlinx.coroutines.launch

class ListViewModel @ViewModelInject constructor(
    private val dataClient: DataClient
) : ViewModel() {

    private val shoppingListId = MutableLiveData<ShoppingListId>()

    private var _listItemsRemoved = SingleLiveEvent<List<ShoppingListItem>>()
    private var _allListItemsSetIsChecked = SingleLiveEvent<List<ShoppingListItem>>()

    val shoppingList = shoppingListId.switchMap {
        dataClient
            .shoppingList
            .getAsFlow(it)
            .asLiveData()
    }

    val listItemsRemoved: LiveData<List<ShoppingListItem>>
        get() = _listItemsRemoved

    val allListItemsSetToChecked: LiveData<List<ShoppingListItem>>
        get() = _allListItemsSetIsChecked

    fun setShoppingListId(id: ShoppingListId) {
        shoppingListId.value = id
    }

    fun listItemClicked(shoppingListItem: ShoppingListItem) {
        viewModelScope.launch {
            dataClient.shoppingListItem.update(
                shoppingListItem.copy(isChecked = !shoppingListItem.isChecked)
            )
        }
    }

    fun setAllListItemsChecked(isChecked: Boolean) {
        shoppingList.value?.let { list ->
            if (!list.isEmpty) {
                viewModelScope.launch {
                    dataClient.shoppingListItem.setAllCheckedForShoppingList(list.id, isChecked)
                }

                _allListItemsSetIsChecked.setValue(
                    list.items.filter { it.isChecked != isChecked }
                )
            }
        }
    }

    fun removeListItem(listItem: ShoppingListItem) {
        viewModelScope.launch {
            dataClient.shoppingListItem.delete(listItem.shoppingListId, listItem.article.id)
        }

        _listItemsRemoved.setValue(listOf(listItem))
    }

    fun removeAllListItems() {
        shoppingList.value?.let {
            if (!it.isEmpty) {
                viewModelScope.launch {
                    dataClient.shoppingListItem.deleteAllForShoppingList(it.id)
                }

                _listItemsRemoved.setValue(it.items)
            }
        }
    }

    fun removeAllCheckedListItems() {
        shoppingList.value?.let { list ->
            if (!list.isEmpty) {
                viewModelScope.launch {
                    dataClient.shoppingListItem.deleteAllCheckedForShoppingList(list.id)
                }

                val checkedListItems = list.items.filter { it.isChecked }
                _listItemsRemoved.setValue(checkedListItems)
            }
        }
    }

    fun undoRemoveListItems() {
        listItemsRemoved.value?.let {
            viewModelScope.launch {
                dataClient.shoppingListItem.create(it)
            }
        }
    }

    fun undoSetAllListItemsIsChecked() {
        allListItemsSetToChecked.value?.let {
            viewModelScope.launch {
                dataClient.shoppingListItem.update(it)
            }
        }
    }
}
