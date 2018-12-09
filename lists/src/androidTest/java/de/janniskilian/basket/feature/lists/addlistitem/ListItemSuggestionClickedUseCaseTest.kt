package de.janniskilian.basket.feature.lists.addlistitem

import android.app.Application
import android.graphics.Color
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.janniskilian.basket.core.data.DefaultDataImporter
import de.janniskilian.basket.core.data.DefaultDataLoader
import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.testing.createTestAppModule
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.type.domain.ShoppingList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class ListItemSuggestionClickedUseCaseTest {

	private lateinit var appModule: AppModule

	private lateinit var useCase: ListItemSuggestionClickedUseCase

	private var shoppingListId = 0L

	private val dataClient get() = appModule.dataModule.dataClient

	@get:Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	@Before
	fun setup() {
		val context = ApplicationProvider.getApplicationContext<Application>()
		appModule = createTestAppModule(context)

		runBlocking {
			DefaultDataImporter(dataClient, DefaultDataLoader(context)).run()
			shoppingListId = dataClient.shoppingList.create("Test", Color.RED)
		}

		useCase = ListItemSuggestionClickedUseCase(shoppingListId, appModule.dataModule.dataClient)
	}

	@After
	fun destroy() {
		dataClient.close()
	}

	@Test
	@UiThreadTest
	fun addListItemWithExistingArticle() = runBlocking {
		val article = createListItemWithExistingArticle(1)
		val shoppingList = getShoppingList()

		assertEquals(1, shoppingList.items.size)
		assertEquals(article, shoppingList.items.first().article)
		assertFalse(shoppingList.items.first().checked)
		assertEquals(shoppingListId, shoppingList.items.first().shoppingListId)
	}

	@Test
	@UiThreadTest
	fun addListItemWithNewArticle() = runBlocking {
		val article = createListItemWithNewArticle("New test article")
		val shoppingList = getShoppingList()

		assertEquals(1, shoppingList.items.size)
		assertEquals(article.name, shoppingList.items.first().article.name)
		assertFalse(shoppingList.items.first().checked)
		assertEquals(shoppingListId, shoppingList.items.first().shoppingListId)
	}

	@Test
	@UiThreadTest
	fun removeListItem() = runBlocking {
		val article1 = createListItemWithExistingArticle(1)
		val article2 = createListItemWithExistingArticle(2)
		val article3 = createListItemWithNewArticle("New test article")

		assertEquals(3, getShoppingList().items.size)

		useCase.run(ShoppingListItemSuggestion(article1, true, true))
		useCase.run(ShoppingListItemSuggestion(article2, true, true))
		useCase.run(ShoppingListItemSuggestion(article3, true, true))

		assert(getShoppingList().items.isEmpty())
	}

	private suspend fun getShoppingList(): ShoppingList =
		dataClient.shoppingList.get(shoppingListId)!!

	private suspend fun createListItemWithExistingArticle(articleId: Long): Article {
		val article = dataClient.article.get(articleId)
		assertNotNull(article)
		useCase.run(ShoppingListItemSuggestion(article, false, true))
		return article
	}

	private suspend fun createListItemWithNewArticle(articleName: String): Article {
		val article = Article(0, articleName, null)
		useCase.run(ShoppingListItemSuggestion(article, false, false))
		return article
	}
}
