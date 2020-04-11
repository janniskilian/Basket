package de.janniskilian.basket.core.listitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingListItemId

class ListItemViewModel(args: ListItemFragmentArgs, dataClient: DataClient) : ViewModel() {

    private val listItemId = ShoppingListItemId(args.listItemId)

    val shoppingListItem = dataClient.shoppingListItem.get(listItemId)

    val shoppingList =
        shoppingListItem.switchMap { dataClient.shoppingList.getLiveData(it.shoppingListId) }
}
