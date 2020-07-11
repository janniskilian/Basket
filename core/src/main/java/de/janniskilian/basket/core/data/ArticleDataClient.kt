package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.type.domain.ArticleSuggestion
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.CategoryId
import de.janniskilian.basket.core.type.domain.ShoppingListId

interface ArticleDataClient {

    suspend fun create(name: String, category: Category?): Article?

    suspend fun create(articles: List<RoomArticle>)

    suspend fun get(articleId: ArticleId): Article?

    fun get(name: String, shoppingListId: ShoppingListId): LiveData<List<ArticleSuggestion>>

    fun get(name: String): LiveData<List<Article>>

    suspend fun getSuspend(name: String): List<Article>

    suspend fun update(article: Article)

    suspend fun update(articleId: ArticleId, name: String, categoryId: CategoryId?)

    suspend fun delete(articleId: ArticleId)

}
