package de.janniskilian.basket.feature.lists.addlistitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.util.function.createMutableLiveData
import de.janniskilian.basket.core.util.sortedByName
import kotlinx.coroutines.launch

class AddListItemViewModel(
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val listItemSuggestionClickedUseCase: ListItemSuggestionClickedUseCase
) : ViewModel() {

    private val _input: MutableLiveData<String> = createMutableLiveData("")

    val input: LiveData<String>
        get() = _input

    val items: LiveData<List<ShoppingListItemSuggestion>> =
        _input
            .switchMap { getSuggestionsUseCase.run(it) }
            .map { it.sortedByName() }

    fun setInput(input: String) {
        _input.value = input
    }

    fun clearInput() {
        setInput("")
    }

    fun suggestionItemClicked(position: Int) {
        items.value?.getOrNull(position)?.let {
            viewModelScope.launch { listItemSuggestionClickedUseCase.run(it) }
        }
    }

    fun inputDoneButtonClicked() {
        suggestionItemClicked(0)
    }
}
