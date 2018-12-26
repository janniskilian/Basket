package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleSuggestion
import de.janniskilian.basket.core.type.domain.Category

interface ArticleDataClient {

    suspend fun create(name: String, category: Category?): Article?

	suspend fun create(articles: List<RoomArticle>)

	suspend fun get(id: Long): Article?

	fun get(name: String, shoppingListId: Long): LiveData<List<ArticleSuggestion>>

	fun get(name: String): LiveData<List<Article>>

	suspend fun getSuspend(name: String): List<Article>

	suspend fun update(article: Article)

    suspend fun update(id: Long, name: String, categoryId: Long?)

    suspend fun delete(articleId: Long)

}
