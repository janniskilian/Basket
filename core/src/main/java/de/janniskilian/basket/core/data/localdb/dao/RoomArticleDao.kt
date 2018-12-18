package de.janniskilian.basket.core.data.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.data.localdb.result.RoomArticleResult
import de.janniskilian.basket.core.data.localdb.result.RoomArticleSuggestionResult

@Dao
interface RoomArticleDao {

	@Insert
	suspend fun insert(article: RoomArticle): Long

	@Insert
	suspend fun insert(articles: List<RoomArticle>)

	@Query(
		"""SELECT article.id AS articleId, article.name AS articleName,
			category.id AS category_id, category.name AS category_name
			FROM article
			LEFT OUTER JOIN category
			ON article.categoryId = category.id
			WHERE article.id = :id"""
	)
	suspend fun select(id: Long): RoomArticleResult?

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

	@Query(SELECT_BY_NAME)
	fun select(name: String): LiveData<List<RoomArticleResult>>

	@Query(SELECT_BY_NAME)
	suspend fun selectSuspend(name: String): List<RoomArticleResult>

	@Update
	suspend fun update(article: RoomArticle)

    @Query("UPDATE article SET name = :name, categoryId = :categoryId WHERE id = :id")
    fun update(id: Long, name: String, categoryId: Long?)

	@Query("DELETE FROM article WHERE id = :id")
	fun delete(id: Long)

	companion object {

		private const val SELECT_BY_NAME =
			"""SELECT article.id AS articleId, article.name AS articleName,
			category.id AS category_id, category.name AS category_name
			FROM article
			LEFT OUTER JOIN category
			ON article.categoryId = category.id
			WHERE article.name LIKE :name
			ORDER BY article.name ASC"""
	}
}
