package de.janniskilian.basket.core.data.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory

@Dao
interface RoomCategoryDao {

    @Insert
    fun insert(category: RoomCategory)

    @Insert
    fun insert(categories: List<RoomCategory>): List<Long>

    @Query("SELECT * FROM category WHERE id = :id")
    fun select(id: Long): LiveData<RoomCategory>

    @Query("SELECT * FROM category WHERE id = :id")
    fun selectSuspend(id: Long): RoomCategory?

    @Query("SELECT * FROM category WHERE name LIKE :name ORDER BY name ASC")
    fun select(name: String): LiveData<List<RoomCategory>>

    @Query("SELECT COUNT(id) FROM category")
    fun selectCount(): Int

    @Update
    fun update(category: RoomCategory)

    @Query("UPDATE category SET name = :name WHERE id = :id")
    fun update(id: Long, name: String)

    @Query("DELETE FROM category WHERE id = :id")
    fun delete(id: Long)
}
