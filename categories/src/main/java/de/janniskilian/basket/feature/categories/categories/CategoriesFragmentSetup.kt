package de.janniskilian.basket.feature.categories.categories

import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.janniskilian.basket.core.CategoriesAdapter
import de.janniskilian.basket.core.type.domain.Category
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragmentSetup(
    private val fragment: CategoriesFragment,
    private val viewModel: CategoriesViewModel
) {

    private val categoriesAdapter get() = fragment.recyclerView.adapter as? CategoriesAdapter

    fun run() {
        setupRecyclerView()

        categoriesAdapter?.clickListener = ::categoryClicked

        viewModel.categories.observe(fragment) { categories ->
            categoriesAdapter?.submitList(
                categories.map { CategoriesAdapter.Item(it) }
            )
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
        }
    }

    private fun categoryClicked(category: Category?) {
        if (category != null) {
            fragment.navigate(CategoriesFragmentDirections.actionCategoriesFragmentToCategoryFragment(category.id.value))
        }
    }
}
