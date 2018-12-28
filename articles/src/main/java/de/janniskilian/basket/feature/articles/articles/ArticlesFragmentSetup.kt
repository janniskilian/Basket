package de.janniskilian.basket.feature.articles.articles

import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_articles.*

class ArticlesFragmentSetup(
    private val fragment: ArticlesFragment,
    private val viewModel: ArticlesViewModel
) {

    private val articlesAdapter get() = fragment.recyclerView.adapter as? ArticlesAdapter

    fun run() {
        setupRecyclerView()

        articlesAdapter?.clickListener = ::articleClicked

        viewModel.articles.observe(fragment) {
            articlesAdapter?.submitList(it)
        }

        fragment.navigationContainer.attachSearchBar(viewModel)
    }

    private fun setupRecyclerView() {
        with(fragment.recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(fragment.requireContext())
            adapter = ArticlesAdapter()
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            addItemDecoration(
                DividerItemDecoration(
                    fragment.requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun articleClicked(position: Int) {
        viewModel.articles.value?.getOrNull(position)?.let {
            fragment.navigate(
                ArticlesFragmentDirections
                    .actionArticlesFragmentToArticleFragment()
                    .setArticleId(it.id)
            )
        }
    }
}
