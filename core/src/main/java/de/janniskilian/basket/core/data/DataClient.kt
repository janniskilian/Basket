package de.janniskilian.basket.core.data

interface DataClient {

	val article: ArticleDataClient

	val category: CategoryDataClient

	val shoppingList: ShoppingListDataClient

	val shoppingListItem: ShoppingListItemDataClient
}
