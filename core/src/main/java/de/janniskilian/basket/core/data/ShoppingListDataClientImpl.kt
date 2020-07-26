package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import de.janniskilian.basket.core.data.localdb.dao.RoomShoppingListDao
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingList
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.withoutSpecialChars
import de.janniskilian.basket.core.util.function.withIOContext
import javax.inject.Inject

class ShoppingListDataClientImpl @Inject constructor(
    private val dao: RoomShoppingListDao
) : ShoppingListDataClient {

    override suspend fun create(name: String, color: Int) = withIOContext {
        ShoppingListId(
            dao.insert(
                RoomShoppingList(
                    name,
                    name.withoutSpecialChars(),
                    color
                )
            )
        )
    }

    override suspend fun create(shoppingList: ShoppingList) = withIOContext {
        ShoppingListId(dao.insert(modelToRoom(shoppingList)))
    }

    override suspend fun get(shoppingListId: ShoppingListId) = withIOContext {
        val result = dao.select(shoppingListId.value)
        if (result.isEmpty()) {
            null
        } else {
            roomToModel(result)
        }
    }

    override fun getLiveData(shoppingListId: ShoppingListId): LiveData<ShoppingList> =
        dao
            .selectLiveData(shoppingListId.value)
            .map { roomToModel(it) }

    override fun getAll(): LiveData<List<ShoppingList>> =
        dao
            .selectAll()
            .map { results ->
                results
                    .groupBy { it.shoppingListId }
                    .map { (_, value) -> roomToModel(value) }
            }

    override suspend fun update(shoppingListId: ShoppingListId, name: String, color: Int) =
        withIOContext {
            dao.update(
                shoppingListId.value,
                name,
                name.withoutSpecialChars(),
                color
            )
        }

    override suspend fun delete(shoppingListId: ShoppingListId) = withIOContext {
        dao.delete(shoppingListId.value)
    }
}
