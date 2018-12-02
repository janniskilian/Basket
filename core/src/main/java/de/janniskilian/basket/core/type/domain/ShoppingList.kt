package de.janniskilian.basket.core.type.domain

data class ShoppingList(
	val id: Long,
	val name: String,
	val color: Int,
	val items: List<ShoppingListItem>
) {

	val checkedItemCount: Int
		get() = items.count { it.checked }
}
