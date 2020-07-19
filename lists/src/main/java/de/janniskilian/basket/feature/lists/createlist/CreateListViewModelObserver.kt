package de.janniskilian.basket.feature.lists.createlist

import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_create_list.*

class CreateListViewModelObserver(
    private val fragment: CreateListFragment,
    viewModel: CreateListViewModel
) : ViewModelObserver<CreateListViewModel>(viewModel) {

    override fun observe() {
        with(viewModel) {
            name.observe(fragment.viewLifecycleOwner, ::renderName)
            selectedColor.observe(fragment.viewLifecycleOwner) { renderColors() }
            error.observe(fragment.viewLifecycleOwner, ::renderError)
            startList.observe(fragment.viewLifecycleOwner, ::startListFragment)
            dismiss.observe(fragment.viewLifecycleOwner) {
                fragment.findNavController().navigateUp()
            }
        }
    }

    private fun renderName(name: String) {
        if (name != fragment.nameEditText.text.toString()) {
            fragment.nameEditText.setText(name)
        }
    }

    private fun renderColors() {
        val selectedColor = viewModel.selectedColor.value

        (fragment.recyclerView.adapter as? ColorsAdapter)?.submitList(
            viewModel.colors.map {
                ColorsAdapter.Item(it, it == selectedColor)
            }
        )
    }

    private fun renderError(error: Boolean) {
        fragment.nameLayout.error = if (error) {
            fragment.getString(R.string.shopping_list_name_error)
        } else {
            null
        }
    }

    private fun startListFragment(shoppingListId: ShoppingListId) {
        fragment.navigate(
            CreateListFragmentDirections
                .actionCreateListFragmentToListFragment(shoppingListId.value)
        )
    }
}
