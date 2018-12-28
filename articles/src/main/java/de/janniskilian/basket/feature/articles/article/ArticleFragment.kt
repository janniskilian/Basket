package de.janniskilian.basket.feature.articles.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.feature.articles.R

class ArticleFragment : BaseFragment() {

    private val module by lazy {
        ArticleModule(appModule, this, args)
    }

    private val args by lazy { ArticleFragmentArgs.fromBundle(arguments) }

    private val setup get() = module.articleSetup

    private val viewModel get() = module.articleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(
            R.layout.fragment_article,
            container,
            false
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup.run()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    override fun onBackPressed(): Boolean =
        viewModel.backPressed()
}
