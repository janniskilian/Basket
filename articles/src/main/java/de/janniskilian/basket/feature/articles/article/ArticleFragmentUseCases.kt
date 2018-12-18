package de.janniskilian.basket.feature.articles.article

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Category
import kotlinx.coroutines.Job

class ArticleFragmentUseCases(private val dataClient: DataClient) {

    fun createArticle(name: String, category: Category?): Job =
        dataClient.article.create(name, category)

    fun editArticle(articleId: Long, name: String, category: Category?): Job =
        dataClient.article.update(articleId, name, category?.id)

    fun deleteArticle(articleId: Long): Job =
        dataClient.article.delete(articleId)
}
