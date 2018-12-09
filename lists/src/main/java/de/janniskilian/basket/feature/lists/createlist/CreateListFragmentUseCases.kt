package de.janniskilian.basket.feature.lists.createlist

import de.janniskilian.basket.core.data.DataClient

class CreateListFragmentUseCases(private val dataClient: DataClient) {

	suspend fun createList(name: String, color: Int): Long =
		dataClient.shoppingList.create(name, color)

	fun updateList(shoppingListId: Long, name: String, color: Int) {
		dataClient.shoppingList.update(shoppingListId, name, color)
	}
}
