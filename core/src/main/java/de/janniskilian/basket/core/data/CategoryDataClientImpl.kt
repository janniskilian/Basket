package de.janniskilian.basket.core.data

import androidx.lifecycle.map
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.function.withIOContext

class CategoryDataClientImpl(localDb: LocalDatabase) : CategoryDataClient {

    private val dao = localDb.categoryDao()

    override suspend fun create(name: String) = withIOContext {
        dao.insert(RoomCategory(name))
    }

    override suspend fun create(categories: List<RoomCategory>) = withIOContext {
        dao.insert(categories)
    }

    override fun get(id: Long) =
        dao
            .select(id)
            .map { roomToModel(it) }

    override suspend fun getSuspend(id: Long) = withIOContext {
        dao.selectSuspend(id)?.let(::roomToModel)
    }

    override fun get(name: String) =
        dao
            .select("%$name%")
            .map { results ->
                results.map { roomToModel(it) }
            }

    override suspend fun getCount() = dao.selectCount()

    override suspend fun update(category: Category) = withIOContext {
        dao.update(modelToRoom(category))
    }

    override suspend fun update(id: Long, name: String) = withIOContext {
        dao.update(id, name)
    }

    override suspend fun delete(id: Long) = withIOContext {
        dao.delete(id)
    }
}
