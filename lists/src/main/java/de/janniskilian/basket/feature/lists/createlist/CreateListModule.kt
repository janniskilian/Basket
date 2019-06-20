package de.janniskilian.basket.feature.lists.createlist

import androidx.core.content.ContextCompat
import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.core.util.function.createViewModel
import de.janniskilian.basket.feature.lists.R

class CreateListModule(
    private val appModule: AppModule,
    private val fragment: CreateListFragment,
    private val args: CreateListFragmentArgs
) {

    val createListFragmentSetup by lazy {
        CreateListFragmentSetup(
            fragment,
            args,
            viewModel,
            createListViewModelObserver
        )
    }

    private val viewModel by lazy {
        createViewModel(fragment) {
            CreateListViewModel(
                args.shoppingListId.minusOneAsNull()?.let(::ShoppingListId),
                colors,
                createListFragmentUseCases,
                appModule.dataModule.dataClient
            )
        }
    }

    private val createListViewModelObserver by lazy {
        CreateListViewModelObserver(viewModel, fragment)
    }

    private val createListFragmentUseCases by lazy {
        CreateListFragmentUseCases(appModule.dataModule.dataClient)
    }

    private val colors by lazy {
        listOf(
            R.color.orange,
            R.color.red,
            R.color.pink,
            R.color.purple,
            R.color.indigo,
            R.color.cyan,
            R.color.green,
            R.color.blue_grey,
            R.color.brown,
            R.color.grey
        ).map {
            ContextCompat.getColor(appModule.androidModule.applicationContext, it)
        }
    }
}
