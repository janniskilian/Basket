package de.janniskilian.basket.feature.articles.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.janniskilian.basket.core.BaseBottomSheetDialogFragment
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.getArgs
import de.janniskilian.basket.core.putArgs
import de.janniskilian.basket.core.type.datapassing.ArticleFragmentArgs
import de.janniskilian.basket.feature.articles.R

class ArticleFragment : BaseBottomSheetDialogFragment() {

    private val module by lazy {
        ArticleModule(appModule, this, getArgs())
    }

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

    override fun onBackPressed(): Boolean =
        viewModel.backPressed()

    companion object {

        fun create(args: ArticleFragmentArgs): ArticleFragment =
            ArticleFragment().putArgs(args)
    }
}
