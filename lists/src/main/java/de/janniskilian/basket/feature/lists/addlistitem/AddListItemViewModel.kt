package de.janniskilian.basket.feature.lists.addlistitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.util.function.createMutableLiveData

class AddListItemViewModel(
	private val getSuggestionsUseCase: GetSuggestionsUseCase,
	private val listItemSuggestionClickedUseCase: ListItemSuggestionClickedUseCase
) : ViewModel() {

	private val _input: MutableLiveData<String> = createMutableLiveData("")

	val input: LiveData<String>
		get() = _input

	val items: LiveData<List<ShoppingListItemSuggestion>> =
		Transformations.switchMap(_input) {
			getSuggestionsUseCase.run(it)
		}

	fun setInput(input: String) {
		_input.value = input
	}

	fun clearInput() {
		setInput("")
	}

	fun suggestionItemClicked(position: Int) {
		items.value?.getOrNull(position)?.let {
			listItemSuggestionClickedUseCase.run(it)
		}
	}

	fun inputDoneButtonClicked() {
		suggestionItemClicked(0)
	}
}
