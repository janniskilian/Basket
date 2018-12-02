package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Article
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListItemSuggestionClickedUseCase(
	private val shoppingListId: Long,
	private val dataClient: DataClient
) {

	fun run(suggestion: ShoppingListItemSuggestion) {
		GlobalScope.launch {
			when {
				suggestion.existingListItem ->
					dataClient.shoppingListItem.delete(shoppingListId, suggestion.article.id)

				suggestion.existingArticle ->
					createShoppingListItem(suggestion.article, suggestion.quantity)

				else -> createArticleAndShoppingListItem(suggestion)
			}
		}
	}

	private fun createShoppingListItem(article: Article, quantity: String = "") {
		dataClient.shoppingListItem.create(
			shoppingListId,
			article,
			quantity
		)
	}

	private suspend fun createArticleAndShoppingListItem(suggestion: ShoppingListItemSuggestion) {
		createShoppingListItem(
			dataClient
				.article
				.createSync(suggestion.article.name, suggestion.article.category)
				.await(),
			suggestion.quantity
		)
	}
}
