package de.janniskilian.basket.core.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingList")
data class RoomShoppingList(

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "searchName", index = true)
    val searchName: String,

    @ColumnInfo(name = "color")
    val color: Int,

    @ColumnInfo(name = "isGroupedByCategory")
    val isGroupedByCategory: Boolean,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0
)
