package de.janniskilian.basket.feature.articles.articles

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.type.domain.Article
import de.janniskilian.basket.core.util.extension.extern.createContainerTransformNavigatorExtras
import de.janniskilian.basket.core.util.extension.extern.hasHardwareKeyboard
import de.janniskilian.basket.core.util.function.getSpeechInputResult
import de.janniskilian.basket.feature.articles.R
import de.janniskilian.basket.feature.articles.databinding.ArticlesFragmentBinding

@AndroidEntryPoint
class ArticlesFragment : BaseFragment<ArticlesFragmentBinding>() {

    private val viewModel: ArticlesViewModel by viewModels()

    private val setup by lazy {
        ArticlesFragmentSetup(this, viewModel)
    }

    override val menuRes get() = R.menu.search

    override val titleTextRes get() = R.string.articles_title

    override val fabTextRes get() = R.string.fab_create_article

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ArticlesFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup.run()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_SPEECH_INPUT
            && resultCode == Activity.RESULT_OK
        ) {
            getSpeechInputResult(data)?.let {
                viewModel.setSearchBarInput(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == R.id.action_search) {
            viewModel.setSearchBarVisible(true)
            true
        } else {
            false
        }

    override fun onNavigateUpAction(): Boolean =
        if (requireActivity().hasHardwareKeyboard
            && viewModel.searchBarVisible.value == true
        ) {
            viewModel.setSearchBarVisible(false)
            true
        } else {
            false
        }

    override fun onFabClicked() {
        navigate(ArticlesFragmentDirections.actionArticlesFragmentToArticleFragment(-1L))
    }

    fun navigateToArticle(position: Int, article: Article) {
        binding
            .recyclerView
            .findViewHolderForAdapterPosition(position)
            ?.itemView
            ?.let {
                navigate(
                    ArticlesFragmentDirections
                        .actionArticlesFragmentToArticleFragment(article.id.value),
                    createContainerTransformNavigatorExtras(it)
                )
            }
    }
}
