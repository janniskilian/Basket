package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.type.domain.Category
import kotlinx.coroutines.Job

interface CategoryDataClient {

	fun create(name: String): Job

	suspend fun create(categories: List<RoomCategory>): List<Long>

	fun get(id: Long): LiveData<Category>

	suspend fun getSuspend(id: Long): Category?

	fun get(name: String = ""): LiveData<List<Category>>

	suspend fun getCount(): Int

	fun update(category: Category): Job

    fun update(id: Long, name: String): Job

    fun delete(id: Long): Job
}
