package de.janniskilian.basket.core.ui.navigation

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

interface NavigationContainer {

    val fab: ExtendedFloatingActionButton

    fun attachSearchBar(viewModel: SearchBarViewModel)
}
