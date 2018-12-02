package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.map
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CategoryDataClientImpl(localDb: LocalDatabase) : CategoryDataClient {

	private val dao = localDb.categoryDao()

	override fun create(name: String) {
		GlobalScope.launch(Dispatchers.IO) {
			dao.insert(RoomCategory(name))
		}
	}

	override fun create(categories: List<RoomCategory>): Deferred<List<Long>> =
		GlobalScope.async(Dispatchers.IO) {
			dao.insert(categories)
		}

	override fun get(id: Long): LiveData<Category> =
		dao
			.select(id)
			.map { roomToModel(it) }

	override fun get(name: String): LiveData<List<Category>> =
		dao
			.select("%$name%")
			.map { results ->
				results.map { roomToModel(it) }
			}

	override fun getCount(): Deferred<Int> =
		GlobalScope.async(Dispatchers.IO) {
			dao.selectCount()
		}

	override fun update(category: Category) {
		GlobalScope.launch(Dispatchers.IO) {
			dao.update(modelToRoom(category))
		}
	}

	override fun delete(category: Category) {
		GlobalScope.launch(Dispatchers.IO) {
			dao.delete(modelToRoom(category))
		}
	}
}
