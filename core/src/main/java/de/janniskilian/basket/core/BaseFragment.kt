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
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider
import de.janniskilian.basket.core.util.extension.extern.addListener
import de.janniskilian.basket.core.util.extension.extern.getThemeColor
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.core.util.function.createMutableLiveData
import de.janniskilian.basket.core.util.function.getLong

@Suppress("TooManyFunctions")
abstract class BaseFragment : Fragment() {

    private val transitionDuration by lazy {
        getLong(requireContext(), R.integer.transition_duration)
    }

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

    open val isShowAppBar: Boolean get() = true

    open val appBarColor: LiveData<Int> by lazy {
        createMutableLiveData(requireContext().getThemeColor(R.attr.colorPrimarySurface))
    }

    open val animateAppBarColor get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTransitions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (view as? ViewGroup)?.isTransitionGroup = true

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

    fun enableFadeThroughExitTransition() {
        exitTransition = MaterialFadeThrough().apply {
            duration = transitionDuration
            setListener()
        }
    }

    private fun setupTransitions() {
        val isRootDestinationStartedFromRootDestination =
            (arguments?.getBoolean(KEY_STARTED_FROM_ROOT_DESTINATION) ?: false
                    && findNavController().currentDestination?.parent?.id == R.id.navGraph)
        arguments?.remove(KEY_STARTED_FROM_ROOT_DESTINATION)
        updateTransitions(isRootDestinationStartedFromRootDestination)
    }

    private fun updateTransitions(enableRootNavigationTransition: Boolean) {
        if (enableRootNavigationTransition) {
            enterTransition = MaterialFadeThrough().apply {
                duration = transitionDuration
                setListener()
            }
        } else {
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                duration = transitionDuration
            }
            exitTransition = enterTransition

            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                duration = transitionDuration
            }
            returnTransition = reenterTransition
        }
    }

    private fun MaterialFadeThrough.setListener() {
        addListener(
            onEnd = { updateTransitions(false) },
            onCancel = { updateTransitions(false) }
        )
    }

    companion object {

        const val KEY_STARTED_FROM_ROOT_DESTINATION = "KEY_STARTED_FROM_ROOT_DESTINATION"
    }
}
