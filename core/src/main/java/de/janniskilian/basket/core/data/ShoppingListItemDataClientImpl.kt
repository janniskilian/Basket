package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingListItem
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.extension.extern.map
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingListItemDataClientImpl(localDb: LocalDatabase) : ShoppingListItemDataClient {

	private val dao = localDb.shoppingListItemDao()

	override suspend fun create(
		shoppingListId: Long,
		article: Article,
		quantity: String,
		checked: Boolean
	) {
		dao.insert(
			RoomShoppingListItem(
				shoppingListId,
				article.id,
				quantity,
				checked
			)
		)
	}

	override fun create(shoppingListItems: List<ShoppingListItem>) =
		GlobalScope.launch {
			dao.insert(shoppingListItems.map { modelToRoom(it) })
		}

	override fun get(id: Long): LiveData<ShoppingListItem> =
		dao
			.select(id)
			.map { roomToModel(it) }

	override fun update(shoppingListItem: ShoppingListItem) =
		GlobalScope.launch {
			dao.update(modelToRoom(shoppingListItem))
		}

	override fun setAllCheckedForShoppingList(shoppingListId: Long, checked: Boolean) =
		GlobalScope.launch {
			dao.setAllCheckedForShoppingList(shoppingListId, checked)
		}

	override fun delete(shoppingListId: Long, articleId: Long) =
		GlobalScope.launch {
			dao.delete(shoppingListId, articleId)
		}

	override fun deleteAllForShoppingList(shoppingListId: Long) =
		GlobalScope.launch {
			dao.deleteAllForShoppingList(shoppingListId)
		}

	override fun deleteAllCheckedForShoppingList(shoppingListId: Long) =
		GlobalScope.launch {
			dao.deleteAllCheckedForShoppingList(shoppingListId)
		}
}
