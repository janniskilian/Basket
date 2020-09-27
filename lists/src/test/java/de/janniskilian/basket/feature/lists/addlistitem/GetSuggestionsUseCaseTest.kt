package de.janniskilian.basket.feature.lists.addlistitem

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import de.janniskilian.basket.core.data.ArticleDataClient
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.type.domain.ArticleSuggestion
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.type.domain.CategoryId
import de.janniskilian.basket.core.type.domain.ShoppingListId
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class GetSuggestionsUseCaseTest {

    private val bananas = Article(ArticleId(1), "Bananas", Category(CategoryId(1), "Produce"))
    private val apples = Article(ArticleId(2), "Apples", Category(CategoryId(1), "Produce"))
    private val clementines = Article(
        ArticleId(3),
        "Clementines",
        Category(CategoryId(1), "Produce")
    )

    private val articleDataClientResult = flowOf(
        listOf(
            ArticleSuggestion(bananas, false),
            ArticleSuggestion(apples, true),
            ArticleSuggestion(clementines, false)
        )
    )

    private val articleDataClient: ArticleDataClient = mock {
        on { get(any(), ShoppingListId(any())) } doReturn articleDataClientResult
    }

    private val dataClient: DataClient = mock {
        on { article } doReturn articleDataClient
    }

    private val shoppingListId = ShoppingListId(1)

    private val useCase = GetSuggestionsUseCase(dataClient)

    @Test
    fun ascendingOrderingByArticleName() = runBlocking {
        assertEquals(
            listOf(
                ShoppingListItemSuggestion(
                    apples,
                    isExistingListItem = true,
                    isExistingArticle = true
                ),
                ShoppingListItemSuggestion(
                    bananas,
                    isExistingListItem = false,
                    isExistingArticle = true
                ),
                ShoppingListItemSuggestion(
                    clementines,
                    isExistingListItem = false,
                    isExistingArticle = true
                )
            ),
            useCase
                .run(shoppingListId, "")
                .single()
        )
    }

    @Test
    fun createArticleSuggestion() = runBlocking {
        assertEquals(
            ShoppingListItemSuggestion(
                Article(ArticleId(0), "apple", null),
                isExistingListItem = false,
                isExistingArticle = false
            ),
            useCase
                .run(shoppingListId, "apple")
                .single()
                .first()
        )


        assertEquals(
            ShoppingListItemSuggestion(
                apples,
                isExistingListItem = true,
                isExistingArticle = true
            ),
            useCase
                .run(shoppingListId, apples.name)
                .single()
                .first()
        )
    }

    @Test
    fun addQuantityToSuggestions() = runBlocking {
        val formatedQuantity = "2 kg"

        assertEquals(
            listOf(
                ShoppingListItemSuggestion(
                    Article(ArticleId(0), "a", null),
                    isExistingListItem = false,
                    isExistingArticle = false,
                    quantity = formatedQuantity
                ),
                ShoppingListItemSuggestion(
                    apples,
                    isExistingListItem = true,
                    isExistingArticle = true,
                    quantity = formatedQuantity
                ),
                ShoppingListItemSuggestion(
                    bananas,
                    isExistingListItem = false,
                    isExistingArticle = true,
                    quantity = formatedQuantity
                ),
                ShoppingListItemSuggestion(
                    clementines,
                    isExistingListItem = false,
                    isExistingArticle = true,
                    quantity = formatedQuantity
                )
            ),
            useCase
                .run(shoppingListId, "a 2kg")
                .single()
        )
    }
}
