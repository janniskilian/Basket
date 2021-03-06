package de.janniskilian.basket.core.data.localdb.result

import androidx.room.Embedded

data class RoomShoppingListResult(
    val shoppingListId: Long,
    val shoppingListName: String,
    val color: Int,
    val isGroupedByCategory: Boolean,
    @Embedded(prefix = "shoppingListItem_")
    val shoppingListItem: RoomShoppingListItemResult?
)

