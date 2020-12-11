package de.janniskilian.basket.feature.lists.addlistitem

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.sortedByName
import de.janniskilian.basket.core.util.android.viewmodel.DefaultMutableLiveData
import kotlinx.coroutines.launch

class AddListItemViewModel @ViewModelInject constructor(
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val listItemSuggestionClickedUseCase: ListItemSuggestionClickedUseCase
) : ViewModel() {

    private val shoppingListId = MutableLiveData<ShoppingListId>()

    private val _input: MutableLiveData<String> = DefaultMutableLiveData("")

    val input: LiveData<String>
        get() = _input

    val items: LiveData<List<ShoppingListItemSuggestion>> =
        _input
            .switchMap { inputValue ->
                shoppingListId.switchMap {
                    getSuggestionsUseCase
                        .run(it, inputValue)
                        .asLiveData()
                }
            }
            .map { it.sortedByName() }

    fun setShoppingListId(id: ShoppingListId) {
        shoppingListId.value = id
    }

    fun setInput(input: String) {
        _input.value = input
    }

    fun clearInput() {
        setInput("")
    }

    fun suggestionItemClicked(position: Int) {
        val id = shoppingListId.value
        val item = items.value?.getOrNull(position)
        if (id != null && item != null) {
            viewModelScope.launch {
                listItemSuggestionClickedUseCase.run(id, item)
            }
        }
    }

    fun inputDoneButtonClicked() {
        suggestionItemClicked(0)
    }
}
