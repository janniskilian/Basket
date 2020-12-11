package de.janniskilian.basket.core.data.localdb.result

import androidx.room.ColumnInfo

data class RoomShoppingListItemResult(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "shoppingListId") val shoppingListId: Long,
    @ColumnInfo(name = "articleId") val articleId: Long,
    @ColumnInfo(name = "articleName") val articleName: String,
    @ColumnInfo(name = "categoryId") val categoryId: Long?,
    @ColumnInfo(name = "categoryName") val categoryName: String?,
    @ColumnInfo(name = "quantity") val quantity: String,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "checked") val isChecked: Boolean
)
