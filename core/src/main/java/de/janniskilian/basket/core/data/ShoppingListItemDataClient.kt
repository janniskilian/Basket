package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ShoppingListItem

interface ShoppingListItemDataClient {

	suspend fun create(
		shoppingListId: Long,
		article: Article,
		quantity: String = "",
		checked: Boolean = false
	)

    suspend fun create(shoppingListItems: List<ShoppingListItem>)

	fun get(id: Long): LiveData<ShoppingListItem>

    suspend fun update(shoppingListItem: ShoppingListItem)

    suspend fun setAllCheckedForShoppingList(shoppingListId: Long, checked: Boolean)

    suspend fun delete(shoppingListId: Long, articleId: Long)

    suspend fun deleteAllForShoppingList(shoppingListId: Long)

    suspend fun deleteAllCheckedForShoppingList(shoppingListId: Long)
}
