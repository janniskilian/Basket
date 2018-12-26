package de.janniskilian.basket.feature.categories.category

import de.janniskilian.basket.core.data.DataClient

class CategoryFragmentUseCases(private val dataClient: DataClient) {

    suspend fun createCategory(name: String) {
        dataClient.category.create(name)
    }

    suspend fun editCategory(id: Long, name: String) {
        dataClient.category.update(id, name)
    }

    suspend fun deleteCategory(id: Long) {
        dataClient.category.delete(id)
    }
}
