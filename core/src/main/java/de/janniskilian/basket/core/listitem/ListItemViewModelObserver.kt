package de.janniskilian.basket.core.listitem

import androidx.navigation.fragment.findNavController
import de.janniskilian.basket.core.R
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver

class ListItemViewModelObserver(
    private val fragment: ListItemFragment,
    viewModel: ListItemViewModel
) : ViewModelObserver<ListItemViewModel>(viewModel) {

    override fun observe(): Unit = with(fragment) {
        viewModel.name.observe(viewLifecycleOwner, ::renderName)
        viewModel.quantity.observe(viewLifecycleOwner, ::renderQuantity)
        viewModel.comment.observe(viewLifecycleOwner, ::renderComment)
        viewModel.nameError.observe(viewLifecycleOwner, ::renderError)
        viewModel.dismiss.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun renderName(name: String) = with(fragment.binding) {
        if (name != articleNameEditText.text.toString()) {
            articleNameEditText.setText(name)
        }
    }

    private fun renderQuantity(quantity: String) = with(fragment.binding) {
        if (quantity != listItemQuantityEditText.text.toString()) {
            listItemQuantityEditText.setText(quantity)
        }
    }

    private fun renderComment(comment: String) = with(fragment.binding) {
        if (comment != listItemCommentEditText.text.toString()) {
            listItemCommentEditText.setText(comment)
        }
    }

    private fun renderError(isError: Boolean) = with(fragment.binding) {
        articleNameEditText.error = if (isError) {
            fragment.getString(R.string.article_name_error)
        } else {
            null
        }
    }
}
