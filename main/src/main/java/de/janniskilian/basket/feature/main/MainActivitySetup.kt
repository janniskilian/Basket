package de.janniskilian.basket.feature.main

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
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
    }

    private fun setupNavigation() = with(activity) {
        findNavController().addOnDestinationChangedListener { _, _, _ ->
            navigationContainer.dismissSnackbar()
        }

        navHostFragment.childFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {

                override fun onFragmentStarted(fm: FragmentManager, fragment: Fragment) {
                    if (fragment is BaseFragment) {
                        invalidateOptionsMenu()

                        fragment.appBarColor.observe(fragment.viewLifecycleOwner) {
                            setAppBarColor(it, fragment.animateAppBarColor)
                        }

                        val fabTextRes = fragment.fabTextRes
                        if (fabTextRes == null) {
                            fab.hide()
                        } else {
                            fab.show()
                            uiController.setFabText(fabTextRes)
                        }

                        appBar.isVisible = fragment.showAppBar
                        navHost.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                            val marginBottom = if (fragment.showAppBar) {
                                activity.getThemeDimen(R.attr.actionBarSize)
                            } else {
                                0
                            }

                            updateMargins(bottom = marginBottom)
                        }
                    }
                }
            },
            false
        )

        navHostFragment.childFragmentManager.addOnBackStackChangedListener {
            uiController.updateNavigationIcon()
        }

        navigationButton.setOnClickListener {
            if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
                uiController.showNavigation()
            } else {
                findNavController().navigateUp()
            }
        }

        uiController.updateNavigationIcon()
    }

    private fun setupFab() = with(activity) {
        fab.setOnClickListener { currentFragment?.onFabClicked() }
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
                    val currentColor =
                        evaluator.evaluate(animatedFraction, initialColor, color) as Int

                    activity.appBar.backgroundTint = ColorStateList.valueOf(currentColor)
                    activity.window.navigationBarColor = currentColor
                }
                start()
            }
        } else {
            activity.appBar.backgroundTint = ColorStateList.valueOf(color)
            activity.window.navigationBarColor = color
        }
    }
}
