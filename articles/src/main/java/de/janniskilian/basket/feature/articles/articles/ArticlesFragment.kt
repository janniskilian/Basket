package de.janniskilian.basket.feature.articles.articles

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.extension.extern.hasHardwareKeyboard
import de.janniskilian.basket.core.util.function.getSpeechInputResult
import de.janniskilian.basket.feature.articles.R

class ArticlesFragment : BaseFragment() {

    private val module by lazy {
        ArticlesModule(appModule, this)
    }

    private val setup get() = module.articlesSetup

    private val viewModel get() = module.articlesViewModel

    override val layoutRes get() = R.layout.fragment_articles

    override val menuRes get() = R.menu.search

    override val fabTextRes get() = R.string.fab_create_article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
}
