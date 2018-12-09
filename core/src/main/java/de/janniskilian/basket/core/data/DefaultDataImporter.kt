package de.janniskilian.basket.core.data

class DefaultDataImporter(
	private val dataClient: DataClient,
	private val defaultDataLoader: DefaultDataLoader
) {

	suspend fun run() {
		if (dataClient.category.getCount() == 0) {
			createCategories()
			createArticles()
		}
	}

	private suspend fun createCategories() {
		dataClient.category.create(defaultDataLoader.loadCategories())
	}

	private suspend fun createArticles() {
		dataClient.article.create(defaultDataLoader.loadArticles())
	}
}
