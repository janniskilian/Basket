package de.janniskilian.basket.core.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingList")
data class RoomShoppingList(

	@ColumnInfo(name = "name", index = true)
	val name: String,

	@ColumnInfo(name = "color")
	val color: Int,

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	val id: Long = 0
)
