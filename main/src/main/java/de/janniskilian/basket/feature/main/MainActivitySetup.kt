package de.janniskilian.basket.feature.main

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.KEY_DEFAULT_DATA_IMPORTED
import de.janniskilian.basket.core.util.extension.extern.getThemeDimen
import de.janniskilian.basket.core.util.function.getLong
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivitySetup(
    private val activity: MainActivity,
    private val uiController: MainActivityUiController,
    private val dataStore: DataStore<Preferences>
) {

    fun run(savedInstanceState: Bundle?) = with(activity) {
        setSupportActionBar(binding.appBar)
        setupNavigation()
        setupFab()
        setupWindowInsets()
        setupOnboarding(savedInstanceState)
    }

    private fun setupNavigation() = with(activity) {
        navHostFragment.childFragmentManager.registerFragmentLifecycleCallbacks(
            NavHostFragmentLifecycleCallback(),
            false
        )

        navHostFragment.childFragmentManager.addOnBackStackChangedListener {
            uiController.updateNavigationIcon()
        }

        binding.navigationButton.setOnClickListener {
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
        binding.fab.setOnClickListener { currentFragment?.onFabClicked() }
    }

    private fun setupWindowInsets() = with(activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)

            val handler = WindowInsetsHandler()
            binding.coordinatorLayout.setWindowInsetsAnimationCallback(handler)
            binding.coordinatorLayout.setOnApplyWindowInsetsListener(handler)
        }
    }

    private fun setupOnboarding(savedInstanceState: Bundle?) = activity.lifecycleScope.launch {
        dataStore
            .data
            .map { it[KEY_DEFAULT_DATA_IMPORTED] }
            .collect {
                if (savedInstanceState == null
                    && it != true
                ) {
                    activity
                        .findNavController()
                        .navigate(R.id.onboardingFragment)
                }
            }
    }

    private fun setAppBarColor(color: Int, isAnimate: Boolean) {
        if (isAnimate && activity.window.navigationBarColor != color) {
            val initialColor = activity.binding.appBar.backgroundTint?.defaultColor
                ?: ContextCompat.getColor(activity, R.color.brand_green)

            if (color == initialColor) return

            val evaluator = ArgbEvaluator()

            with(ValueAnimator.ofFloat(0f, 1f)) {
                duration = getLong(activity, R.integer.transition_duration)
                interpolator = LinearInterpolator()
                addUpdateListener {
                    activity.binding.appBar.backgroundTint = ColorStateList.valueOf(
                        evaluator.evaluate(animatedFraction, initialColor, color) as Int
                    )
                }
                start()
            }
        } else {
            activity.binding.appBar.backgroundTint = ColorStateList.valueOf(color)
        }
    }

    private inner class NavHostFragmentLifecycleCallback :
        FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
            if (fragment is BaseFragment<*>) {
                activity.invalidateOptionsMenu()

                updateNavHostMargins(fragment)
                updateAppBar(fragment)
                updateFab(fragment)
            }
        }

        private fun updateNavHostMargins(fragment: BaseFragment<*>) {
            activity.binding.navHost.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val bottom = if (fragment.isShowAppBar) {
                    activity.getThemeDimen(R.attr.actionBarSize)
                } else {
                    0
                }
                updateMargins(bottom = bottom)
            }
        }

        private fun updateAppBar(fragment: BaseFragment<*>) {
            activity.binding.appBar.isVisible = fragment.isShowAppBar

            fragment.appBarColor.observe(fragment.viewLifecycleOwner) {
                setAppBarColor(it, fragment.animateAppBarColor)
            }
        }

        private fun updateFab(fragment: BaseFragment<*>) {
            val fabTextRes = fragment.fabTextRes
            if (fabTextRes == null) {
                activity.binding.fab.hide()
            } else {
                activity.binding.fab.show()
                uiController.setFabText(fabTextRes)
            }
        }
    }
}
