package de.janniskilian.basket.core.listitem

import de.janniskilian.basket.core.type.domain.ShoppingListItemId
import de.janniskilian.basket.core.util.extension.extern.doOnTextChanged
import de.janniskilian.basket.core.util.extension.extern.onDone

class ListItemFragmentSetup(
    private val fragment: ListItemFragment,
    private val args: ListItemFragmentArgs,
    private val viewModel: ListItemViewModel
) {

    private val viewModelObserver = ListItemViewModelObserver(fragment, viewModel)

    fun run() {
        viewModel.setListItemId(ShoppingListItemId(args.listItemId))

        setupEditTexts()
        setupSubmitButton()

        viewModelObserver.observe()
    }

    private fun setupEditTexts() = with(fragment.binding) {
        articleNameEditText.doOnTextChanged {
            viewModel.setName(it)
        }

        listItemQuantityEditText.doOnTextChanged {
            viewModel.setQuantity(it)
        }

        listItemCommentEditText.doOnTextChanged {
            viewModel.setComment(it)
        }
        listItemCommentEditText.onDone {
            viewModel.submit()
        }
    }

    private fun setupSubmitButton() = with(fragment.binding) {
        submitButton.setOnClickListener {
            viewModel.submit()
        }
    }
}
