package de.janniskilian.basket.core.data.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingListItem
import de.janniskilian.basket.core.data.localdb.result.RoomShoppingListItemResult

@Dao
interface RoomShoppingListItemDao {

    @Insert
    fun insert(shoppingListItem: RoomShoppingListItem)

    @Insert
    fun insert(shoppingListItems: List<RoomShoppingListItem>)

    @Query(SELECT_QUERY)
    fun select(id: Long): RoomShoppingListItemResult?

    @Query(SELECT_QUERY)
    fun selectLiveData(id: Long): LiveData<RoomShoppingListItemResult>

    @Update
    fun update(shoppingListItem: RoomShoppingListItem)

    @Query("UPDATE shoppingListItem SET checked = :isChecked WHERE shoppingListId = :shoppingListId")
    fun setAllCheckedForShoppingList(
        shoppingListId: Long,
        isChecked: Boolean
    )

    @Query(
        """DELETE FROM shoppingListItem
			WHERE shoppingListId = :shoppingListId AND articleId = :articleId"""
    )
    fun delete(shoppingListId: Long, articleId: Long)

    @Query("DELETE FROM shoppingListItem WHERE shoppingListId = :shoppingListId")
    fun deleteAllForShoppingList(shoppingListId: Long)

    @Query("DELETE FROM shoppingListItem WHERE shoppingListId = :shoppingListId AND checked = 1")
    fun deleteAllCheckedForShoppingList(shoppingListId: Long)

    companion object {

        private const val SELECT_QUERY =
            """SELECT shoppingListItem.id AS id,
                shoppingListItem.shoppingListId AS shoppingListId,
                shoppingListItem.quantity AS quantity,
                shoppingListItem.comment AS comment, 
                shoppingListItem.checked AS checked,
                article.id AS articleId,
                article.name AS articleName,
                article.categoryId AS categoryId,
                category.name AS categoryName
                FROM shoppingListItem
                LEFT OUTER JOIN article
                ON shoppingListItem.articleId = article.id
                LEFT OUTER JOIN category
                ON article.categoryId = category.id
                WHERE shoppingListItem.id = :id"""
    }
}
