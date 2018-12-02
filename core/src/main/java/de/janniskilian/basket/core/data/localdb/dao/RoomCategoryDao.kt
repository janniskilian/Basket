package de.janniskilian.basket.core.data.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory

@Dao
interface RoomCategoryDao : RoomBaseDao<RoomCategory> {

	@Query("SELECT * FROM category WHERE id = :id")
	fun select(id: Long): LiveData<RoomCategory>

	@Query("SELECT * FROM category WHERE name LIKE :name ORDER BY name ASC")
	fun select(name: String): LiveData<List<RoomCategory>>

	@Query("SELECT COUNT(id) FROM category")
	fun selectCount(): Int
}
