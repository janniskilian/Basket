package de.janniskilian.basket.core.type.domain

data class Article(
    val id: ArticleId,
    val name: String,
    val category: Category?
)
