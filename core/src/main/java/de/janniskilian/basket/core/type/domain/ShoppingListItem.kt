package de.janniskilian.basket.core.type.domain

data class ShoppingListItem(
    val id: ShoppingListItemId,
    val shoppingListId: ShoppingListId,
    val article: Article,
    val quantity: String = "",
    val checked: Boolean = false
)
