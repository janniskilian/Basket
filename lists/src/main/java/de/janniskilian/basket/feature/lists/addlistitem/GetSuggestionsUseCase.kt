package de.janniskilian.basket.feature.lists.addlistitem

import androidx.lifecycle.LiveData
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.util.extension.extern.map
import de.janniskilian.basket.core.util.function.addToFront

class GetSuggestionsUseCase(private val shoppingListId: Long, private val dataClient: DataClient) {

	private val parser = ListItemInputParser()

	fun run(input: String): LiveData<List<ShoppingListItemSuggestion>> =
		getSuggestions(input)

	private fun getSuggestions(input: String): LiveData<List<ShoppingListItemSuggestion>> {
		val parsedInput = parser.parse(input)
		val articles = dataClient.article.get(parsedInput.name, shoppingListId)
		val amount = parsedInput.quantity.orEmpty() +
			parsedInput.unit?.let { " $it" }.orEmpty()

		return articles.map { result ->
			val itemSuggestions = result
				.map {
					ShoppingListItemSuggestion(
						it.article,
						it.existingListItem,
						true,
						amount
					)
				}
				.sortedBy { it.article.name }

			val exactMatchExists = lazy {
				result.any {
					it.article.name.toLowerCase() == parsedInput.name.toLowerCase()
				}
			}

			if (input.isBlank() || exactMatchExists.value) {
				itemSuggestions
			} else {
				itemSuggestions.addToFront(
					ShoppingListItemSuggestion(
						Article(
							0,
							parsedInput.name,
							null
						),
						false,
						false,
						amount
					)
				)
			}
		}
	}
}