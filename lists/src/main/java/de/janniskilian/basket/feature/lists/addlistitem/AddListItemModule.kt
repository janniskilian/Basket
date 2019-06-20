package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.function.createViewModel

class AddListItemModule(
	private val appModule: AppModule,
	private val fragment: AddListItemFragment,
	private val shoppingListId: ShoppingListId
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
        GetSuggestionsUseCase(shoppingListId, appModule.dataModule.dataClient)
	}

	private val createListItemUseCase by lazy {
        ListItemSuggestionClickedUseCase(shoppingListId, appModule.dataModule.dataClient)
	}
}
