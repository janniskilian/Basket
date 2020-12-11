package de.janniskilian.basket.core.data

import java.io.Closeable

interface DataClient : Closeable {

    val article: ArticleDataClient

    val category: CategoryDataClient

    val shoppingList: ShoppingListDataClient

    val shoppingListItem: ShoppingListItemDataClient

    fun clear()
}
