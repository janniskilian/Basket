package de.janniskilian.basket.core.data.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.data.localdb.result.RoomArticleResult
import de.janniskilian.basket.core.data.localdb.result.RoomArticleSuggestionResult

@Dao
interface RoomArticleDao : RoomBaseDao<RoomArticle> {

	@Query(
		"""SELECT article.id AS articleId, article.name AS articleName,
			category.id AS category_id, category.name AS category_name
			FROM article
			LEFT OUTER JOIN category
			ON article.categoryId = category.id
			WHERE article.id = :id"""
	)
	fun select(id: Long): RoomArticleResult

	@Query(
		"""SELECT DISTINCT article.id AS articleId, article.name AS articleName,
			category.id AS category_id, category.name AS category_name,
			listItem.shoppingListId AS shoppingListId
			FROM article
			LEFT OUTER JOIN category
			ON article.categoryId = category.id
			LEFT OUTER JOIN
			(SELECT articleId, shoppingListId FROM shoppingListItem
			WHERE shoppingListId = :shoppingListId) AS listItem
			ON article.id = listItem.articleId
			WHERE article.name LIKE :name"""
	)
	fun select(name: String, shoppingListId: Long): LiveData<List<RoomArticleSuggestionResult>>

	@Query(
		"""SELECT article.id AS articleId, article.name AS articleName,
			category.id AS category_id, category.name AS category_name
			FROM article
			LEFT OUTER JOIN category
			ON article.categoryId = category.id
			WHERE article.name LIKE :name
			ORDER BY article.name ASC"""
	)
	fun select(name: String): LiveData<List<RoomArticleResult>>

	@Query("DELETE FROM article WHERE id = :id")
	fun delete(id: Long)
}
