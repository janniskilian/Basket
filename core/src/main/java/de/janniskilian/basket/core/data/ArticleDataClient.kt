package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleSuggestion
import de.janniskilian.basket.core.type.domain.Category
import kotlinx.coroutines.Deferred

interface ArticleDataClient {

	fun create(name: String, category: Category?)

	fun createSync(name: String, category: Category?): Deferred<Article>

	fun create(articles: List<RoomArticle>)

	suspend fun get(id: Long): Article

	fun get(name: String, shoppingListId: Long): LiveData<List<ArticleSuggestion>>

	fun get(name: String): LiveData<List<Article>>

	fun update(article: Article)

	fun delete(articleId: Long)
}
