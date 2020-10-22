package de.janniskilian.basket.feature.lists.list

import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.extension.extern.setScrollable
import de.janniskilian.basket.core.util.function.addToFront
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.lists.R

class ListViewModelObserver(
    private val fragment: ListFragment,
    viewModel: ListViewModel
) : ViewModelObserver<ListViewModel>(viewModel) {

    private var snackbar: Snackbar? = null

    override fun observe() {
        viewModel
            .shoppingList
            .observe(fragment.viewLifecycleOwner) {
                renderTitle(it)
                renderList(it)
            }

        viewModel
            .listItemsRemoved
            .observe(fragment.viewLifecycleOwner, ::listItemsRemovedObserver)

        viewModel
            .allListItemsSetToChecked
            .observe(fragment.viewLifecycleOwner, ::allListItemsSetToCheckedObserver)
    }

    private fun renderTitle(shoppingList: ShoppingList) {
        fragment.toolbar?.setScrollable(!shoppingList.isEmpty)
        fragment.titleTextView?.text = shoppingList.name
    }

    private fun renderList(shoppingList: ShoppingList) = with(fragment.binding) {
        emptyGroup.isVisible = shoppingList.isEmpty
        recyclerView.isVisible = !shoppingList.isEmpty

        val (checkedItems, uncheckedItems) = shoppingList.items.partition { it.isChecked }

        val uncheckedAdapterItems: List<ShoppingListAdapter.Item> =
            if (shoppingList.isGroupedByCategory) {
                val uncheckedItemGroups = uncheckedItems.groupBy { it.article.category }
                val categories = uncheckedItemGroups.keys.sortedBy { it?.name }

                categories.flatMapIndexed { i, category ->
                    val groupItem = ShoppingListAdapter.Item.GroupHeader(
                        category?.id?.value ?: NO_CATEGORY_ID,
                        category?.name ?: fragment.getString(R.string.category_default),
                        i == 0
                    )
                    uncheckedItemGroups[category]
                        .orEmpty()
                        .sortedBy(::selectArticleName)
                        .map {
                            ShoppingListAdapter.Item.ListItem(it)
                        }
                        .addToFront(groupItem)
                }
            } else {
                uncheckedItems
                    .sortedBy(::selectArticleName)
                    .map {
                        ShoppingListAdapter.Item.ListItem(it)
                    }
            }

        val checkedListItems = checkedItems.sortedBy { it.article.name }
        val checkedAdapterItems = if (checkedListItems.isEmpty()) {
            emptyList()
        } else {
            val groupItem = ShoppingListAdapter.Item.GroupHeader(
                CHECKED_CATEGORY_ID,
                fragment.getString(R.string.checked_items_group),
                uncheckedAdapterItems.isEmpty()
            )
            checkedListItems
                .sortedBy(::selectArticleName)
                .map {
                    ShoppingListAdapter.Item.ListItem(it)
                }
                .addToFront(groupItem)
        }

        fragment.shoppingListAdapter?.submitList(uncheckedAdapterItems + checkedAdapterItems)
    }

    private fun listItemsRemovedObserver(removedListItems: List<ShoppingListItem>) =
        with(fragment) {
            val snackbarText = if (removedListItems.size == 1) {
                getString(
                    R.string.list_item_removed_snackbar,
                    removedListItems.first().name
                )
            } else {
                getString(
                    R.string.list_items_removed_snackbar,
                    removedListItems.size
                )
            }

            showSnackbar(snackbarText) {
                viewModel.undoRemoveListItems()
            }
        }

    private fun allListItemsSetToCheckedObserver(
        listItems: List<ShoppingListItem>
    ) = with(fragment) {
        val snackbarText = if (listItems.firstOrNull()?.isChecked == true) {
            getString(R.string.all_list_items_unchecked_snackbar)
        } else {
            getString(R.string.all_list_items_checked_snackbar)
        }

        showSnackbar(snackbarText) {
            viewModel.undoSetAllListItemsIsChecked()
        }
    }

    private fun selectArticleName(item: ShoppingListItem): String =
        item.article.name

    private fun showSnackbar(text: String, actionListener: () -> Unit) {
        snackbar?.dismiss()
        snackbar = Snackbar
            .make(
                fragment.requireView(),
                text,
                Snackbar.LENGTH_LONG
            )
            .setAction(R.string.undo) {
                actionListener()
            }
            .setAnchorView(fragment.navigationContainer.fab)
            .apply {
                show()
            }
    }

    companion object {

        private const val NO_CATEGORY_ID = -1L
        private const val CHECKED_CATEGORY_ID = -2L
    }
}
