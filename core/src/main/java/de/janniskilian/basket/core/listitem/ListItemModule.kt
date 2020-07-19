package de.janniskilian.basket.core.listitem

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.util.function.createViewModel

class ListItemModule(
    appModule: AppModule,
    fragment: ListItemFragment,
    args: ListItemFragmentArgs
) {

    val viewModel by lazy {
        createViewModel(fragment) {
            ListItemViewModel(args, appModule.dataModule.dataClient)
        }
    }

    val setup by lazy {
        ListItemFragmentSetup(fragment, viewModel, viewModelObserver)
    }

    private val viewModelObserver by lazy {
        ListItemViewModelObserver(fragment, viewModel)
    }
}
