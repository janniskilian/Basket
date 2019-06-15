package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleSuggestion
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.function.withIOContext

class ArticleDataClientImpl(localDb: LocalDatabase) : ArticleDataClient {

    private val dao = localDb.articleDao()

    override suspend fun create(name: String, category: Category?) = withIOContext {
        val id = dao.insert(RoomArticle(name, category?.id))
        get(id)
    }

    override suspend fun create(articles: List<RoomArticle>) = withIOContext {
        dao.insert(articles)
    }

    override suspend fun get(id: Long): Article? = withIOContext {
        dao.select(id)?.let(::roomToModel)
    }

    override fun get(name: String, shoppingListId: Long): LiveData<List<ArticleSuggestion>> =
        dao
            .select("$name%", shoppingListId)
            .map { result ->
                result.map { it ->
                    ArticleSuggestion(
                        Article(
                            it.articleId,
                            it.articleName,
                            it.category?.let(::roomToModel)
                        ),
                        it.shoppingListId == shoppingListId
                    )
                }
            }

    override fun get(name: String): LiveData<List<Article>> =
        dao
            .select("$name%")
            .map { results ->
                results.map { roomToModel(it) }
            }

    override suspend fun getSuspend(name: String) = withIOContext {
        dao
            .selectSuspend("$name%")
            .map(::roomToModel)
    }

    override suspend fun update(article: Article) = withIOContext {
        dao.update(modelToRoom(article))
    }

    override suspend fun update(id: Long, name: String, categoryId: Long?) = withIOContext {
        dao.update(id, name, categoryId)
    }

    override suspend fun delete(articleId: Long) = withIOContext {
        dao.delete(articleId)
    }
}
