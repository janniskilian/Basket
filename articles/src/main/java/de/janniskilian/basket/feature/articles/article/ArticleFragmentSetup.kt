package de.janniskilian.basket.feature.articles.article

import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.CategoriesAdapter
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.util.extension.extern.doOnTextChanged
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.feature.articles.R
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragmentSetup(
    private val fragment: ArticleFragment,
    args: ArticleFragmentArgs,
    private val viewModel: ArticleViewModel
) {

    private val articleId = args.articleId.minusOneAsNull()?.let(::ArticleId)

    private val viewModelObserver = ArticleViewModelObserver(fragment, viewModel)

    fun run() {
        articleId?.let(viewModel::setArticleId)

        setupButtons()
        setupNameEditText()
        setupCategoryEditText()
        setupCategoriesRecyclerView()

        viewModelObserver.observe()
    }

    private fun setupButtons() {
        fragment.deleteButton.isVisible = articleId != null
        fragment.deleteButton.setOnClickListener { viewModel.deleteButtonClicked() }

        val buttonTextRes = if (articleId == null) {
            R.string.create_article_button
        } else {
            R.string.save_article_button
        }
        fragment.submitButton.setText(buttonTextRes)
        fragment.submitButton.setOnClickListener { viewModel.submitButtonClicked() }
    }

    private fun setupNameEditText() {
        fragment.nameEditText.doOnTextChanged(viewModel::setName)
        fragment.nameEditText.onDone(viewModel::submitButtonClicked)
    }

    private fun setupCategoryEditText() {
        fragment.categoryEditText.setOnClickListener { viewModel.editCategoryClicked() }
    }

    private fun setupCategoriesRecyclerView() {
        val categoriesAdapter = CategoriesAdapter()

        with(fragment.recyclerView) {
            layoutManager = LinearLayoutManager(
                fragment.requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = categoriesAdapter
            addItemDecoration(
                DividerItemDecoration(
                    fragment.requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        categoriesAdapter.clickListener = {
            viewModel.setCategory(it)
        }
    }
}
