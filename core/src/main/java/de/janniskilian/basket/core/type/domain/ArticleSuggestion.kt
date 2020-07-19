package de.janniskilian.basket.core.type.domain

data class ArticleSuggestion(
    val article: Article,
    val existingListItem: Boolean
)
