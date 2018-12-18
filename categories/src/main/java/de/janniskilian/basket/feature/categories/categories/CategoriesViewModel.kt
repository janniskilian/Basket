package de.janniskilian.basket.feature.categories.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.navigationcontainer.DefaultSearchBarViewModel
import de.janniskilian.basket.core.navigationcontainer.SearchBarViewModel
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.switchMap

class CategoriesViewModel(
    dataClient: DataClient
) : ViewModel(), SearchBarViewModel by DefaultSearchBarViewModel() {

    val categories: LiveData<List<Category>> =
        searchBarInput.switchMap { dataClient.category.get(it) }
}
