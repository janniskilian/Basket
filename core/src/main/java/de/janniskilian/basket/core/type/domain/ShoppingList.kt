package de.janniskilian.basket.core.type.domain

data class ShoppingList(
    val id: ShoppingListId,
    override val name: String,
    val color: Int,
    val items: List<ShoppingListItem>
) : NamedItem {

    val isEmpty get() = items.isEmpty()

    val checkedItemCount: Int
        get() = items.count { it.checked }
}
