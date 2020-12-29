package de.janniskilian.basket.feature.categories.categories

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.feature.categories.CategoriesAdapter
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.android.setupOverviewContainerTransformTransition
import de.janniskilian.basket.core.util.android.view.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.feature.categories.R
import de.janniskilian.basket.feature.categories.databinding.CategoriesFragmentBinding

class CategoriesFragmentSetup(
    private val fragment: CategoriesFragment,
    private val viewModel: CategoriesViewModel
) {

    private val categoriesAdapter
        get() = fragment.binding.recyclerView.adapter as? CategoriesAdapter

    fun run() {
        fragment.setupOverviewContainerTransformTransition(fragment.binding.recyclerView)

        fragment.binding.setupRecyclerView()

        categoriesAdapter?.clickListener = ::categoryClicked

        viewModel.categories.observe(fragment.viewLifecycleOwner, ::renderRecyclerView)

        fragment.navigationContainer.attachSearchBar(viewModel)
    }

    private fun CategoriesFragmentBinding.setupRecyclerView() {
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = CategoriesAdapter()
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(
                EndSpacingDecoration(
                    0,
                    resources.getDimensionPixelSize(R.dimen.fab_spacing),
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    private fun renderRecyclerView(categories: List<Category>) {
        categoriesAdapter?.submitList(
            categories.map { CategoriesAdapter.Item(it) }
        ) {
            fragment.binding.recyclerView.invalidateItemDecorations()
        }
    }

    private fun categoryClicked(position: Int) {
        viewModel
            .categories
            .value
            ?.getOrNull(position)
            ?.let {
                fragment.navigateToCategory(position, it)
            }
    }
}
