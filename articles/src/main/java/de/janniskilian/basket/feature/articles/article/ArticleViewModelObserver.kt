package de.janniskilian.basket.feature.articles.article

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import de.janniskilian.basket.core.ANIMATION_DURATION_S
import de.janniskilian.basket.core.CategoriesAdapter
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.articles.R
import de.janniskilian.basket.feature.articles.article.ArticleFragmentMode.EDIT

class ArticleViewModelObserver(
    private val fragment: ArticleFragment,
    viewModel: ArticleViewModel
) : ViewModelObserver<ArticleViewModel>(viewModel) {

    override fun observe() = with(fragment) {
        with(viewModel) {
            name.observe(viewLifecycleOwner, ::renderName)
            category.observe(viewLifecycleOwner) {
                renderCategory(it)
                renderCategories()
            }
            categories.observe(viewLifecycleOwner) { renderCategories() }
            mode.observe(viewLifecycleOwner, ::renderMode)
            error.observe(viewLifecycleOwner, ::renderError)
            dismiss.observe(viewLifecycleOwner) {
                findNavController().popBackStack()
            }
        }
    }

    private fun renderName(name: String) = with(fragment.binding) {
        if (name != nameEditText.text.toString()) {
            nameEditText.setText(name)
        }
    }

    private fun renderCategory(category: Category?) {
        fragment.binding.categoryEditText.setText(
            category?.name ?: fragment.getString(R.string.category_default)
        )
    }

    private fun renderCategories() {
        (fragment.binding.recyclerView.adapter as? CategoriesAdapter)?.submitList(
            viewModel.categories.value?.map {
                CategoriesAdapter.Item(it, it == viewModel.category.value)
            }
        )
    }

    private fun renderMode(mode: ArticleFragmentMode) {
        if (mode != EDIT) {
            fragment.hideKeyboard()
        }

        toggleRecyclerView(mode != EDIT)
    }

    private fun renderError(isError: Boolean) {
        fragment.binding.nameLayout.error = if (isError) {
            fragment.getString(R.string.article_name_error)
        } else {
            null
        }
    }

    private fun toggleRecyclerView(isVisible: Boolean) = with(fragment.binding) {
        if (isVisible == recyclerView.isVisible) return

        TransitionManager.beginDelayedTransition(
            root,
            AutoTransition().setDuration(ANIMATION_DURATION_S)
        )
        constraintLayout.isVisible = !isVisible
        recyclerView.isVisible = isVisible
    }
}
