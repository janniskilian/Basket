package de.janniskilian.basket.feature.lists.lists

import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.util.android.view.setScrollable
import de.janniskilian.basket.core.util.android.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.lists.R

class ListsViewModelObserver(
    private val fragment: ListsFragment,
    viewModel: ListsViewModel
) : ViewModelObserver<ListsViewModel>(viewModel) {

    private var snackbar: Snackbar? = null

    override fun observe() {
        viewModel.shoppingLists.observe(fragment.viewLifecycleOwner, ::shoppingListsObserver)
        viewModel.shoppingListDeleted.observe(fragment.viewLifecycleOwner) {
            shoppingListDeletedObserver()
        }
    }

    private fun shoppingListsObserver(shoppingLists: List<ShoppingList>) = with(fragment) {
        listsAdapter?.submitList(shoppingLists) {
            binding.recyclerView.invalidateItemDecorations()
        }

        binding.emptyLayout.isVisible = shoppingLists.isEmpty()
        toolbar?.setScrollable(shoppingLists.isNotEmpty())
    }

    private fun shoppingListDeletedObserver() = with(fragment) {
        snackbar?.dismiss()
        snackbar = Snackbar
            .make(
                requireView(),
                R.string.list_deleted_snackbar,
                Snackbar.LENGTH_LONG
            )
            .setAction(R.string.undo) { viewModel.restoreShoppingList() }
            .setAnchorView(navigationContainer.fab)
            .apply {
                show()
            }
    }
}
