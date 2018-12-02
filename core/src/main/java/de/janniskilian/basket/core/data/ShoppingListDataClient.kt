package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.type.domain.ShoppingList
import kotlinx.coroutines.Deferred

interface ShoppingListDataClient {

	fun create(name: String, color: Int): Deferred<Long>

	fun create(shoppingList: ShoppingList): Deferred<Long>

	fun get(id: Long): Deferred<ShoppingList?>

	fun getLiveData(id: Long): LiveData<ShoppingList>

	fun getAll(): LiveData<List<ShoppingList>>

	fun update(shoppingList: ShoppingList)

	fun update(shoppingListId: Long, name: String, color: Int)

	fun delete(shoppingList: ShoppingList)

	fun delete(id: Long)
}
