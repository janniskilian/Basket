package de.janniskilian.basket.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataClientModule {

    @Singleton
    @Binds
    abstract fun provideDataClient(dataClientImpl: DataClientImpl): DataClient

    @Singleton
    @Binds
    abstract fun provideArticleDataClient(
        articleDataClientImpl: ArticleDataClientImpl
    ): ArticleDataClient

    @Singleton
    @Binds
    abstract fun provideCategoryDataClient(
        categoryDataClientImpl: CategoryDataClientImpl
    ): CategoryDataClient

    @Singleton
    @Binds
    abstract fun provideShoppingListDataClient(
        shoppingListDataClientImpl: ShoppingListDataClientImpl
    ): ShoppingListDataClient

    @Singleton
    @Binds
    abstract fun provideShoppingListItemDataClient(
        shoppingListItemDataClientImpl: ShoppingListItemDataClientImpl
    ): ShoppingListItemDataClient
}
