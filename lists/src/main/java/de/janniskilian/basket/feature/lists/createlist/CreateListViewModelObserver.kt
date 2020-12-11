package de.janniskilian.basket.feature.lists.createlist

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.android.view.recyclerview.centerItem
import de.janniskilian.basket.core.util.android.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.lists.R

class CreateListViewModelObserver(
    private val fragment: CreateListFragment,
    viewModel: CreateListViewModel
) : ViewModelObserver<CreateListViewModel>(viewModel) {

    override fun observe() {
        with(viewModel) {
            name.observe(fragment.viewLifecycleOwner, ::renderName)
            selectedColor.observe(fragment.viewLifecycleOwner) { renderColors() }
            error.observe(fragment.viewLifecycleOwner, ::renderError)
            startList.observe(fragment.viewLifecycleOwner, ::navigateToListFragment)
            dismiss.observe(fragment.viewLifecycleOwner) {
                fragment
                    .findNavController()
                    .navigateUp()
            }
        }
    }

    private fun renderName(name: String) = with(fragment.binding) {
        if (name != nameEditText.text.toString()) {
            nameEditText.setText(name)
        }
    }

    private fun renderColors() = with(fragment.binding) {
        val selectedColor = viewModel.selectedColor.value

        (colorsRecyclerView.adapter as? ColorsAdapter)?.submitList(
            viewModel.colors.map {
                ColorsAdapter.Item(it, it == selectedColor)
            }
        ) {
            colorsRecyclerView.centerItem(
                viewModel.colors.indexOf(selectedColor),
                RecyclerView.HORIZONTAL
            )
        }
    }

    private fun renderError(isError: Boolean) = with(fragment.binding) {
        nameLayout.error = if (isError) {
            fragment.getString(R.string.shopping_list_name_error)
        } else {
            null
        }
    }

    private fun navigateToListFragment(shoppingListId: ShoppingListId) {
        fragment.navigate(
            CreateListFragmentDirections
                .actionCreateListFragmentToListFragment(shoppingListId.value)
        )
    }
}
