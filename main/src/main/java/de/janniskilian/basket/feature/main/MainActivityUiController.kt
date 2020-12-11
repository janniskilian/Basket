package de.janniskilian.basket.feature.main

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.updateLayoutParams
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import de.janniskilian.basket.R
import de.janniskilian.basket.core.util.android.getLong
import de.janniskilian.basket.core.util.android.view.setSelectedImageState

class MainActivityUiController(private val activity: MainActivity) {

    fun setFabText(@StringRes buttonTextRes: Int): Unit = with(activity.binding) {
        if (fab.text.isNullOrEmpty() || !fab.isLaidOut) {
            fab.setText(buttonTextRes)
            fab.updateLayoutParams {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        } else {
            val fabWidth = fab.width

            fab.setText(buttonTextRes)

            fab.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )

            with(ValueAnimator.ofInt(fabWidth, fab.measuredWidth)) {
                duration = getLong(activity, R.integer.animation_duration_m)
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener {
                    fab.updateLayoutParams {
                        width = animatedValue as Int
                    }
                }
                start()
            }
        }
    }

    fun updateNavigationIcon() = with(activity) {
        binding.navigationButton.setSelectedImageState(
            navHostFragment.childFragmentManager.backStackEntryCount > 0
        )
    }

    fun showNavigation() {
        val navController = activity.findNavController()
        navController.currentDestination?.id?.let { destinationId ->
            navController.navigate(
                R.id.bottomNavigationDrawerFragment,
                BottomNavigationDrawerFragmentArgs(destinationId).toBundle()
            )
        }
    }
}
