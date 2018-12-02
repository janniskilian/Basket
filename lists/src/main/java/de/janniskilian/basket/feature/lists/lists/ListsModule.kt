package de.janniskilian.basket.feature.lists.lists

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.util.function.createViewModel

class ListsModule(
	private val appModule: AppModule,
	private val fragment: ListsFragment
) {

	val listsViewModel by lazy {
		createViewModel(fragment) {
			ListsViewModel(appModule.dataModule.dataClient)
		}
	}
}
