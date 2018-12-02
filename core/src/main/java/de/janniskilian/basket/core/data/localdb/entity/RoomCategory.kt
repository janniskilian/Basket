package de.janniskilian.basket.core.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class RoomCategory(

	@ColumnInfo(index = true)
	val name: String,

	@PrimaryKey(autoGenerate = true)
	val id: Long = 0
)
