package de.janniskilian.basket.feature.articles.articles

import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.feature.articles.R
import kotlinx.android.synthetic.main.fragment_articles.*

class ArticlesFragmentSetup(
    private val fragment: ArticlesFragment,
    private val viewModel: ArticlesViewModel
) {

    private val articlesAdapter get() = fragment.recyclerView.adapter as? ArticlesAdapter

    fun run() {
        setupRecyclerView()

        articlesAdapter?.clickListener = ::articleClicked

        viewModel.articles.observe(fragment.viewLifecycleOwner) {
            articlesAdapter?.submitList(it) {
                fragment.recyclerView.invalidateItemDecorations()
            }
        }

        fragment.navigationContainer.attachSearchBar(viewModel)
    }

    private fun setupRecyclerView() = with(fragment.recyclerView) {
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
        addItemDecoration(
            EndSpacingDecoration(
                0,
                resources.getDimensionPixelSize(R.dimen.fab_spacing),
                RecyclerView.VERTICAL
            )
        )
    }

    private fun articleClicked(position: Int) {
        viewModel.articles.value?.getOrNull(position)?.let {
            fragment.navigate(ArticlesFragmentDirections.actionArticlesFragmentToArticleFragment(it.id.value))
        }
    }
}
