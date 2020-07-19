package de.janniskilian.basket.core.module

import de.janniskilian.basket.core.data.ArticleDataClient
import de.janniskilian.basket.core.data.ArticleDataClientImpl
import de.janniskilian.basket.core.data.CategoryDataClient
import de.janniskilian.basket.core.data.CategoryDataClientImpl
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.data.DataClientImpl
import de.janniskilian.basket.core.data.ShoppingListDataClient
import de.janniskilian.basket.core.data.ShoppingListDataClientImpl
import de.janniskilian.basket.core.data.ShoppingListItemDataClient
import de.janniskilian.basket.core.data.ShoppingListItemDataClientImpl
import de.janniskilian.basket.core.data.localdb.LocalDatabase

class DataModule(private val localDatabase: LocalDatabase) {

    val dataClient: DataClient by lazy {
        DataClientImpl(
            articleDataClient,
            categoryDataClient,
            shoppingListDataClient,
            shoppingListItemDataClient,
            localDatabase
        )
    }

    private val articleDataClient: ArticleDataClient by lazy {
        ArticleDataClientImpl(localDatabase)
    }

    private val categoryDataClient: CategoryDataClient by lazy {
        CategoryDataClientImpl(localDatabase)
    }

    private val shoppingListDataClient: ShoppingListDataClient by lazy {
        ShoppingListDataClientImpl(localDatabase)
    }

    private val shoppingListItemDataClient: ShoppingListItemDataClient by lazy {
        ShoppingListItemDataClientImpl(localDatabase)
    }
}
