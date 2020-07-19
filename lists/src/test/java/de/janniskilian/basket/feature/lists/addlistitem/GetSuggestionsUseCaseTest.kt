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
import de.janniskilian.basket.core.util.extension.extern.nextValue
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class GetSuggestionsUseCaseTest {

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

    private val useCase = GetSuggestionsUseCase(ShoppingListId(1), dataClient)

    @Test
    fun `ascending ordering by article name`() = runBlocking {
        useCase.run("").nextValue { result ->
            assertEquals(
                listOf(
                    ShoppingListItemSuggestion(
                        apples,
                        existingListItem = true,
                        existingArticle = true
                    ),
                    ShoppingListItemSuggestion(
                        bananas,
                        existingListItem = false,
                        existingArticle = true
                    ),
                    ShoppingListItemSuggestion(
                        clementines,
                        existingListItem = false,
                        existingArticle = true
                    )
                ),
                result
            )
        }
    }

    @Test
    fun `create article suggestion`() = runBlocking {
        useCase.run("apple").nextValue { result ->
            assertEquals(
                ShoppingListItemSuggestion(
                    Article(ArticleId(0), "apple", null),
                    existingListItem = false,
                    existingArticle = false
                ),
                result.first()
            )
        }

        useCase.run(apples.name).nextValue { result ->
            assertEquals(
                ShoppingListItemSuggestion(apples, existingListItem = true, existingArticle = true),
                result.first()
            )
        }
    }

    @Test
    fun `add quantity to suggestions`() = runBlocking {
        val formatedQuantity = "2 kg"

        useCase.run("a 2kg").nextValue { result ->
            assertEquals(
                listOf(
                    ShoppingListItemSuggestion(
                        Article(ArticleId(0), "a", null),
                        existingListItem = false,
                        existingArticle = false,
                        quantity = formatedQuantity
                    ),
                    ShoppingListItemSuggestion(
                        apples,
                        existingListItem = true,
                        existingArticle = true,
                        quantity = formatedQuantity
                    ),
                    ShoppingListItemSuggestion(
                        bananas,
                        existingListItem = false,
                        existingArticle = true,
                        quantity = formatedQuantity
                    ),
                    ShoppingListItemSuggestion(
                        clementines,
                        existingListItem = false,
                        existingArticle = true,
                        quantity = formatedQuantity
                    )
                ),
                result
            )
        }
    }
}
