package de.janniskilian.basket.feature.lists.addlistitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.util.extension.extern.switchMap
import de.janniskilian.basket.core.util.function.createMutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddListItemViewModel(
	private val getSuggestionsUseCase: GetSuggestionsUseCase,
	private val listItemSuggestionClickedUseCase: ListItemSuggestionClickedUseCase
) : ViewModel() {

	private val _input: MutableLiveData<String> = createMutableLiveData("")

	val input: LiveData<String>
		get() = _input

	val items: LiveData<List<ShoppingListItemSuggestion>> =
		_input.switchMap { getSuggestionsUseCase.run(it) }

	fun setInput(input: String) {
		_input.value = input
	}

	fun clearInput() {
		setInput("")
	}

	fun suggestionItemClicked(position: Int) {
		items.value?.getOrNull(position)?.let {
			GlobalScope.launch { listItemSuggestionClickedUseCase.run(it) }
		}
	}

	fun inputDoneButtonClicked() {
		suggestionItemClicked(0)
	}
}