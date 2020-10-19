package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.util.extension.extern.setSelectedImageState
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_add_list_item.*

class AddListItemViewModelObserver(
    private val fragment: AddListItemFragment,
    viewModel: AddListItemViewModel
) : ViewModelObserver<AddListItemViewModel>(viewModel) {

    override fun observe() {
        viewModel.input.observe(fragment.viewLifecycleOwner, ::renderInput)
        viewModel.items.observe(fragment.viewLifecycleOwner, ::renderSuggestions)
    }

    private fun renderInput(input: String) {
        if (fragment.inputEditText.text.toString() != input) {
            fragment.inputEditText.setText(input)
        }

        val showSpeechInput = input.isEmpty()
        fragment.searchBarSpeechInputButton.setSelectedImageState(!showSpeechInput)
        fragment.searchBarSpeechInputButton.contentDescription = fragment.getString(
            if (showSpeechInput) {
                R.string.speech_input_button_desc
            } else {
                R.string.clear_input_button_desc
            }
        )
    }

    private fun renderSuggestions(suggestions: List<ShoppingListItemSuggestion>) {
        fragment.suggestionsAdapter?.submitList(
            suggestions.map {
                ShoppingListItemSuggestionsAdapter.Item(it)
            }
        )
    }
}
