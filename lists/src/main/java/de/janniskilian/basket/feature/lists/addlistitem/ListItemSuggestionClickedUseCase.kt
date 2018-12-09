package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Article

class ListItemSuggestionClickedUseCase(
	private val shoppingListId: Long,
	private val dataClient: DataClient
) {

	suspend fun run(suggestion: ShoppingListItemSuggestion) {
		when {
			suggestion.existingListItem ->
				dataClient.shoppingListItem
					.delete(shoppingListId, suggestion.article.id)
					.join()

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
		dataClient.article.createSuspend(
			suggestion.article.name,
			suggestion.article.category
		)?.let {
			createShoppingListItem(it, suggestion.quantity)
		}
	}
}
