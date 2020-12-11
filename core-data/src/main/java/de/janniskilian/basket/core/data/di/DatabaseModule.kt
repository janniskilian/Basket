package de.janniskilian.basket.core.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.dao.RoomArticleDao
import de.janniskilian.basket.core.data.localdb.dao.RoomCategoryDao
import de.janniskilian.basket.core.data.localdb.dao.RoomShoppingListDao
import de.janniskilian.basket.core.data.localdb.dao.RoomShoppingListItemDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase =
        LocalDatabase.create(context)

    @Singleton
    @Provides
    fun provideRoomArticleDao(localDatabase: LocalDatabase): RoomArticleDao =
        localDatabase.articleDao()

    @Singleton
    @Provides
    fun provideRoomCategoryDao(localDatabase: LocalDatabase): RoomCategoryDao =
        localDatabase.categoryDao()

    @Singleton
    @Provides
    fun provideRoomShoppingListDao(localDatabase: LocalDatabase): RoomShoppingListDao =
        localDatabase.shoppingListDao()

    @Singleton
    @Provides
    fun provideRoomShoppingItemListDao(localDatabase: LocalDatabase): RoomShoppingListItemDao =
        localDatabase.shoppingListItemDao()
}
