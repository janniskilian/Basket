package de.janniskilian.basket.feature.lists.list

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.keepScreenOn
import de.janniskilian.basket.core.util.extension.extern.setupOverviewContainerTransformTransition
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.feature.lists.R

class ListFragmentSetup(
    private val fragment: ListFragment,
    private val args: ListFragmentArgs,
    private val viewModel: ListViewModel
) {

    private val viewModelObserver = ListViewModelObserver(fragment, viewModel)

    fun run() {
        viewModel.setShoppingListId(ShoppingListId(args.shoppingListId))

        fragment.setupOverviewContainerTransformTransition(fragment.binding.recyclerView)

        setupWindow()
        setupRecyclerView()
        viewModelObserver.observe()
    }

    private fun setupWindow() = with(fragment) {
        val preventSleep = sharedPrefs.getBoolean(
            getString(R.string.pref_key_prevent_sleep),
            resources.getBoolean(R.bool.pref_def_prevent_sleep)
        )
        requireActivity()
            .window
            .keepScreenOn(preventSleep)
    }

    private fun setupRecyclerView() = with(fragment) {
        val displayCompact = sharedPrefs.getBoolean(
            getString(R.string.pref_key_compact_lists),
            resources.getBoolean(R.bool.pref_def_compact_lists)
        )

        with(binding.recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = ShoppingListAdapter(displayCompact)
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            addItemDecoration(
                EndSpacingDecoration(
                    0,
                    resources.getDimensionPixelSize(R.dimen.fab_spacing),
                    RecyclerView.VERTICAL
                )
            )
        }

        shoppingListAdapter?.apply {
            listItemClickListener = viewModel::listItemClicked
            editButtonClickListener = ::navigateToListItem
        }

        val itemTouchHelperCallback = ListRecyclerViewItemTouchHelperCallback(
            requireContext(),
            ::onListItemSwiped
        )
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)
    }

    private fun onListItemSwiped(itemAdapterPosition: Int) {
        (fragment
            .shoppingListAdapter
            ?.currentList
            ?.getOrNull(itemAdapterPosition) as? ShoppingListAdapter.Item.ListItem)
            ?.let {
                viewModel.removeListItem(it.shoppingListItem)
            }
    }
}
