package de.janniskilian.basket.feature.articles.article

import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.CategoriesAdapter
import de.janniskilian.basket.core.type.datapassing.ArticleFragmentArgs
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.extension.extern.onTextChanged
import de.janniskilian.basket.feature.articles.R
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragmentSetup(
    private val fragment: ArticleFragment,
    private val args: ArticleFragmentArgs,
    private val viewModel: ArticleViewModel,
    private val viewModelObserver: ArticleViewModelObserver
) {

    fun run() {
        setupButtons()
        setClickListeners()
        setupNameEditText()
        setupCategoriesRecyclerView()

        viewModelObserver.observe()
    }

    private fun setupButtons() {
        fragment.deleteButton.isVisible = args.articleId != null

        val buttonTextRes = if (args.articleId == null) {
            R.string.create_article_button
        } else {
            R.string.save_article_button
        }
        fragment.submitButton.setText(buttonTextRes)
    }

    private fun setClickListeners() {
        fragment.nameEditText.onDone(viewModel::submitButtonClicked)
        fragment.categoryEditText.setOnClickListener {
            viewModel.editCategoryClicked()
        }

        fragment.deleteButton.setOnClickListener { viewModel.deleteButtonClicked() }
        fragment.submitButton.setOnClickListener { viewModel.submitButtonClicked() }
    }

    private fun setupNameEditText() {
        fragment.nameEditText.onTextChanged {
            viewModel.setName(it)
        }
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
