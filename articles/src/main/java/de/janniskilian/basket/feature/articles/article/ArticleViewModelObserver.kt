package de.janniskilian.basket.feature.articles.article

import android.animation.ValueAnimator
import android.graphics.Point
import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.observe
import de.janniskilian.basket.core.ANIMATION_DURATION_M
import de.janniskilian.basket.core.CategoriesAdapter
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.doOnEnd
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.articles.R
import de.janniskilian.basket.feature.articles.article.ArticleDialogMode.EDIT
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleViewModelObserver(
    viewModel: ArticleViewModel,
    private val fragment: ArticleFragment
) : ViewModelObserver<ArticleViewModel>(viewModel) {

    private val recyclerViewHeight by lazy {
        val size = Point()
        fragment.requireActivity().windowManager.defaultDisplay.getSize(size)
        size.y / 2
    }

    override fun observe() {
        with(viewModel) {
            name.observe(fragment, ::renderName)
            category.observe(fragment) {
                renderCategory(it)
                renderCategories()
            }
            categories.observe(fragment) { renderCategories() }
            mode.observe(fragment, ::renderMode)
            error.observe(fragment, ::renderError)
            dismiss.observe(fragment) { fragment.dismiss() }
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

        val startHeight: Int
        val targetHeight: Int
        val startView: View
        val targetView: View
        if (visible) {
            startHeight = fragment.constraintLayout.height
            targetHeight = recyclerViewHeight
            startView = fragment.constraintLayout
            targetView = fragment.recyclerView
        } else {
            startHeight = recyclerViewHeight
            targetHeight = fragment.constraintLayout.height
            startView = fragment.recyclerView
            targetView = fragment.constraintLayout
        }

        val animationDuration = ANIMATION_DURATION_M
        ValueAnimator
            .ofInt(startHeight, targetHeight)
            .apply {
                duration = animationDuration
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener {
                    fragment.content.updateLayoutParams {
                        height = it.animatedValue as Int
                    }
                }
                start()
            }

        startView.alpha = 1f
        with(startView.animate()) {
            duration = animationDuration / 2
            alpha(0f)
            doOnEnd {
                startView.isVisible = false
            }
            start()
        }

        targetView.alpha = 0f
        targetView.isVisible = true
        with(targetView.animate()) {
            duration = animationDuration / 2
            startDelay = animationDuration / 2
            alpha(1f)
            start()
        }
    }
}
