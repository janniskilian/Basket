package de.janniskilian.basket.feature.categories.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.ui.navigation.DefaultSearchBarViewModel
import de.janniskilian.basket.core.ui.navigation.SearchBarViewModel
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.sortedByName

class CategoriesViewModel @ViewModelInject constructor(
    dataClient: DataClient
) : ViewModel(), SearchBarViewModel by DefaultSearchBarViewModel() {

    val categories: LiveData<List<Category>> =
        searchBarInput
            .switchMap {
                dataClient
                    .category
                    .get(it)
                    .asLiveData()
            }
            .map { it.sortedByName() }
}
