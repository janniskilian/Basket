package de.janniskilian.basket.test

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import de.janniskilian.basket.core.data.ArticleDataClientImpl
import de.janniskilian.basket.core.data.CategoryDataClientImpl
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.data.DataClientImpl
import de.janniskilian.basket.core.data.DefaultDataImporter
import de.janniskilian.basket.core.data.DefaultDataLoader
import de.janniskilian.basket.core.data.ShoppingListDataClientImpl
import de.janniskilian.basket.core.data.ShoppingListItemDataClientImpl
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import kotlinx.coroutines.runBlocking

fun createTestDataClient(): DataClient {
    val application = ApplicationProvider.getApplicationContext<Application>()

    val localDatabase = Room
        .inMemoryDatabaseBuilder(application, LocalDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    val dataClient = DataClientImpl(
        ArticleDataClientImpl(localDatabase.articleDao()),
        CategoryDataClientImpl(localDatabase.categoryDao()),
        ShoppingListDataClientImpl(localDatabase.shoppingListDao()),
        ShoppingListItemDataClientImpl(localDatabase.shoppingListItemDao()),
        localDatabase
    )

    runBlocking {
        DefaultDataImporter(dataClient, DefaultDataLoader(application)).run()
    }

    return dataClient
}
