package de.janniskilian.basket.core.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.janniskilian.basket.core.data.localdb.LocalDatabase.Companion.DB_VERSION
import de.janniskilian.basket.core.data.localdb.dao.RoomArticleDao
import de.janniskilian.basket.core.data.localdb.dao.RoomCategoryDao
import de.janniskilian.basket.core.data.localdb.dao.RoomShoppingListDao
import de.janniskilian.basket.core.data.localdb.dao.RoomShoppingListItemDao
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.data.localdb.entity.RoomCategory
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingList
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingListItem

@Database(
    entities = [
        RoomArticle::class,
        RoomCategory::class,
        RoomShoppingList::class,
        RoomShoppingListItem::class
    ],
    version = DB_VERSION
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun articleDao(): RoomArticleDao

    abstract fun categoryDao(): RoomCategoryDao

    abstract fun shoppingListDao(): RoomShoppingListDao

    abstract fun shoppingListItemDao(): RoomShoppingListItemDao

    companion object {

        const val DB_VERSION = 2
        private const val DB_NAME = "APP_DATABASE"

        fun create(context: Context): LocalDatabase =
            Room
                .databaseBuilder(context, LocalDatabase::class.java, DB_NAME)
                .addMigrations(LocalDatabaseMigration1To2())
                .build()
    }
}
