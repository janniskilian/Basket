package de.janniskilian.basket.feature.articles.article

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import de.janniskilian.basket.core.data.DefaultDataImporter
import de.janniskilian.basket.core.data.DefaultDataLoader
import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.testing.createTestAppModule
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@RunWith(AndroidJUnit4::class)
class ArticleFragmentUseCasesTest {

	private lateinit var appModule: AppModule

	private lateinit var useCases: ArticleFragmentUseCases

	private val dataClient get() = appModule.dataModule.dataClient

	@get:Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	@Before
	fun setup() {
		val context = ApplicationProvider.getApplicationContext<Application>()
		appModule = createTestAppModule(context)

		runBlocking {
			DefaultDataImporter(dataClient, DefaultDataLoader(context)).run()
		}

		useCases = ArticleFragmentUseCases(appModule.dataModule.dataClient)
	}

	@After
	fun destroy() {
		dataClient.close()
	}

	@Test
	@UiThreadTest
	fun createArticle() = runBlocking {
		val name1 = "Test1"
		val name2 = "Test2"
		val name3 = "Test3"
		val category = dataClient.category.getSuspend(1)

		joinAll(
			useCases.createArticle(name1, null),
			useCases.createArticle(name2, category)
		)

		val article1 = dataClient.article.getSuspend(name1)
		assert(article1.size == 1)
		assertEquals(name1, article1.first().name)
		assertEquals(null, article1.first().category)

		val article2 = dataClient.article.getSuspend(name2)
		assert(article2.size == 1)
		assertEquals(name2, article2.first().name)
		assertEquals(category, article2.first().category)

		val article3 = dataClient.article.getSuspend(name3)
		assert(article3.isEmpty())
	}

	@Test
	@UiThreadTest
	fun editArticle() = runBlocking {
		val article = dataClient.article.get(1)
		val editedName = "Test"

		assertNotNull(article)
		useCases.editArticle(article.id, editedName, null).join()

		val editedArticle = dataClient.article.get(1)
		assertNotNull(editedArticle)
		assertEquals(editedName, editedArticle.name)
		assertNull(editedArticle.category)
	}

	@Test
	@UiThreadTest
	fun deleteArticle() = runBlocking {
		val article = dataClient.article.get(1)
		assertNotNull(article)
		useCases.deleteArticle(article.id).join()
		assertNull(dataClient.article.get(1))
	}
}
