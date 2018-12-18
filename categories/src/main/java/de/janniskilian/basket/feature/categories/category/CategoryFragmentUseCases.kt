package de.janniskilian.basket.feature.categories.category

import de.janniskilian.basket.core.data.DataClient
import kotlinx.coroutines.Job

class CategoryFragmentUseCases(private val dataClient: DataClient) {

    fun createCategory(name: String): Job =
        dataClient.category.create(name)

    fun editCategory(id: Long, name: String): Job =
        dataClient.category.update(id, name)

    fun deleteCategory(id: Long): Job =
        dataClient.category.delete(id)
}
