package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.function.addToFront
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSuggestionsUseCase(private val dataClient: DataClient) {

    private val parser = ListItemInputParser()

    fun run(
        shoppingListId: ShoppingListId,
        input: String
    ): Flow<List<ShoppingListItemSuggestion>> {
        val parsedInput = parser.parse(input)
        val articles = dataClient.article.get(parsedInput.name, shoppingListId)
        val amount = parsedInput.quantity.orEmpty() +
                parsedInput.unit
                    ?.let { " $it" }
                    .orEmpty()

        return articles.map { result ->
            val itemSuggestions = result
                .map {
                    ShoppingListItemSuggestion(
                        it.article,
                        it.isExistingListItem,
                        true,
                        amount
                    )
                }
                .sortedBy { it.article.name }

            val exactMatchExists = lazy {
                result.any {
                    it.article.name.equals(parsedInput.name, ignoreCase = true)
                }
            }

            if (input.isBlank() || exactMatchExists.value) {
                itemSuggestions
            } else {
                itemSuggestions.addToFront(
                    ShoppingListItemSuggestion(
                        Article(
                            ArticleId(0),
                            parsedInput.name,
                            null
                        ),
                        isExistingListItem = false,
                        isExistingArticle = false,
                        quantity = amount
                    )
                )
            }
        }
    }
}
