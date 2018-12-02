package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.util.function.createViewModel

class AddListItemModule(
	private val appModule: AppModule,
	private val fragment: AddListItemFragment,
	private val args: AddListItemFragmentArgs
) {

	val addListItemViewModel by lazy {
		createViewModel(fragment) {
			AddListItemViewModel(
				getSuggestionsUseCase,
				createListItemUseCase
			)
		}
	}

	val addListItemSetup by lazy {
		AddListItemFragmentSetup(fragment, addListItemViewModel)
	}

	private val getSuggestionsUseCase by lazy {
		GetSuggestionsUseCase(args.shoppingListId, appModule.dataModule.dataClient)
	}

	private val createListItemUseCase by lazy {
		ListItemSuggestionClickedUseCase(args.shoppingListId, appModule.dataModule.dataClient)
	}
}
