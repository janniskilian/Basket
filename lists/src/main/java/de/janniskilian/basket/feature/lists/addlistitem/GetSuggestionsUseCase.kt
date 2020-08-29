package de.janniskilian.basket.feature.lists.addlistitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.function.addToFront
import java.util.*

class GetSuggestionsUseCase(private val dataClient: DataClient) {

    private val parser = ListItemInputParser()

    fun run(
        shoppingListId: ShoppingListId,
        input: String
    ): LiveData<List<ShoppingListItemSuggestion>> {
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
                        it.existingListItem,
                        true,
                        amount
                    )
                }
                .sortedBy { it.article.name }

            val exactMatchExists = lazy {
                result.any {
                    it.article.name.toLowerCase(Locale.ROOT) ==
                            parsedInput.name.toLowerCase(Locale.ROOT)
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
                        existingListItem = false,
                        existingArticle = false,
                        quantity = amount
                    )
                )
            }
        }
    }

}
