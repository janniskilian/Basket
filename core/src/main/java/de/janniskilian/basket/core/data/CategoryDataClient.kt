package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.type.domain.Category

interface CategoryDataClient {

    suspend fun create(name: String)

    suspend fun create(categories: List<RoomCategory>): List<Long>

    fun get(id: Long): LiveData<Category>

    suspend fun getSuspend(id: Long): Category?

    fun get(name: String = ""): LiveData<List<Category>>

    suspend fun getCount(): Int

    suspend fun update(category: Category)

    suspend fun update(id: Long, name: String)

    suspend fun delete(id: Long)
}
