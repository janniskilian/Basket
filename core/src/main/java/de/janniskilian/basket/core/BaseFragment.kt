package de.janniskilian.basket.core

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
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

    protected fun showDialogFragment(fragment: DialogFragment) {
        fragment.show(requireActivity().supportFragmentManager, fragment.tag)
    }
}

val Fragment.app: BasketApp
    get() = requireActivity().application as BasketApp

val Fragment.appModule: AppModule
    get() = app.appModule

fun <A : Parcelable> Fragment.bindArgs(): Lazy<A> =
    lazy {
        arguments?.getParcelable<A>(ARGS_KEY)!!
    }

fun <A : Parcelable> Fragment.getArgs(): A =
    arguments?.getParcelable(ARGS_KEY)!!

fun <A : Parcelable> createArgs(args: A): Bundle =
    bundleOf(ARGS_KEY to args)

fun <T : Fragment> T.putArgs(args: Parcelable?): T {
    if (args != null) {
        arguments = bundleOf(ARGS_KEY to args)
    }

    return this
}

private const val ARGS_KEY = "ARGS_KEY"
