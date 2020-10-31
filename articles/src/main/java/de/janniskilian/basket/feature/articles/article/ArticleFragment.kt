package de.janniskilian.basket.feature.articles.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.feature.articles.R
import de.janniskilian.basket.feature.articles.databinding.ArticleFragmentBinding

@AndroidEntryPoint
class ArticleFragment : BaseFragment<ArticleFragmentBinding>() {

    private val args by lazy { ArticleFragmentArgs.fromBundle(requireArguments()) }

    private val viewModel: ArticleViewModel by viewModels()

    private val setup by lazy {
        ArticleFragmentSetup(this, args, viewModel)
    }

    override val useDefaultTransitions get() = false

    override val titleTextRes
        get() = if (args.articleId.minusOneAsNull() == null) {
            R.string.create_article_title
        } else {
            R.string.edit_article_title
        }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ArticleFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup.run()
    }

    override fun onNavigateUpAction(): Boolean =
        viewModel.backPressed()
}
