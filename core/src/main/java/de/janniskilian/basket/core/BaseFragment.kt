package de.janniskilian.basket.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider
import de.janniskilian.basket.core.util.extension.extern.getThemeColor
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.core.util.function.createMutableLiveData

abstract class BaseFragment : Fragment() {

    private var pendingNavigation = false

    val navigationContainer
        get() = (requireActivity() as NavigationContainerProvider).navigationContainer

    val toolbar: MaterialToolbar?
        get() = requireView().findViewById(R.id.toolbar)

    val titleTextView: TextView?
        get() = toolbar?.findViewById(R.id.title)

    @get:LayoutRes
    abstract val layoutRes: Int

    @get:MenuRes
    open val menuRes: Int?
        get() = null

    @get:StringRes
    open val titleTextRes: Int?
        get() = null

    @get:StringRes
    open val fabTextRes: Int?
        get() = null

    open val showAppBar: Boolean get() = true

    open val appBarColor: LiveData<Int> by lazy {
        createMutableLiveData(requireContext().getThemeColor(R.attr.colorPrimarySurface))
    }

    open val animateAppBarColor get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleTextRes?.let {
            titleTextView?.text = getString(it)
        }
    }

    override fun onResume() {
        super.onResume()
        pendingNavigation = false
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }

    open fun onNavigateUpAction(): Boolean = false

    open fun onHomePressed(): Boolean = false

    open fun onBackPressed(): Boolean = false

    open fun onFabClicked() {
        // This is a click listener for the FAB
        // that should be overridden by fragments where the FAB is displayed.
    }

    fun navigate(directions: NavDirections, navOptions: NavOptions? = null) {
        if (!pendingNavigation) {
            pendingNavigation = true
            findNavController().navigate(directions, navOptions)
        }
    }
}
