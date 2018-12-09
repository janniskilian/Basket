package de.janniskilian.basket.core.data

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.data.localdb.entity.RoomArticle
import de.janniskilian.basket.core.data.localdb.transformation.modelToRoom
import de.janniskilian.basket.core.data.localdb.transformation.roomToModel
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleSuggestion
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.map
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticleDataClientImpl(localDb: LocalDatabase) : ArticleDataClient {

	private val dao = localDb.articleDao()

	override fun create(name: String, category: Category?) =
		GlobalScope.launch { dao.insert(RoomArticle(name, category?.id)) }

	override suspend fun createSuspend(name: String, category: Category?): Article? {
		val id = dao.insert(RoomArticle(name, category?.id))
		return get(id)
	}

	override suspend fun create(articles: List<RoomArticle>) {
		dao.insert(articles)
	}

	override suspend fun get(id: Long): Article? =
		dao.select(id)?.let(::roomToModel)

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
			.select("%$name%")
			.map { results ->
				results.map { roomToModel(it) }
			}

	override suspend fun getSuspend(name: String): List<Article> =
		dao
			.selectSuspend("%$name%")
			.map(::roomToModel)

	override suspend fun update(article: Article) {
		dao.update(modelToRoom(article))
	}

	override fun delete(articleId: Long) = GlobalScope.launch { dao.delete(articleId) }
}
