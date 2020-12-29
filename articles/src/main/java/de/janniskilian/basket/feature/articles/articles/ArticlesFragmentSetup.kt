package de.janniskilian.basket.feature.articles.articles

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.util.android.setupOverviewContainerTransformTransition
import de.janniskilian.basket.core.util.android.view.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.feature.articles.R
import de.janniskilian.basket.feature.articles.databinding.ArticlesFragmentBinding

class ArticlesFragmentSetup(
    private val fragment: ArticlesFragment,
    private val viewModel: ArticlesViewModel
) {

    private val articlesAdapter
        get() = fragment.binding.recyclerView.adapter as? ArticlesAdapter

    fun run() {
        fragment.setupOverviewContainerTransformTransition(fragment.binding.recyclerView)

        fragment.binding.setupRecyclerView()

        articlesAdapter?.clickListener = ::articleClicked

        viewModel.articles.observe(fragment.viewLifecycleOwner, ::renderRecyclerView)

        fragment.navigationContainer.attachSearchBar(viewModel)
    }

    private fun ArticlesFragmentBinding.setupRecyclerView() {
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ArticlesAdapter()
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(
                EndSpacingDecoration(
                    0,
                    resources.getDimensionPixelSize(R.dimen.fab_spacing),
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    private fun renderRecyclerView(it: List<Article>) {
        articlesAdapter?.submitList(it) {
            fragment.binding.recyclerView.invalidateItemDecorations()
        }
    }

    private fun articleClicked(position: Int) {
        viewModel
            .articles
            .value
            ?.getOrNull(position)
            ?.let {
                fragment.navigateToArticle(position, it)
            }
    }
}
