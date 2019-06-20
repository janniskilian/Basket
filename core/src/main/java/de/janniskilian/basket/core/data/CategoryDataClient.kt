package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.CategoryId

interface CategoryDataClient {

    suspend fun create(name: String)

    suspend fun create(categories: List<RoomCategory>): List<Long>

    fun get(categoryId: CategoryId): LiveData<Category>

    suspend fun getSuspend(categoryId: CategoryId): Category?

    fun get(name: String = ""): LiveData<List<Category>>

    suspend fun getCount(): Int

    suspend fun update(category: Category)

    suspend fun update(categoryId: CategoryId, name: String)

    suspend fun delete(categoryId: CategoryId)
}
