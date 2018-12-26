package de.janniskilian.basket.feature.articles.article

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Category

class ArticleFragmentUseCases(private val dataClient: DataClient) {

    suspend fun createArticle(name: String, category: Category?) {
        dataClient.article.create(name, category)
    }

    suspend fun editArticle(articleId: Long, name: String, category: Category?) {
        dataClient.article.update(articleId, name, category?.id)
    }

    suspend fun deleteArticle(articleId: Long) {
        dataClient.article.delete(articleId)
    }
}
