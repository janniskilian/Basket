package de.janniskilian.basket.feature.lists.addlistitem

/*import androidx.lifecycle.Observer
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
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class GetSuggestionsUseCaseTest {

    //@get:Rule
    //val rule = InstantTaskExecutorRule()

    private val observer: Observer<List<ShoppingListItemSuggestion>> = mock()

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
    fun ascendingOrderingByArticleName() = runBlocking {
        useCase
            .run(shoppingListId, "")
            .observeForever(observer)

        val captor = ArgumentCaptor.forClass<List, List<ShoppingListItemSuggestion>>(List<>::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(expectedUser, value)
        }

            .nextValue { result ->
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
                    result
                )
            }
    }

    @Test
    fun createArticleSuggestion() = runBlocking {
        useCase
            .run(shoppingListId, "apple")
            .nextValue { result ->
                assertEquals(
                    ShoppingListItemSuggestion(
                        Article(ArticleId(0), "apple", null),
                        isExistingListItem = false,
                        isExistingArticle = false
                    ),
                    result.first()
                )
            }

        useCase
            .run(shoppingListId, apples.name)
            .nextValue { result ->
                assertEquals(
                    ShoppingListItemSuggestion(
                        apples,
                        isExistingListItem = true,
                        isExistingArticle = true
                    ),
                    result.first()
                )
            }
    }

    @Test
    fun addQuantityToSuggestions() = runBlocking {
        val formatedQuantity = "2 kg"

        useCase
            .run(shoppingListId, "a 2kg")
            .nextValue { result ->
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
                    result
                )
            }
    }
}*/
