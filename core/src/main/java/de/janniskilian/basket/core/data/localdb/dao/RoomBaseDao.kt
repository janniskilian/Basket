package de.janniskilian.basket.core.data.localdb.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface RoomBaseDao<in T> {

	@Insert
	fun insert(t: T): Long

	@Insert
	fun insert(ts: List<T>): List<Long>

	@Update
	fun update(t: T)

	@Delete
	fun delete(t: T)
}
