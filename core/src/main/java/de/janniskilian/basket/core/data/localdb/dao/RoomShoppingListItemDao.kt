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
	suspend fun insert(shoppingListItem: RoomShoppingListItem)

	@Insert
	suspend fun insert(shoppingListItems: List<RoomShoppingListItem>)

	@Query(
		"""SELECT shoppingListItem.id AS id, shoppingListItem.shoppingListId AS shoppingListId,
			shoppingListItem.quantity AS quantity, shoppingListItem.checked AS checked,
			article.id AS articleId, article.name AS articleName, article.categoryId AS categoryId,
			category.name AS categoryName
			FROM shoppingListItem
			LEFT OUTER JOIN article
			ON shoppingListItem.articleId = article.id
			LEFT OUTER JOIN category
			ON article.categoryId = category.id
			WHERE shoppingListItem.id = :id"""
	)
	fun select(id: Long): LiveData<RoomShoppingListItemResult>

	@Update
	suspend fun update(shoppingListItem: RoomShoppingListItem)

	@Query("UPDATE shoppingListItem SET checked = :checked WHERE shoppingListId = :shoppingListId")
	fun setAllCheckedForShoppingList(
		shoppingListId: Long,
		checked: Boolean
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
}