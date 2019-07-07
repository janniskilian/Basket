package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.type.domain.*

interface ShoppingListItemDataClient {

	suspend fun create(
		shoppingListId: ShoppingListId,
		article: Article,
		quantity: String = "",
		checked: Boolean = false
	)

    suspend fun create(shoppingListItems: List<ShoppingListItem>)

    fun get(shoppingListItemId: ShoppingListItemId): LiveData<ShoppingListItem>

    suspend fun update(shoppingListItem: ShoppingListItem)

	suspend fun setAllCheckedForShoppingList(shoppingListId: ShoppingListId, checked: Boolean)

	suspend fun delete(shoppingListId: ShoppingListId, articleId: ArticleId)

	suspend fun deleteAllForShoppingList(shoppingListId: ShoppingListId)

	suspend fun deleteAllCheckedForShoppingList(shoppingListId: ShoppingListId)
}
