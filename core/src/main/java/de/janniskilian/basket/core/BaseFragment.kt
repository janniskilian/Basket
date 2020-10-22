package de.janniskilian.basket.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider
import de.janniskilian.basket.core.util.extension.extern.addListener
import de.janniskilian.basket.core.util.extension.extern.doOnEvent
import de.janniskilian.basket.core.util.extension.extern.getThemeColor
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.core.util.function.getLong
import de.janniskilian.basket.core.util.viewmodel.DefaultMutableLiveData

@Suppress("TooManyFunctions")
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private val transitionDuration by lazy {
        getLong(requireContext(), R.integer.transition_duration)
    }

    @IdRes
    private var navDestinationId = 0

    lateinit var binding: VB
        private set

    val navigationContainer
        get() = (requireActivity() as NavigationContainerProvider).navigationContainer

    val toolbar: MaterialToolbar?
        get() = requireView().findViewById(R.id.toolbar)

    val titleTextView: TextView?
        get() = toolbar?.findViewById(R.id.title)

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
        DefaultMutableLiveData(requireContext().getThemeColor(R.attr.colorPrimarySurface))
    }

    open val animateAppBarColor get() = true

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTransitions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        createViewBinding(inflater, container)
            .also {
                binding = it
            }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveNavDestinationId(savedInstanceState)
        setupNavigationResultHandling()

        (view as? ViewGroup)?.isTransitionGroup = true

        titleTextRes?.let {
            titleTextView?.text = getString(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_NAV_DESTINATION_ID, navDestinationId)
    }

    open fun onNavigationResult(requestCode: Int, resultCode: ResultCode, data: Any?) {
        // Override this method to receive navigation results.
    }

    open fun onNavigateUpAction(): Boolean = false

    open fun onHomePressed(): Boolean = false

    open fun onBackPressed(): Boolean = false

    open fun onFabClicked() {
        // This is a click listener for the FAB
        // that should be overridden by fragments where the FAB is displayed.
    }

    fun navigate(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        findNavController().navigate(
            resId,
            args,
            navOptions,
            navigatorExtras
        )
    }

    fun navigate(directions: NavDirections, navOptions: NavOptions? = null) {
        findNavController().navigate(directions, navOptions)
    }

    fun navigateWithResult(
        directions: NavDirections,
        requestCode: Int,
        navOptions: NavOptions? = null
    ) {
        val args = directions.arguments
        addRequestCodeToBundle(args, requestCode)
        navigate(directions.actionId, args, navOptions)
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
                    && findNavController().currentDestination?.parent?.id == R.id.nav_graph)
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

    private fun saveNavDestinationId(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            findNavController().currentDestination?.let {
                navDestinationId = it.id
            }
        } else {
            navDestinationId = savedInstanceState.getInt(KEY_NAV_DESTINATION_ID)
        }
    }

    private fun setupNavigationResultHandling() {
        if (navDestinationId != 0) {
            val backStackEntry = try {
                findNavController().getBackStackEntry(navDestinationId)
            } catch (e: IllegalArgumentException) {
                return
            }

            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    backStackEntry.getNavigationResult { requestCode, resultCode, data ->
                        clearResult()
                        onNavigationResult(requestCode, resultCode, data)
                    }
                }
            }

            backStackEntry.lifecycle.addObserver(observer)

            viewLifecycleOwner.lifecycle.doOnEvent(Lifecycle.Event.ON_DESTROY) {
                backStackEntry.lifecycle.removeObserver(observer)
            }
        }
    }

    private fun addRequestCodeToBundle(bundle: Bundle, requestCode: Int) {
        bundle.putInt(KEY_REQUEST_CODE, requestCode)
    }

    companion object {

        const val KEY_STARTED_FROM_ROOT_DESTINATION = "KEY_STARTED_FROM_ROOT_DESTINATION"

        private const val KEY_NAV_DESTINATION_ID = "KEY_NAV_DESTINATION_ID"
    }
}
