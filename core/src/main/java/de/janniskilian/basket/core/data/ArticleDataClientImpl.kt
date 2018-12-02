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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleDataClientImpl(localDb: LocalDatabase) : ArticleDataClient {

	private val dao = localDb.articleDao()

	override fun create(name: String, category: Category?) {
		GlobalScope.launch(Dispatchers.IO) {
			dao.insert(RoomArticle(name, category?.id))
		}
	}

	override fun createSync(name: String, category: Category?): Deferred<Article> =
		GlobalScope.async(Dispatchers.IO) {
			val id = dao.insert(RoomArticle(name, category?.id))
			roomToModel(dao.select(id))
		}

	override fun create(articles: List<RoomArticle>) {
		GlobalScope.launch(Dispatchers.IO) {
			dao.insert(articles)
		}
	}

	override suspend fun get(id: Long): Article =
		withContext(Dispatchers.IO) {
			roomToModel(dao.select(id))
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
			.select("%$name%")
			.map { results ->
				results.map { roomToModel(it) }
			}

	override fun update(article: Article) {
		GlobalScope.launch(Dispatchers.IO) {
			dao.update(modelToRoom(article))
		}
	}

	override fun delete(articleId: Long) {
		GlobalScope.launch(Dispatchers.IO) {
			dao.delete(articleId)
		}
	}
}
