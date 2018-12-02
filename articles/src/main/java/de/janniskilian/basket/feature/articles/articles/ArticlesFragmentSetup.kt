package de.janniskilian.basket.feature.articles.articles

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.janniskilian.basket.core.type.datapassing.ArticleFragmentArgs
import de.janniskilian.basket.core.util.extension.extern.observe
import de.janniskilian.basket.feature.articles.article.ArticleFragment
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
		viewModel.articles.value?.getOrNull(position)?.let { article ->
			ArticleFragment
				.create(ArticleFragmentArgs(article.id))
				.let {
					it.show(fragment.fragmentManager, it.tag)
				}
		}
	}
}
