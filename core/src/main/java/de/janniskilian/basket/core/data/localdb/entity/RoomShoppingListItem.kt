package de.janniskilian.basket.core.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
	tableName = "shoppingListItem",
	foreignKeys = [
		ForeignKey(
			entity = RoomShoppingList::class,
			parentColumns = ["id"],
			childColumns = ["shoppingListId"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = RoomArticle::class,
			parentColumns = ["id"],
			childColumns = ["articleId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
class RoomShoppingListItem(

	@ColumnInfo(index = true)
	val shoppingListId: Long,

	@ColumnInfo(index = true)
	val articleId: Long,

	val quantity: String = "",

	val checked: Boolean = false,

	@PrimaryKey(autoGenerate = true)
	val id: Long = 0
)
