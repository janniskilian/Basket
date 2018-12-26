package de.janniskilian.basket.feature.lists.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import kotlinx.coroutines.launch

class ListViewModel(
	private val args: ListFragmentArgs,
	private val dataClient: DataClient
) : ViewModel() {

	val shoppingList = dataClient.shoppingList.getLiveData(args.shoppingListId)

	fun listItemClicked(shoppingListItem: ShoppingListItem) {
		viewModelScope.launch {
			dataClient.shoppingListItem.update(
				shoppingListItem.copy(checked = !shoppingListItem.checked)
			)
		}
	}

	fun setAllListItemsChecked(checked: Boolean) {
		viewModelScope.launch {
			dataClient.shoppingListItem.setAllCheckedForShoppingList(args.shoppingListId, checked)
		}
	}

	fun removeAllListItems() {
		viewModelScope.launch {
			dataClient.shoppingListItem.deleteAllForShoppingList(args.shoppingListId)
		}
	}

	fun removeAllCheckedListItems() {
		viewModelScope.launch {
			dataClient.shoppingListItem.deleteAllCheckedForShoppingList(args.shoppingListId)
		}
	}
}
