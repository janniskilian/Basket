package de.janniskilian.basket.feature.categories.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.navigationcontainer.DefaultSearchBarViewModel
import de.janniskilian.basket.core.navigationcontainer.SearchBarViewModel
import de.janniskilian.basket.core.type.domain.Category

class CategoriesViewModel(
	dataClient: DataClient
) : ViewModel(), SearchBarViewModel by DefaultSearchBarViewModel() {

	val categories: LiveData<List<Category>> =
		Transformations.switchMap(searchBarInput) {
			dataClient.category.get(it)
		}
}
