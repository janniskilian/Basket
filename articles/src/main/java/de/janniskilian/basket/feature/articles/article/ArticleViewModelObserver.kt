package de.janniskilian.basket.feature.articles.article

import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import de.janniskilian.basket.core.ANIMATION_DURATION_S
import de.janniskilian.basket.core.CategoriesAdapter
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.articles.R
import de.janniskilian.basket.feature.articles.article.ArticleDialogMode.EDIT
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleViewModelObserver(
    viewModel: ArticleViewModel,
    private val fragment: ArticleFragment
) : ViewModelObserver<ArticleViewModel>(viewModel) {

    override fun observe() {
        with(viewModel) {
            name.observe(fragment.viewLifecycleOwner, ::renderName)
            category.observe(fragment.viewLifecycleOwner) {
                renderCategory(it)
                renderCategories()
            }
            categories.observe(fragment.viewLifecycleOwner) { renderCategories() }
            mode.observe(fragment.viewLifecycleOwner, ::renderMode)
            error.observe(fragment.viewLifecycleOwner, ::renderError)
            dismiss.observe(fragment.viewLifecycleOwner) {
                fragment.findNavController().navigateUp()
            }
        }
    }

    private fun renderName(name: String) {
        if (name != fragment.nameEditText.text.toString()) {
            fragment.nameEditText.setText(name)
        }
    }

    private fun renderCategory(category: Category?) {
        fragment.categoryEditText.setText(
            category?.name ?: fragment.getString(R.string.category_default)
        )
    }

    private fun renderCategories() {
        (fragment.recyclerView.adapter as? CategoriesAdapter)?.submitList(
            viewModel.categories.value?.map {
                CategoriesAdapter.Item(it, it == viewModel.category.value)
            }
        )
    }

    private fun renderMode(mode: ArticleDialogMode) {
        if (mode != EDIT) {
            fragment.hideKeyboard()
        }

        toggleRecyclerView(mode != EDIT)
    }

    private fun renderError(error: Boolean) {
        fragment.nameLayout.error = if (error) {
            fragment.getString(R.string.article_name_error)
        } else {
            null
        }
    }

    private fun toggleRecyclerView(visible: Boolean) {
        if (visible == fragment.recyclerView.isVisible) return

        TransitionManager.beginDelayedTransition(
            fragment.content,
            AutoTransition().setDuration(ANIMATION_DURATION_S)
        )
        fragment.constraintLayout.isVisible = !visible
        fragment.recyclerView.isVisible = visible
    }
}
