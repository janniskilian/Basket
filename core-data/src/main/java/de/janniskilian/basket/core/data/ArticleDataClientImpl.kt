package de.janniskilian.basket.core.data

import de.janniskilian.basket.core.data.localdb.dao.RoomArticleDao
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.type.domain.ArticleSuggestion
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.CategoryId
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.function.withIOContext
import de.janniskilian.basket.core.util.withoutSpecialChars
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleDataClientImpl @Inject constructor(
    private val dao: RoomArticleDao
) : ArticleDataClient {

    override suspend fun create(name: String, category: Category?) = withIOContext {
        val id = dao.insert(RoomArticle(name, name.withoutSpecialChars(), category?.id?.value))
        get(ArticleId(id))
    }

    override suspend fun create(articles: List<RoomArticle>) = withIOContext {
        dao.insert(articles)
    }

    override suspend fun get(articleId: ArticleId): Article? = withIOContext {
        dao
            .select(articleId.value)
            ?.let(::roomToModel)
    }

    override fun get(
        name: String,
        shoppingListId: ShoppingListId
    ): Flow<List<ArticleSuggestion>> =
        dao
            .select("${name.withoutSpecialChars()}%", shoppingListId.value)
            .map { result ->
                result.map {
                    ArticleSuggestion(
                        Article(
                            ArticleId(it.articleId),
                            it.articleName,
                            it.category?.let(::roomToModel)
                        ),
                        it.shoppingListId == shoppingListId.value
                    )
                }
            }

    override fun get(name: String): Flow<List<Article>> {
        val withoutSpecialChars = name.withoutSpecialChars()
        return dao
            .select("$withoutSpecialChars%")
            .map { results ->
                results.map { roomToModel(it) }
            }
    }

    override suspend fun getSuspend(name: String) = withIOContext {
        dao
            .selectSuspend("${name.withoutSpecialChars()}%")
            .map(::roomToModel)
    }

    override suspend fun update(article: Article) = withIOContext {
        dao.update(modelToRoom(article))
    }

    override suspend fun update(articleId: ArticleId, name: String, categoryId: CategoryId?) =
        withIOContext {
            dao.update(
                articleId.value,
                name,
                name.withoutSpecialChars(),
                categoryId?.value
            )
        }

    override suspend fun delete(articleId: ArticleId) = withIOContext {
        dao.delete(articleId.value)
    }
}
