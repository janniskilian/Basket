package de.janniskilian.basket.core.data

import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListId
import kotlinx.coroutines.flow.Flow

interface ShoppingListDataClient {

    suspend fun create(name: String, color: Int): ShoppingListId

    suspend fun create(shoppingList: ShoppingList): ShoppingListId

    suspend fun get(shoppingListId: ShoppingListId): ShoppingList?

    fun getAsFlow(shoppingListId: ShoppingListId): Flow<ShoppingList>

    fun getAll(): Flow<List<ShoppingList>>

    suspend fun update(shoppingListId: ShoppingListId, name: String, color: Int)

    suspend fun delete(shoppingListId: ShoppingListId)
}
