package de.janniskilian.basket.core.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class RoomCategory(

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "searchName", index = true)
    val searchName: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0
)
