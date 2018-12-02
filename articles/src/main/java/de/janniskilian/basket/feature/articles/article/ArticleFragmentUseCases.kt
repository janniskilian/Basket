package de.janniskilian.basket.feature.articles.article

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Category
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticleFragmentUseCases(private val dataClient: DataClient) {

	fun createArticle(name: String, category: Category?) {
		dataClient.article.create(
			name,
			category
		)
	}

	fun editArticle(articleId: Long, name: String, category: Category?) {
		GlobalScope.launch {
			val article = dataClient.article.get(articleId)
			dataClient.article.update(
				article.copy(name = name, category = category)
			)
		}
	}

	fun deleteArticle(articleId: Long) {
		dataClient.article.delete(articleId)
	}
}
