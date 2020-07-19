package de.janniskilian.basket.feature.articles.article

import android.os.Bundle
import android.view.View
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.feature.articles.R

class ArticleFragment : BaseFragment() {

    private val args by lazy { ArticleFragmentArgs.fromBundle(requireArguments()) }

    private val module by lazy {
        ArticleModule(appModule, this, args)
    }

    private val setup get() = module.articleSetup

    private val viewModel get() = module.articleViewModel

    override val layoutRes get() = R.layout.fragment_article

    override val titleTextRes
        get() = if (args.articleId.minusOneAsNull() == null) {
            R.string.create_article_title
        } else {
            R.string.edit_article_title
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup.run()
    }

    override fun onNavigateUpAction(): Boolean =
        viewModel.backPressed()
}
