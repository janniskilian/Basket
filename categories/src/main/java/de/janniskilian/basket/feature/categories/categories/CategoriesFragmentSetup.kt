package de.janniskilian.basket.feature.categories.categories

import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.CategoriesAdapter
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.feature.categories.R
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragmentSetup(
    private val fragment: CategoriesFragment,
    private val viewModel: CategoriesViewModel
) {

    private val categoriesAdapter get() = fragment.recyclerView.adapter as? CategoriesAdapter

    fun run() {
        setupRecyclerView()

        categoriesAdapter?.clickListener = ::categoryClicked

        viewModel.categories.observe(fragment.viewLifecycleOwner) { categories ->
            categoriesAdapter?.submitList(
                categories.map { CategoriesAdapter.Item(it) }
            ) {
                fragment.recyclerView.invalidateItemDecorations()
            }
        }

        fragment.navigationContainer.attachSearchBar(viewModel)
    }

    private fun setupRecyclerView() {
        with(fragment.recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(fragment.requireContext())
            adapter = CategoriesAdapter()
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            addItemDecoration(
                DividerItemDecoration(
                    fragment.requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            addItemDecoration(
                EndSpacingDecoration(
                    0,
                    resources.getDimensionPixelSize(R.dimen.fab_spacing),
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    private fun categoryClicked(category: Category?) {
        if (category != null) {
            fragment.navigate(
                CategoriesFragmentDirections
                    .actionCategoriesFragmentToCategoryFragment(category.id.value)
            )
        }
    }
}
