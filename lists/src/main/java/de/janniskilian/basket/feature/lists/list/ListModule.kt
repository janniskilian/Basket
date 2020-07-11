package de.janniskilian.basket.feature.lists.list

import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.util.function.createViewModel

class ListModule(
    private val appModule: AppModule,
    private val fragment: ListFragment,
    private val args: ListFragmentArgs
) {

    val listSetup by lazy {
        ListFragmentSetup(
            fragment,
            listViewModel,
            listViewModelObserver,
            appModule.androidModule.sharedPrefs
        )
    }

    val listViewModel by lazy {
        createViewModel(fragment) {
            ListViewModel(args, appModule.dataModule.dataClient)
        }
    }

    private val listViewModelObserver by lazy {
        ListViewModelObserver(listViewModel, fragment)
    }
}
