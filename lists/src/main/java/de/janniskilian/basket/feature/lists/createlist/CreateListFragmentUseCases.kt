package de.janniskilian.basket.feature.lists.createlist

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingListId

class CreateListFragmentUseCases(private val dataClient: DataClient) {

	suspend fun createList(name: String, color: Int): Long =
		dataClient.shoppingList.create(name, color)

	suspend fun updateList(shoppingListId: ShoppingListId, name: String, color: Int) {
		dataClient.shoppingList.update(shoppingListId, name, color)
	}
}
