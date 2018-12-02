package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingList
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.util.extension.extern.map
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ShoppingListDataClientImpl(localDb: LocalDatabase) : ShoppingListDataClient {

	private val shoppingListDao = localDb.shoppingListDao()

	override fun create(name: String, color: Int): Deferred<Long> =
		GlobalScope.async(Dispatchers.IO) {
			shoppingListDao.insert(RoomShoppingList(name, color))
		}

	override fun create(shoppingList: ShoppingList): Deferred<Long> =
		GlobalScope.async(Dispatchers.IO) {
			shoppingListDao.insert(modelToRoom(shoppingList))
		}

	override fun get(id: Long): Deferred<ShoppingList?> =
		GlobalScope.async(Dispatchers.IO) {
			val result = shoppingListDao.select(id)
			if (result.isEmpty()) {
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

	override fun update(shoppingList: ShoppingList) {
		GlobalScope.launch(Dispatchers.IO) {
			shoppingListDao.update(modelToRoom(shoppingList))
		}
	}

	override fun update(shoppingListId: Long, name: String, color: Int) {
		GlobalScope.launch(Dispatchers.IO) {
			shoppingListDao.update(shoppingListId, name, color)
		}
	}

	override fun delete(shoppingList: ShoppingList) {
		GlobalScope.launch(Dispatchers.IO) {
			shoppingListDao.delete(modelToRoom(shoppingList))
		}
	}

	override fun delete(id: Long) {
		GlobalScope.launch(Dispatchers.IO) {
			shoppingListDao.delete(id)
		}
	}
}
