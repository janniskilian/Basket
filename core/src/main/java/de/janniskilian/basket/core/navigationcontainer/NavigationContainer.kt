package de.janniskilian.basket.core.navigationcontainer

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

interface NavigationContainer {

    val fab: ExtendedFloatingActionButton

    fun attachSearchBar(viewModel: SearchBarViewModel)
}
