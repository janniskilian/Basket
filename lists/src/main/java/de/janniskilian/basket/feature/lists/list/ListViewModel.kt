package de.janniskilian.basket.feature.lists.list

import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.data.DataClient

class ListViewModel(
	private val args: ListFragmentArgs,
	private val dataClient: DataClient
) : ViewModel() {

	val shoppingList = dataClient.shoppingList.getLiveData(args.shoppingListId)

	fun setAllListItemsChecked(checked: Boolean) {
		dataClient.shoppingListItem.setAllCheckedForShoppingList(args.shoppingListId, checked)
	}

	fun removeAllListItems() {
		dataClient.shoppingListItem.deleteAllForShoppingList(args.shoppingListId)
	}

	fun removeAllCheckedListItems() {
		dataClient.shoppingListItem.deleteAllCheckedForShoppingList(args.shoppingListId)
	}
}
