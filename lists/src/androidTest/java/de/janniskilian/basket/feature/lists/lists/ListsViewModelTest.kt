package de.janniskilian.basket.feature.lists.lists

import android.graphics.Color
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.util.extension.extern.nextValue
import de.janniskilian.basket.test.createTestDataClient
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListsViewModelTest {


    private lateinit var viewModel: ListsViewModel

    private lateinit var dataClient: DataClient

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        dataClient = createTestDataClient()
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
