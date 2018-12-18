package de.janniskilian.basket.feature.categories.category

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.type.datapassing.CategoryFragmentArgs
import de.janniskilian.basket.core.util.function.createViewModel

class CategoryModule(
    private val appModule: AppModule,
    private val fragment: CategoryFragment,
    private val args: CategoryFragmentArgs
) {

    val categorySetup by lazy {
        CategoryFragmentSetup(
            fragment,
            args,
            categoryViewModel,
            categoryViewModelObserver
        )
    }

    private val categoryViewModel by lazy {
        createViewModel(fragment) {
            CategoryViewModel(
                args.categoryId,
                CategoryFragmentUseCases(appModule.dataModule.dataClient),
                appModule.dataModule.dataClient
            )
        }
    }

    private val categoryViewModelObserver by lazy {
        CategoryViewModelObserver(categoryViewModel, fragment)
    }
}
