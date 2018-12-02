package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ShoppingListItem

interface ShoppingListItemDataClient {

	fun create(
		shoppingListId: Long,
		article: Article,
		quantity: String = "",
		checked: Boolean = false
	)

	fun create(shoppingListItems: List<ShoppingListItem>)

	fun get(id: Long): LiveData<ShoppingListItem>

	fun update(shoppingListItem: ShoppingListItem)

	fun setAllCheckedForShoppingList(shoppingListId: Long, checked: Boolean)

	fun delete(shoppingListItem: ShoppingListItem)

	fun delete(shoppingListId: Long, articleId: Long)

	fun deleteAllForShoppingList(shoppingListId: Long)

	fun deleteAllCheckedForShoppingList(shoppingListId: Long)
}
