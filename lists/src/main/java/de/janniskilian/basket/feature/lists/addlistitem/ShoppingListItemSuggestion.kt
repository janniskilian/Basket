package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.NamedItem

data class ShoppingListItemSuggestion(
    val article: Article,
    val existingListItem: Boolean,
    val existingArticle: Boolean,
    val quantity: String = ""
) : NamedItem {

    override val name: String
        get() = article.name
}
