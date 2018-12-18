package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.map
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CategoryDataClientImpl(localDb: LocalDatabase) : CategoryDataClient {

	private val dao = localDb.categoryDao()

	override fun create(name: String) = GlobalScope.launch { dao.insert(RoomCategory(name)) }

	override suspend fun create(categories: List<RoomCategory>): List<Long> =
		dao.insert(categories)

	override fun get(id: Long): LiveData<Category> =
		dao
			.select(id)
			.map { roomToModel(it) }

	override suspend fun getSuspend(id: Long): Category? =
		dao.selectSuspend(id)?.let(::roomToModel)

	override fun get(name: String): LiveData<List<Category>> =
		dao
			.select("%$name%")
			.map { results ->
				results.map { roomToModel(it) }
			}

	override suspend fun getCount(): Int = dao.selectCount()

	override fun update(category: Category) =
		GlobalScope.launch { dao.update(modelToRoom(category)) }

    override fun update(id: Long, name: String) =
        GlobalScope.launch { dao.update(id, name) }

    override fun delete(id: Long) =
        GlobalScope.launch { dao.delete(id) }
}
