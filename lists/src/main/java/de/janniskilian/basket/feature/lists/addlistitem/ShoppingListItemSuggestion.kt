package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.NamedItem

data class ShoppingListItemSuggestion(
    val article: Article,
    val isExistingListItem: Boolean,
    val isExistingArticle: Boolean,
    val quantity: String = ""
) : NamedItem {

    override val name: String
        get() = article.name
}
