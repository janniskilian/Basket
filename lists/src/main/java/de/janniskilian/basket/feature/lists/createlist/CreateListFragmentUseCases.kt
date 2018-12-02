package de.janniskilian.basket.feature.lists.createlist

import de.janniskilian.basket.core.data.DataClient
import kotlinx.coroutines.Deferred

class CreateListFragmentUseCases(private val dataClient: DataClient) {

	fun createList(name: String, color: Int): Deferred<Long> =
		dataClient.shoppingList.create(name, color)

	fun updateList(shoppingListId: Long, name: String, color: Int) {
		dataClient.shoppingList.update(shoppingListId, name, color)
	}
}
