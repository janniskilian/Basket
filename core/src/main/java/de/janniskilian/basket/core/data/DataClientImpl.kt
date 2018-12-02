package de.janniskilian.basket.core.data

class DataClientImpl(
	override val article: ArticleDataClient,
	override val category: CategoryDataClient,
	override val shoppingList: ShoppingListDataClient,
	override val shoppingListItem: ShoppingListItemDataClient
) : DataClient
