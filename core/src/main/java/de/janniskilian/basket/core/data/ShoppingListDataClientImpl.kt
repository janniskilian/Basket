package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingList
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.withoutSpecialChars
import de.janniskilian.basket.core.util.function.withIOContext

class ShoppingListDataClientImpl(localDb: LocalDatabase) : ShoppingListDataClient {

    private val shoppingListDao = localDb.shoppingListDao()

    override suspend fun create(name: String, color: Int) = withIOContext {
        ShoppingListId(
            shoppingListDao.insert(
                RoomShoppingList(
                    name,
                    name.withoutSpecialChars(),
                    color
                )
            )
        )
    }

    override suspend fun create(shoppingList: ShoppingList) = withIOContext {
        ShoppingListId(shoppingListDao.insert(modelToRoom(shoppingList)))
    }

    override suspend fun get(shoppingListId: ShoppingListId) = withIOContext {
        val result = shoppingListDao.select(shoppingListId.value)
        if (result.isEmpty()) {
            null
        } else {
            roomToModel(result)
        }
    }

    override fun getLiveData(shoppingListId: ShoppingListId): LiveData<ShoppingList> =
        shoppingListDao
            .selectLiveData(shoppingListId.value)
            .map { roomToModel(it) }

    override fun getAll(): LiveData<List<ShoppingList>> =
        shoppingListDao
            .selectAll()
            .map { results ->
                results
                    .groupBy { it.shoppingListId }
                    .map { (_, value) -> roomToModel(value) }
            }

    override suspend fun update(shoppingListId: ShoppingListId, name: String, color: Int) =
        withIOContext {
            shoppingListDao.update(
                shoppingListId.value,
                name,
                name.withoutSpecialChars(),
                color
            )
        }

    override suspend fun delete(shoppingListId: ShoppingListId) = withIOContext {
        shoppingListDao.delete(shoppingListId.value)
    }
}
