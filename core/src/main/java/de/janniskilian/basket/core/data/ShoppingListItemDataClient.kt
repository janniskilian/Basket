package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import kotlinx.coroutines.Job

interface ShoppingListItemDataClient {

	suspend fun create(
		shoppingListId: Long,
		article: Article,
		quantity: String = "",
		checked: Boolean = false
	)

	fun create(shoppingListItems: List<ShoppingListItem>): Job

	fun get(id: Long): LiveData<ShoppingListItem>

	fun update(shoppingListItem: ShoppingListItem): Job

	fun setAllCheckedForShoppingList(shoppingListId: Long, checked: Boolean): Job

	fun delete(shoppingListId: Long, articleId: Long): Job

	fun deleteAllForShoppingList(shoppingListId: Long): Job

	fun deleteAllCheckedForShoppingList(shoppingListId: Long): Job
}
