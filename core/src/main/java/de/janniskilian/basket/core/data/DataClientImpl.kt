package de.janniskilian.basket.core.data

import de.janniskilian.basket.core.data.localdb.LocalDatabase

class DataClientImpl(
	override val article: ArticleDataClient,
	override val category: CategoryDataClient,
	override val shoppingList: ShoppingListDataClient,
	override val shoppingListItem: ShoppingListItemDataClient,
	private val localDatabase: LocalDatabase
) : DataClient {

	override fun close() {
		localDatabase.close()
	}

	override fun clear() {
		localDatabase.clearAllTables()
	}
}
