package de.janniskilian.basket.feature.articles.article

import android.os.Bundle
import android.view.View
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.feature.articles.R

class ArticleFragment : BaseFragment() {

    private val args by lazy { ArticleFragmentArgs.fromBundle(requireArguments()) }

    private val module by lazy {
        ArticleModule(appModule, this, args)
    }

    private val setup get() = module.articleSetup

    private val viewModel get() = module.articleViewModel

    override val layoutRes get() = R.layout.fragment_article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup.run()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    override fun onNavigateUpAction(): Boolean =
        viewModel.backPressed()
}
