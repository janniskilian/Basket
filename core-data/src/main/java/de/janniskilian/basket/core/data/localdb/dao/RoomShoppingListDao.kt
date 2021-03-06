package de.janniskilian.basket.core.data.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingList
import de.janniskilian.basket.core.data.localdb.result.RoomShoppingListResult
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomShoppingListDao {

    @Insert
    fun insert(shoppingList: RoomShoppingList): Long

    @Query("$SELECT_QUERY WHERE shoppingList.id = :id")
    fun select(id: Long): List<RoomShoppingListResult>

    @Query("$SELECT_QUERY WHERE shoppingList.id = :id")
    fun selectLiveData(id: Long): Flow<List<RoomShoppingListResult>>

    @Query(SELECT_QUERY)
    fun selectAll(): Flow<List<RoomShoppingListResult>>

    @Query(
        """UPDATE shoppingList 
			SET name = :name,
            searchName = :searchName,
            color = :color,
            isGroupedByCategory = :isGroupedByCategory
			WHERE id = :id"""
    )
    fun update(id: Long, name: String, searchName: String, color: Int, isGroupedByCategory: Boolean)

    @Query("DELETE FROM shoppingList WHERE id = :id")
    fun delete(id: Long)

    companion object {

        private const val SELECT_QUERY =
            """SELECT shoppingList.id AS shoppingListId,
				shoppingList.name AS shoppingListName,
				shoppingList.color AS color,
				shoppingList.isGroupedByCategory AS isGroupedByCategory,
				shoppingListItem.id AS shoppingListItem_id,
				shoppingListItem.shoppingListId AS shoppingListItem_shoppingListId,
				shoppingListItem.quantity AS shoppingListItem_quantity,
				shoppingListItem.comment AS shoppingListItem_comment,
				shoppingListItem.checked AS shoppingListItem_checked,
				article.id AS shoppingListItem_articleId,
				article.name AS shoppingListItem_articleName,
				category.id AS shoppingListItem_categoryId,
				category.name AS shoppingListItem_categoryName,
                category.searchName AS category_searchName
				FROM shoppingList
				LEFT OUTER JOIN shoppingListItem ON shoppingList.id = shoppingListItem.shoppingListId
				LEFT OUTER JOIN article ON shoppingListItem.articleId = article.id
				LEFT OUTER JOIN category ON article.categoryId = category.id"""
    }
}
