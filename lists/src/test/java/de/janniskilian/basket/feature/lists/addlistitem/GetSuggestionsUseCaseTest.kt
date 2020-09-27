package de.janniskilian.basket.feature.lists.addlistitem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import de.janniskilian.basket.core.util.extension.extern.getOrAwaitValue
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class GetSuggestionsUseCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val bananas = Article(ArticleId(1), "Bananas", Category(CategoryId(1), "Produce"))
    private val apples = Article(ArticleId(2), "Apples", Category(CategoryId(1), "Produce"))
    private val clementines = Article(
        ArticleId(3),
        "Clementines",
        Category(CategoryId(1), "Produce")
    )

    private val articleDataClientResult = DefaultMutableLiveData(
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
    fun ascendingOrderingByArticleName() {
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
                .getOrAwaitValue()
        )
    }

    @Test
    fun createArticleSuggestion() {
        assertEquals(
            ShoppingListItemSuggestion(
                Article(ArticleId(0), "apple", null),
                isExistingListItem = false,
                isExistingArticle = false
            ),
            useCase
                .run(shoppingListId, "apple")
                .getOrAwaitValue()
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
                .getOrAwaitValue()
                .first()
        )
    }

    @Test
    fun addQuantityToSuggestions() {
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
                .getOrAwaitValue()
        )
    }
}
