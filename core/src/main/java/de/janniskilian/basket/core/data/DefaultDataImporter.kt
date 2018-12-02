package de.janniskilian.basket.core.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DefaultDataImporter(
	private val dataClient: DataClient,
	private val defaultDataLoader: DefaultDataLoader
) {

	fun run() {
		GlobalScope.launch(Dispatchers.IO) {
			if (dataClient.category.getCount().await() == 0) {
				createCategories()
				createArticles()
			}
		}
	}

	private suspend fun createCategories() {
		dataClient.category.create(defaultDataLoader.loadCategories()).await()
	}

	private fun createArticles() {
		dataClient.article.create(defaultDataLoader.loadArticles())
	}
}
