package de.janniskilian.basket.feature.articles.articles

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.type.datapassing.ArticleFragmentArgs
import de.janniskilian.basket.core.util.extension.extern.hasHardwareKeyboard
import de.janniskilian.basket.core.util.function.getSpeechInputResult
import de.janniskilian.basket.feature.articles.R
import de.janniskilian.basket.feature.articles.article.ArticleFragment

class ArticlesFragment : BaseFragment() {

    private val module by lazy {
        ArticlesModule(appModule, this)
    }

    private val setup get() = module.articlesSetup

    private val viewModel get() = module.articlesViewModel

    override val menuRes: Int?
        get() = R.menu.search

    override val fabTextRes: Int?
        get() = R.string.fab_create_article

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(
            R.layout.fragment_articles,
            container,
            false
        )

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        if (item?.itemId == R.id.action_search) {
            viewModel.setSearchBarVisible(true)
            true
        } else {
            false
        }

    override fun onBackPressed(): Boolean =
        if (requireActivity().hasHardwareKeyboard
            && viewModel.searchBarVisible.value == true
        ) {
            viewModel.setSearchBarVisible(false)
            true
        } else {
            false
        }

    override fun onFabClicked() {
        showDialogFragment(ArticleFragment.create(ArticleFragmentArgs(null)))
    }
}
