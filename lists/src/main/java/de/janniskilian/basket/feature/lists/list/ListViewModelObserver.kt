package de.janniskilian.basket.feature.lists.list

import androidx.core.view.isVisible
import androidx.lifecycle.observe
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.extension.extern.flatMapIndexed
import de.janniskilian.basket.core.util.extension.extern.setScrollable
import de.janniskilian.basket.core.util.function.addToFront
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_list.*

class ListViewModelObserver(
	viewModel: ListViewModel,
	private val fragment: ListFragment
) : ViewModelObserver<ListViewModel>(viewModel) {

	override fun observe() {
		viewModel.shoppingList.observe(fragment) {
			renderTitle(it)
			renderList(it)
		}
	}

	private fun renderTitle(shoppingList: ShoppingList) {
		fragment.toolbar.setScrollable(!shoppingList.isEmpty)
		fragment.headline.text = shoppingList.name
		fragment.navigationContainer.setAppBarColor(shoppingList.color, !fragment.recreated)
	}

	private fun renderList(shoppingList: ShoppingList) {
		fragment.emptyGroup.isVisible = shoppingList.isEmpty
		fragment.recyclerView.isVisible = !shoppingList.isEmpty

		val (checkedItems, uncheckedItems) = shoppingList.items.partition { it.checked }

		val uncheckedItemGroups = uncheckedItems.groupBy { it.article.category }
		val categories = uncheckedItemGroups.keys.sortedBy { it?.name }
		val uncheckedAdapterItems = categories.flatMapIndexed { i, category ->
			val groupItem = ShoppingListAdapter.Item.Group(
				category?.id ?: NO_CATEGORY_ID,
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

		val checkedListItems = checkedItems.sortedBy { it.article.name }
		val checkedAdapterItems = if (checkedListItems.isEmpty()) {
			emptyList()
		} else {
			val groupItem = ShoppingListAdapter.Item.Group(
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

	private fun selectArticleName(item: ShoppingListItem): String =
		item.article.name

	companion object {

		private const val NO_CATEGORY_ID = -1L
		private const val CHECKED_CATEGORY_ID = -2L
	}
}
