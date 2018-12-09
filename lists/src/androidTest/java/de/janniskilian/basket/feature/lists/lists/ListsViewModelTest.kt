package de.janniskilian.basket.feature.lists.lists

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
import de.janniskilian.basket.core.util.extension.extern.nextValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListsViewModelTest {

	private lateinit var appModule: AppModule

	private lateinit var viewModel: ListsViewModel

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

		viewModel = ListsViewModel(dataClient)
	}

	@After
	fun destroy() {
		dataClient.close()
	}

	@Test
	@UiThreadTest
	fun sortListsByName() = runBlocking {
		val id1 = dataClient.shoppingList.create("C", Color.RED)
		val id2 = dataClient.shoppingList.create("B", Color.RED)
		val id3 = dataClient.shoppingList.create("A", Color.RED)

		viewModel.shoppingLists.nextValue {
			assert(it.size == 3)
			assert(it.component1().id == id3)
			assert(it.component2().id == id2)
			assert(it.component3().id == id1)
		}
	}

	@Test
	@UiThreadTest
	fun deleteAndRestoreList() = runBlocking {
		val id1 = dataClient.shoppingList.create("Test", Color.RED)
		viewModel.shoppingLists.nextValue { lists ->
			assert(lists.indexOfFirst { it.id == id1 } == 1)
		}

		viewModel.deleteList(1)
		viewModel.shoppingLists.nextValue { lists ->
			assert(lists.isEmpty())
		}

		viewModel.restoreShoppingList()
		viewModel.shoppingLists.nextValue { lists ->
			assert(lists.indexOfFirst { it.id == id1 } == 1)
		}
	}
}
