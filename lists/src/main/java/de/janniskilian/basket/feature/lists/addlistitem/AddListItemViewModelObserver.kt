package de.janniskilian.basket.feature.lists.addlistitem

import de.janniskilian.basket.core.util.android.view.setSelectedImageState
import de.janniskilian.basket.core.util.android.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.lists.R

class AddListItemViewModelObserver(
    private val fragment: AddListItemFragment,
    viewModel: AddListItemViewModel
) : ViewModelObserver<AddListItemViewModel>(viewModel) {

    override fun observe() {
        viewModel.input.observe(fragment.viewLifecycleOwner, ::renderInput)
        viewModel.items.observe(fragment.viewLifecycleOwner, ::renderSuggestions)
    }

    private fun renderInput(input: String) = with(fragment.binding) {
        if (inputEditText.text.toString() != input) {
            inputEditText.setText(input)
        }

        val showSpeechInput = input.isEmpty()
        searchBarSpeechInputButton.setSelectedImageState(!showSpeechInput)
        searchBarSpeechInputButton.contentDescription = fragment.getString(
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
