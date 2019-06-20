package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ShoppingListId

class ListItemSuggestionClickedUseCase(
    private val shoppingListId: ShoppingListId,
    private val dataClient: DataClient
) {

    suspend fun run(suggestion: ShoppingListItemSuggestion) {
        when {
            suggestion.existingListItem ->
                dataClient.shoppingListItem.delete(shoppingListId, suggestion.article.id)

            suggestion.existingArticle ->
                createShoppingListItem(suggestion.article, suggestion.quantity)

            else -> createArticleAndShoppingListItem(suggestion)
        }
    }

    private suspend fun createShoppingListItem(article: Article, quantity: String = "") {
        dataClient.shoppingListItem.create(
            shoppingListId,
            article,
            quantity
        )
    }

    private suspend fun createArticleAndShoppingListItem(suggestion: ShoppingListItemSuggestion) {
        dataClient.article.create(
            suggestion.article.name,
            suggestion.article.category
        )?.let {
            createShoppingListItem(it, suggestion.quantity)
        }
    }
}
