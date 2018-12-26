package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingListItem
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.function.withIOContext

class ShoppingListItemDataClientImpl(localDb: LocalDatabase) : ShoppingListItemDataClient {

    private val dao = localDb.shoppingListItemDao()

    override suspend fun create(
        shoppingListId: Long,
        article: Article,
        quantity: String,
        checked: Boolean
    ) = withIOContext {
        dao.insert(
            RoomShoppingListItem(
                shoppingListId,
                article.id,
                quantity,
                checked
            )
        )
    }

    override suspend fun create(shoppingListItems: List<ShoppingListItem>) = withIOContext {
        dao.insert(shoppingListItems.map { modelToRoom(it) })
    }

    override fun get(id: Long): LiveData<ShoppingListItem> =
        dao
            .select(id)
            .map { roomToModel(it) }

    override suspend fun update(shoppingListItem: ShoppingListItem) = withIOContext {
        dao.update(modelToRoom(shoppingListItem))
    }

    override suspend fun setAllCheckedForShoppingList(
        shoppingListId: Long,
        checked: Boolean
    ) = withIOContext {
        dao.setAllCheckedForShoppingList(shoppingListId, checked)
    }

    override suspend fun delete(shoppingListId: Long, articleId: Long) = withIOContext {
        dao.delete(shoppingListId, articleId)
    }

    override suspend fun deleteAllForShoppingList(shoppingListId: Long) = withIOContext {
        dao.deleteAllForShoppingList(shoppingListId)
    }

    override suspend fun deleteAllCheckedForShoppingList(shoppingListId: Long) = withIOContext {
        dao.deleteAllCheckedForShoppingList(shoppingListId)
    }
}
