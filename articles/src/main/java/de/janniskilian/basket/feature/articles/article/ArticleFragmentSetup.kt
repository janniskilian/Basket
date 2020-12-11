package de.janniskilian.basket.feature.articles.article

import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.feature.categories.CategoriesAdapter
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.util.android.maybe
import de.janniskilian.basket.core.util.android.view.doOnTextChanged
import de.janniskilian.basket.core.util.android.view.onDone
import de.janniskilian.basket.core.util.android.setupDetailContainerTransformTransition
import de.janniskilian.basket.core.util.android.view.toggleSoftKeyboard
import de.janniskilian.basket.feature.articles.R

class ArticleFragmentSetup(
    private val fragment: ArticleFragment,
    args: ArticleFragmentArgs,
    private val viewModel: ArticleViewModel
) {

    private val articleId = args.articleId
        .maybe()
        ?.let(::ArticleId)

    private val viewModelObserver = ArticleViewModelObserver(fragment, viewModel)

    fun run() {
        articleId?.let(viewModel::setArticleId)

        fragment.setupDetailContainerTransformTransition()
        setupButtons()
        setupNameEditText()
        setupCategoryEditText()
        setupCategoriesRecyclerView()

        viewModelObserver.observe()
    }

    private fun setupButtons() = with(fragment.binding) {
        deleteButton.isVisible = articleId != null
        deleteButton.setOnClickListener { viewModel.deleteButtonClicked() }

        val buttonTextRes = if (articleId == null) {
            R.string.create_article_button
        } else {
            R.string.save_article_button
        }
        submitButton.setText(buttonTextRes)
        submitButton.setOnClickListener { viewModel.submitButtonClicked() }
    }

    private fun setupNameEditText() = with(fragment.binding) {
        nameEditText.doOnTextChanged(viewModel::setName)
        nameEditText.onDone(viewModel::submitButtonClicked)

        if (articleId == null) {
            nameEditText.toggleSoftKeyboard(true)
        }
    }

    private fun setupCategoryEditText() = with(fragment.binding) {
        categoryEditText.setText(R.string.category_default)
        categoryEditText.setOnClickListener { viewModel.editCategoryClicked() }
    }

    private fun setupCategoriesRecyclerView() = with(fragment) {
        val categoriesAdapter = CategoriesAdapter()

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = categoriesAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        categoriesAdapter.clickListener = { position ->
            viewModel
                .categories
                .value
                ?.getOrNull(position)
                ?.let(viewModel::setCategory)
        }
    }
}
