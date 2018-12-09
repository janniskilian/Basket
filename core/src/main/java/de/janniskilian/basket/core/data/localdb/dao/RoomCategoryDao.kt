package de.janniskilian.basket.core.data.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory

@Dao
interface RoomCategoryDao {

	@Insert
	suspend fun insert(category: RoomCategory): Long

	@Insert
	suspend fun insert(categories: List<RoomCategory>): List<Long>

	@Query("SELECT * FROM category WHERE id = :id")
	fun select(id: Long): LiveData<RoomCategory>

	@Query("SELECT * FROM category WHERE id = :id")
	suspend fun selectSuspend(id: Long): RoomCategory?

	@Query("SELECT * FROM category WHERE name LIKE :name ORDER BY name ASC")
	fun select(name: String): LiveData<List<RoomCategory>>

	@Query("SELECT COUNT(id) FROM category")
	suspend fun selectCount(): Int

	@Update
	suspend fun update(category: RoomCategory)

	@Delete
	suspend fun delete(category: RoomCategory)
}
