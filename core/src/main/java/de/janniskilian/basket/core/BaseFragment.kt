package de.janniskilian.basket.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider
import de.janniskilian.basket.core.util.extension.extern.getThemeColor
import de.janniskilian.basket.core.util.function.createMutableLiveData

abstract class BaseFragment : Fragment() {

    private var pendingNavigation = false

    val navigationContainer
        get() = (requireActivity() as NavigationContainerProvider).navigationContainer

    @get:LayoutRes
    abstract val layoutRes: Int

    @get:MenuRes
    open val menuRes: Int?
        get() = null

    @get:StringRes
    open val fabTextRes: Int?
        get() = null

    open val showAppBar: Boolean get() = true

    open val appBarColor: LiveData<Int> by lazy {
        createMutableLiveData(requireContext().getThemeColor(R.attr.colorPrimary))
    }

    open val animateAppBarColor get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutRes, container, false)

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
