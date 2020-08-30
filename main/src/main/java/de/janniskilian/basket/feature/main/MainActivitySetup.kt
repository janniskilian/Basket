package de.janniskilian.basket.feature.main

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Build
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.util.extension.extern.getThemeDimen
import de.janniskilian.basket.core.util.function.getLong
import kotlinx.android.synthetic.main.activity_main.*

class MainActivitySetup(
    private val activity: MainActivity,
    private val uiController: MainActivityUiController
) {

    fun run() = with(activity) {
        setSupportActionBar(appBar)
        setupNavigation()
        setupFab()
        setupWindowInsets()
    }

    private fun setupNavigation() = with(activity) {
        findNavController().addOnDestinationChangedListener { _, _, _ ->
            navigationContainer.dismissSnackbar()
        }

        navHostFragment.childFragmentManager.registerFragmentLifecycleCallbacks(
            NavHostFragmentLifecycleCallback(),
            false
        )

        navHostFragment.childFragmentManager.addOnBackStackChangedListener {
            uiController.updateNavigationIcon()
        }

        navigationButton.setOnClickListener {
            if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
                uiController.showNavigation()
            } else {
                if (currentFragment?.onNavigateUpAction() != true
                    && currentFragment?.onHomePressed() != true
                ) {
                    findNavController().navigateUp()
                }
            }
        }

        uiController.updateNavigationIcon()
    }

    private fun setupFab() = with(activity) {
        fab.setOnClickListener { currentFragment?.onFabClicked() }
    }

    private fun setupWindowInsets() = with(activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)

            val handler = WindowInsetsHandler()
            coordinatorLayout.setWindowInsetsAnimationCallback(handler)
            coordinatorLayout.setOnApplyWindowInsetsListener(handler)
        }
    }

    private fun setAppBarColor(color: Int, animate: Boolean) {
        if (animate && activity.window.navigationBarColor != color) {
            val initialColor = activity.appBar.backgroundTint?.defaultColor
                ?: ContextCompat.getColor(activity, R.color.primary)

            if (color == initialColor) return

            val evaluator = ArgbEvaluator()

            with(ValueAnimator.ofFloat(0f, 1f)) {
                duration = getLong(activity, R.integer.transition_duration)
                interpolator = LinearInterpolator()
                addUpdateListener {
                    activity.appBar.backgroundTint = ColorStateList.valueOf(
                        evaluator.evaluate(animatedFraction, initialColor, color) as Int
                    )
                }
                start()
            }
        } else {
            activity.appBar.backgroundTint = ColorStateList.valueOf(color)
        }
    }

    private inner class NavHostFragmentLifecycleCallback :
        FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentStarted(fm: FragmentManager, fragment: Fragment) {
            if (fragment is BaseFragment) {
                activity.invalidateOptionsMenu()

                updateNavHostMargins(fragment)
                updateAppBar(fragment)
                updateFab(fragment)
            }
        }

        private fun updateNavHostMargins(fragment: BaseFragment) {
            activity.navHost.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val bottom = if (fragment.showAppBar) {
                    activity.getThemeDimen(R.attr.actionBarSize)
                } else {
                    0
                }
                updateMargins(bottom = bottom)
            }
        }

        private fun updateAppBar(fragment: BaseFragment) {
            activity.appBar.isVisible = fragment.showAppBar

            fragment.appBarColor.observe(fragment.viewLifecycleOwner) {
                setAppBarColor(it, fragment.animateAppBarColor)
            }
        }

        private fun updateFab(fragment: BaseFragment) {
            val fabTextRes = fragment.fabTextRes
            if (fabTextRes == null) {
                activity.fab.hide()
            } else {
                activity.fab.show()
                uiController.setFabText(fabTextRes)
            }
        }
    }
}
