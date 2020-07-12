package de.janniskilian.basket.core.type.domain

data class ShoppingListItem(
    val id: ShoppingListItemId,
    val shoppingListId: ShoppingListId,
    val article: Article,
    val quantity: String = "",
    val comment: String = "",
    val checked: Boolean = false
) : NamedItem {

    override val name: String
        get() = article.name
}
