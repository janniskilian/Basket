package de.janniskilian.basket.feature.articles.article

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Category
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArticleFragmentUseCases(private val dataClient: DataClient) {

	fun createArticle(name: String, category: Category?): Job =
		dataClient.article.create(name, category)

	fun editArticle(articleId: Long, name: String, category: Category?): Job =
		GlobalScope.launch {
			dataClient.article.get(articleId)?.let {
				dataClient.article.update(
					it.copy(name = name, category = category)
				)
			}
		}

	fun deleteArticle(articleId: Long): Job =
		dataClient.article.delete(articleId)
}
