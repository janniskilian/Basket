package de.janniskilian.basket.feature.categories.category

import androidx.lifecycle.LifecycleOwner
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.datapassing.CategoryFragmentArgs
import de.janniskilian.basket.core.util.extension.extern.observe

class CategoryEventHandler(
	private val args: CategoryFragmentArgs,
	private val router: CategoryRouter,
	private val lifecycleOwner: LifecycleOwner,
	private val dataClient: DataClient
) {

	fun createButtonClicked(name: String) {
		if (name.isNotEmpty()) {
			if (args.categoryId == null) {
				dataClient.category.create(name)
				router.dismiss()
			} else {
				args.categoryId?.let { categoryId ->
					dataClient
						.category
						.get(categoryId)
						.observe(lifecycleOwner) {
							dataClient.category.update(it.copy(name = name))
							router.dismiss()
						}
				}
			}
		} else {
		}
	}
}
