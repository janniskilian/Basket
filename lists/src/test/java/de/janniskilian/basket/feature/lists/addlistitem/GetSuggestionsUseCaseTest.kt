package de.janniskilian.basket.feature.lists.addlistitem

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import de.janniskilian.basket.core.data.ArticleDataClient
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ArticleSuggestion
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.nextValue
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class GetSuggestionsUseCaseTest {

	private val bananas = Article(1, "Bananas", Category(1, "Produce"))
	private val apples = Article(2, "Apples", Category(1, "Produce"))
	private val clementines = Article(3, "Clementines", Category(1, "Produce"))

	private val articleDataClientResult = DefaultMutableLiveData(
		listOf(
			ArticleSuggestion(bananas, false),
			ArticleSuggestion(apples, true),
			ArticleSuggestion(clementines, false)
		)
	)

	private val articleDataClient: ArticleDataClient = mock {
		on { get(any(), any()) } doReturn articleDataClientResult
	}

	private val dataClient: DataClient = mock {
		on { article } doReturn articleDataClient
	}

	private val useCase = GetSuggestionsUseCase(1, dataClient)

	@Test
	fun `ascending ordering by article name`() = runBlocking {
		useCase.run("").nextValue { result ->
			assertEquals(
				listOf(
					ShoppingListItemSuggestion(apples, true, true),
					ShoppingListItemSuggestion(bananas, false, true),
					ShoppingListItemSuggestion(clementines, false, true)
				),
				result
			)
		}
	}

	@Test
	fun `create article suggestion`() = runBlocking {
		useCase.run("apple").nextValue { result ->
			assertEquals(
				ShoppingListItemSuggestion(Article(0, "apple", null), false, false),
				result.first()
			)
		}

		useCase.run(apples.name).nextValue { result ->
			assertEquals(
				ShoppingListItemSuggestion(apples, true, true),
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
						Article(0, "a", null),
						false,
						false,
						formatedQuantity
					),
					ShoppingListItemSuggestion(apples, true, true, formatedQuantity),
					ShoppingListItemSuggestion(bananas, false, true, formatedQuantity),
					ShoppingListItemSuggestion(clementines, false, true, formatedQuantity)
				),
				result
			)
		}
	}
}
