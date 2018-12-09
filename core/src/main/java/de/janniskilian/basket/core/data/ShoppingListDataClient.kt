package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.type.domain.ShoppingList
import kotlinx.coroutines.Job

interface ShoppingListDataClient {

	suspend fun create(name: String, color: Int): Long

	suspend fun create(shoppingList: ShoppingList): Long

	suspend fun get(id: Long): ShoppingList?

	fun getLiveData(id: Long): LiveData<ShoppingList>

	fun getAll(): LiveData<List<ShoppingList>>

	fun update(shoppingListId: Long, name: String, color: Int): Job

	fun delete(id: Long): Job
}
