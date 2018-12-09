package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingList
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.util.extension.extern.map
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingListDataClientImpl(localDb: LocalDatabase) : ShoppingListDataClient {

	private val shoppingListDao = localDb.shoppingListDao()

	override suspend fun create(name: String, color: Int): Long =
		shoppingListDao.insert(RoomShoppingList(name, color))

	override suspend fun create(shoppingList: ShoppingList): Long =
		shoppingListDao.insert(modelToRoom(shoppingList))

	override suspend fun get(id: Long): ShoppingList? {
		val result = shoppingListDao.select(id)
		return if (result.isEmpty()) {
			null
		} else {
			roomToModel(result)
		}
	}

	override fun getLiveData(id: Long): LiveData<ShoppingList> =
		shoppingListDao
			.selectLiveData(id)
			.map { roomToModel(it) }

	override fun getAll(): LiveData<List<ShoppingList>> =
		shoppingListDao
			.selectAll()
			.map { results ->
				results
					.groupBy { it.shoppingListId }
					.map { (_, value) -> roomToModel(value) }
			}

	override fun update(shoppingListId: Long, name: String, color: Int) =
		GlobalScope.launch { shoppingListDao.update(shoppingListId, name, color) }

	override fun delete(id: Long) = GlobalScope.launch { shoppingListDao.delete(id) }
}
