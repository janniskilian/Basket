package de.janniskilian.basket.core.data.localdb.result

data class RoomShoppingListItemResult(
    val id: Long,
    val shoppingListId: Long,
    val articleId: Long,
    val articleName: String,
    val categoryId: Long?,
    val categoryName: String?,
    val quantity: String,
    val comment: String,
    val checked: Boolean
)
