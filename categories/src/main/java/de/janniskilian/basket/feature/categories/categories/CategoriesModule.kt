package de.janniskilian.basket.feature.categories.categories

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.util.function.createViewModel

class CategoriesModule(
    private val appModule: AppModule,
    private val fragment: CategoriesFragment
) {

    val categoriesSetup by lazy {
        CategoriesFragmentSetup(fragment, categoriesViewModel)
    }

    val categoriesViewModel by lazy {
        createViewModel(fragment) {
            CategoriesViewModel(appModule.dataModule.dataClient)
        }
    }
}
