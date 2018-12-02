package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.type.domain.Category
import kotlinx.coroutines.Deferred

interface CategoryDataClient {

	fun create(name: String)

	fun create(categories: List<RoomCategory>): Deferred<List<Long>>

	fun get(id: Long): LiveData<Category>

	fun get(name: String = ""): LiveData<List<Category>>

	fun getCount(): Deferred<Int>

	fun update(category: Category)

	fun delete(category: Category)
}
