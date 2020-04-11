package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListId

interface ShoppingListDataClient {

    suspend fun create(name: String, color: Int): ShoppingListId

    suspend fun create(shoppingList: ShoppingList): ShoppingListId

    suspend fun get(shoppingListId: ShoppingListId): ShoppingList?

    fun getLiveData(shoppingListId: ShoppingListId): LiveData<ShoppingList>

    fun getAll(): LiveData<List<ShoppingList>>

    suspend fun update(shoppingListId: ShoppingListId, name: String, color: Int)

    suspend fun delete(shoppingListId: ShoppingListId)
}
