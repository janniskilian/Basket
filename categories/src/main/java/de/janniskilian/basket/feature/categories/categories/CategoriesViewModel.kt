package de.janniskilian.basket.feature.categories.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.navigationcontainer.DefaultSearchBarViewModel
import de.janniskilian.basket.core.navigationcontainer.SearchBarViewModel
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.sortedByName

class CategoriesViewModel(
    dataClient: DataClient
) : ViewModel(), SearchBarViewModel by DefaultSearchBarViewModel() {

    val categories: LiveData<List<Category>> =
        searchBarInput
            .switchMap { dataClient.category.get(it) }
            .map { it.sortedByName() }
}
