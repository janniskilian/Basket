package de.janniskilian.basket.feature.articles.article

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.CategoriesAdapter
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.extension.extern.onTextChanged
import de.janniskilian.basket.feature.articles.R
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragmentSetup(
    private val fragment: ArticleFragment,
    args: ArticleFragmentArgs,
    private val viewModel: ArticleViewModel,
    private val viewModelObserver: ArticleViewModelObserver
) {

    private val articleId = args.articleId.minusOneAsNull()

    fun run() {
        setupHeadline()
        setupButtons()
        setClickListeners()
        setupNameEditText()
        setupCategoriesRecyclerView()

        viewModelObserver.observe()
    }

    private fun setupHeadline() {
        val headlineTextRes = if (articleId == null) {
            R.string.create_article_headline
        } else {
            R.string.edit_article_headline
        }

        fragment
            .requireView()
            .findViewById<TextView>(R.id.headline)
            .setText(headlineTextRes)
    }

    private fun setupButtons() {
        fragment.deleteButton.isVisible = articleId != null

        val buttonTextRes = if (articleId == null) {
            R.string.create_article_button
        } else {
            R.string.save_article_button
        }
        fragment.submitButton.setText(buttonTextRes)
    }

    private fun setClickListeners() {
        fragment.nameEditText.onDone(viewModel::submitButtonClicked)
        fragment.categoryEditText.setOnClickListener { viewModel.editCategoryClicked() }
        fragment.deleteButton.setOnClickListener { viewModel.deleteButtonClicked() }
        fragment.submitButton.setOnClickListener { viewModel.submitButtonClicked() }
    }

    private fun setupNameEditText() {
        fragment.nameEditText.onTextChanged(viewModel::setName)
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
