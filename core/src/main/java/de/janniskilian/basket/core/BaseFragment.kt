package de.janniskilian.basket.core

import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider

open class BaseFragment : Fragment() {

    private var pendingNavigation = false

    val navigationContainer
        get() = (requireActivity() as NavigationContainerProvider).navigationContainer

    @get:MenuRes
    open val menuRes: Int?
        get() = null

    @get:StringRes
    open val fabTextRes: Int?
        get() = null

    override fun onResume() {
        super.onResume()
        pendingNavigation = false
    }

    open fun onBackPressed(): Boolean = false

    open fun onFabClicked() {}

    fun navigate(directions: NavDirections, navOptions: NavOptions? = null) {
        if (!pendingNavigation) {
            pendingNavigation = true
            findNavController().navigate(directions, navOptions)
        }
    }
}

val Fragment.app: BasketApp
    get() = requireActivity().application as BasketApp

val Fragment.appModule: AppModule
    get() = app.appModule
