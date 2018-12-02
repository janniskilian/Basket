package de.janniskilian.basket.feature.articles.articles

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.util.function.createViewModel

class ArticlesModule(
	private val appModule: AppModule,
	private val fragment: ArticlesFragment
) {

	val articlesSetup by lazy {
		ArticlesFragmentSetup(fragment, articlesViewModel)
	}

	val articlesViewModel by lazy {
		createViewModel(fragment) {
			ArticlesViewModel(appModule.dataModule.dataClient)
		}
	}
}
