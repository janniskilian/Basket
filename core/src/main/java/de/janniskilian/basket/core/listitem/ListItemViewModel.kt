package de.janniskilian.basket.core.listitem

import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingListItemId

class ListItemViewModel(args: ListItemFragmentArgs, dataClient: DataClient) : ViewModel() {

    private val listItemId = ShoppingListItemId(args.listItemId)

    val listItem = dataClient.shoppingListItem.get(listItemId)
}
