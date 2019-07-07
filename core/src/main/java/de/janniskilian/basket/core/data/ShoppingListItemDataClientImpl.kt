package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingListItem
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.*
import de.janniskilian.basket.core.util.function.withIOContext

class ShoppingListItemDataClientImpl(localDb: LocalDatabase) : ShoppingListItemDataClient {

    private val dao = localDb.shoppingListItemDao()

    override suspend fun create(
        shoppingListId: ShoppingListId,
        article: Article,
        quantity: String,
        checked: Boolean
    ) = withIOContext {
        dao.insert(
            RoomShoppingListItem(
                shoppingListId.value,
                article.id.value,
                quantity,
                checked
            )
        )
    }

    override suspend fun create(shoppingListItems: List<ShoppingListItem>) = withIOContext {
        dao.insert(shoppingListItems.map { modelToRoom(it) })
    }

    override fun get(shoppingListItemId: ShoppingListItemId): LiveData<ShoppingListItem> =
        dao
            .select(shoppingListItemId.value)
            .map { roomToModel(it) }

    override suspend fun update(shoppingListItem: ShoppingListItem) = withIOContext {
        dao.update(modelToRoom(shoppingListItem))
    }

    override suspend fun setAllCheckedForShoppingList(
        shoppingListId: ShoppingListId,
        checked: Boolean
    ) = withIOContext {
        dao.setAllCheckedForShoppingList(shoppingListId.value, checked)
    }

    override suspend fun delete(shoppingListId: ShoppingListId, articleId: ArticleId) = withIOContext {
        dao.delete(shoppingListId.value, articleId.value)
    }

    override suspend fun deleteAllForShoppingList(shoppingListId: ShoppingListId) = withIOContext {
        dao.deleteAllForShoppingList(shoppingListId.value)
    }

    override suspend fun deleteAllCheckedForShoppingList(shoppingListId: ShoppingListId) = withIOContext {
        dao.deleteAllCheckedForShoppingList(shoppingListId.value)
    }
}
