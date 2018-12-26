package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.type.domain.ShoppingList

interface ShoppingListDataClient {

	suspend fun create(name: String, color: Int): Long

	suspend fun create(shoppingList: ShoppingList): Long

	suspend fun get(id: Long): ShoppingList?

	fun getLiveData(id: Long): LiveData<ShoppingList>

	fun getAll(): LiveData<List<ShoppingList>>

    suspend fun update(shoppingListId: Long, name: String, color: Int)

    suspend fun delete(id: Long)
}
