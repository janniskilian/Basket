package de.janniskilian.basket.core.type.domain

data class ShoppingListItem(
	val id: Long,
	val shoppingListId: Long,
	val article: Article,
	val quantity: String = "",
	val checked: Boolean = false
)
