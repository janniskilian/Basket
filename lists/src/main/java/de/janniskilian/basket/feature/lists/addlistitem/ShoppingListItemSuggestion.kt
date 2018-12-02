package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.type.domain.Article

data class ShoppingListItemSuggestion(
	val article: Article,
	val existingListItem: Boolean,
	val existingArticle: Boolean,
	val quantity: String = ""
)
