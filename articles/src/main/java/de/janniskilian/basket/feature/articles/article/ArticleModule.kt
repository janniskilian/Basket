package de.janniskilian.basket.feature.articles.article

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.type.domain.ArticleId
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.core.util.function.createViewModel

class ArticleModule(
    private val appModule: AppModule,
    private val fragment: ArticleFragment,
    private val args: ArticleFragmentArgs
) {

    val articleSetup by lazy {
        ArticleFragmentSetup(
            fragment,
            args,
            articleViewModel,
            articleViewModelObserver
        )
    }

    val articleViewModel by lazy {
        createViewModel(fragment) {
            ArticleViewModel(
                args.articleId.minusOneAsNull()?.let(::ArticleId),
                ArticleFragmentUseCases(appModule.dataModule.dataClient),
                appModule.dataModule.dataClient
            )
        }
    }

    private val articleViewModelObserver by lazy {
        ArticleViewModelObserver(fragment, articleViewModel)
    }
}
