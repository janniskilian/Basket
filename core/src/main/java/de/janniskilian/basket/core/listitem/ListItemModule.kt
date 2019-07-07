package de.janniskilian.basket.core.listitem

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.util.function.createViewModel

class ListItemModule(
    appModule: AppModule,
    fragment: ListItemFragment,
    args: ListItemFragmentArgs
) {

    val listItemViewModel by lazy {
        createViewModel(fragment) {
            ListItemViewModel(args, appModule.dataModule.dataClient)
        }
    }

}
