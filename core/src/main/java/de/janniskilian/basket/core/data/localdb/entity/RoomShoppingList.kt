package de.janniskilian.basket.core.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingList")
data class RoomShoppingList(
	@ColumnInfo(index = true)
	val name: String,

	val color: Int,

	@PrimaryKey(autoGenerate = true)
	val id: Long = 0
)
