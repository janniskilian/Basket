package de.janniskilian.basket.core.data.localdb.result

import androidx.room.Embedded
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory

data class RoomArticleSuggestionResult(
	val articleId: Long,

	val articleName: String,

	@Embedded(prefix = "category_")
	val category: RoomCategory?,

	val shoppingListId: Long?
)
