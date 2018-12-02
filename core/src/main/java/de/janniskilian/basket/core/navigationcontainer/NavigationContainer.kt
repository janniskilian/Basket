package de.janniskilian.basket.core.navigationcontainer

interface NavigationContainer {

	fun setAppBarColor(color: Int, animate: Boolean)

	fun attachSearchBar(viewModel: SearchBarViewModel)
}
