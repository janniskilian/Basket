package de.janniskilian.basket.core.data.localdb

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class LocalDatabaseMigration1To2 : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE shoppingList ADD COLUMN isGroupedByCategory INTEGER NOT NULL DEFAULT 1"
        )
    }
}
