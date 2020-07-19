package de.janniskilian.basket.core.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "shoppingListItem",
    foreignKeys = [
        ForeignKey(
            entity = RoomShoppingList::class,
            parentColumns = ["id"],
            childColumns = ["shoppingListId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoomArticle::class,
            parentColumns = ["id"],
            childColumns = ["articleId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class RoomShoppingListItem(

    @ColumnInfo(name = "shoppingListId", index = true)
    val shoppingListId: Long,

    @ColumnInfo(name = "articleId", index = true)
    val articleId: Long,

    @ColumnInfo(name = "quantity")
    val quantity: String = "",

    @ColumnInfo(name = "comment")
    val comment: String = "",

    @ColumnInfo(name = "checked")
    val checked: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0
)
