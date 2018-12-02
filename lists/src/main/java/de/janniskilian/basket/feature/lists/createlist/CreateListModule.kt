package de.janniskilian.basket.feature.lists.createlist

import androidx.core.content.ContextCompat
import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.type.datapassing.CreateListFragmentArgs
import de.janniskilian.basket.core.util.function.createViewModel
import de.janniskilian.basket.feature.lists.R

class CreateListModule(
	private val appModule: AppModule,
	private val fragment: CreateListFragment,
	private val args: CreateListFragmentArgs
) {

	val viewModel by lazy {
		createViewModel(fragment) {
			CreateListViewModel(
				args.shoppingListId,
				colors,
				createListFragmentUseCases,
				appModule.dataModule.dataClient
			)
		}
	}

	val createListViewModelObserver by lazy {
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
