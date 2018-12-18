package de.janniskilian.basket.core.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
	tableName = "article",
	foreignKeys = [
		ForeignKey(
			entity = RoomCategory::class,
			parentColumns = ["id"],
			childColumns = ["categoryId"],
			onDelete = ForeignKey.SET_NULL
		)
	]
)
data class RoomArticle(

	@ColumnInfo(name = "name", index = true)
	val name: String,

	@ColumnInfo(name = "categoryId", index = true)
	val categoryId: Long?,

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	val id: Long = 0
)
