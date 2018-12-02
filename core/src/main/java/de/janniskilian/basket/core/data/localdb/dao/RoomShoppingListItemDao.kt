package de.janniskilian.basket.core.data.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import de.janniskilian.basket.core.data.localdb.entity.RoomShoppingListItem
import de.janniskilian.basket.core.data.localdb.result.RoomShoppingListItemResult

@Dao
interface RoomShoppingListItemDao : RoomBaseDao<RoomShoppingListItem> {

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
			WHERE shoppingListItem.shoppingListId = :shoppingListId"""
	)
	fun selectAll(shoppingListId: Long): List<RoomShoppingListItemResult>

	@Query(
		"""SELECT COUNT(articleId)
			FROM shoppingListItem
			WHERE shoppingListId = :shoppingListId AND articleId = :articleId"""
	)
	fun selectArticleCount(shoppingListId: Long, articleId: Long): Int

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
