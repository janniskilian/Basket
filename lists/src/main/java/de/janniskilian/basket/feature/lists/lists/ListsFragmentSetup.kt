package de.janniskilian.basket.feature.lists.lists

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.extension.extern.setupOverviewContainerTransformTransition
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.core.util.recyclerview.ItemSpacingDecoration
import de.janniskilian.basket.feature.lists.R

class ListsFragmentSetup(
    private val fragment: ListsFragment,
    viewModel: ListsViewModel
) {

    private val viewModelObserver = ListsViewModelObserver(fragment, viewModel)

    fun run() {
        fragment.setupOverviewContainerTransformTransition(fragment.binding.recyclerView)

        setupRecyclerView()
        setClickListeners()
        viewModelObserver.observe()
    }

    private fun setupRecyclerView() {
        with(fragment.binding.recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ListsAdapter()
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            val spacing = resources.getDimensionPixelSize(R.dimen.shopping_list_item_spacing)
            addItemDecoration(
                ItemSpacingDecoration(
                    spacing,
                    RecyclerView.VERTICAL
                )
            )
            addItemDecoration(
                EndSpacingDecoration(
                    spacing,
                    resources.getDimensionPixelSize(R.dimen.fab_spacing),
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    private fun setClickListeners() = with(fragment) {
        listsAdapter?.itemClickListener = ::navigateToList
        listsAdapter?.moreButtonClickListener = ::showListMenu
    }
}
