package de.janniskilian.basket.core.type.domain

data class ShoppingList(
    val id: ShoppingListId,
    val name: String,
    val color: Int,
    val items: List<ShoppingListItem>
) {

	val isEmpty get() = items.isEmpty()

	val checkedItemCount: Int
		get() = items.count { it.checked }
}
