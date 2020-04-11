package de.janniskilian.basket.feature.main

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.updateLayoutParams
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import de.janniskilian.basket.R
import de.janniskilian.basket.core.ANIMATION_DURATION_M
import de.janniskilian.basket.core.util.extension.extern.setSelectedImageState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityUiController(private val activity: MainActivity) {

    fun setFabText(@StringRes buttonTextRes: Int) = with(activity) {
        if (fab.text.isNullOrEmpty()) {
            fab.setText(buttonTextRes)
            fab.updateLayoutParams {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        } else {
            fab.updateLayoutParams {
                width = fab.width
            }
            fab.setText(buttonTextRes)

            fab.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            with(ValueAnimator.ofInt(fab.width, fab.measuredWidth)) {
                duration = ANIMATION_DURATION_M
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
        navigationButton.setSelectedImageState(
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
