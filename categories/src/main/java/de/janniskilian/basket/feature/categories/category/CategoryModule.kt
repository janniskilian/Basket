package de.janniskilian.basket.feature.categories.category

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.type.datapassing.CategoryFragmentArgs

class CategoryModule(
    private val appModule: AppModule,
    private val fragment: CategoryFragment,
    private val args: CategoryFragmentArgs
) {

    val categorySetup by lazy {
        CategoryFragmentSetup(
            fragment,
            args,
            categoryEventHandler,
            appModule.dataModule.dataClient
        )
    }

    private val categoryEventHandler by lazy {
        CategoryEventHandler(
            args,
            categoryRouter,
            fragment,
            appModule.dataModule.dataClient
        )
    }

    private val categoryRouter by lazy {
        CategoryRouterImpl(fragment)
    }
}
