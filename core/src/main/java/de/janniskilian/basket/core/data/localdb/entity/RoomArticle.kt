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
			childColumns = ["categoryId"]
		)
	]
)
data class RoomArticle(

	@ColumnInfo(index = true)
	val name: String,

	@ColumnInfo(index = true)
	val categoryId: Long?,

	@PrimaryKey(autoGenerate = true)
	val id: Long = 0
)
