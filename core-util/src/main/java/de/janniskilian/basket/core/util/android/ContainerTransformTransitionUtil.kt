package de.janniskilian.basket.core.util.android

import android.graphics.Color
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import de.janniskilian.basket.core.util.R

fun View.setContainerTransformTransitionName(id: Long) {
    transitionName = context.getString(R.string.item_transition_name, id)
}

fun Fragment.createContainerTransformNavigatorExtras(itemView: View): Navigator.Extras {
    val transitionDuration = getLong(
        requireContext(),
        R.integer.transition_duration
    )

    exitTransition = MaterialElevationScale(false).apply {
        duration = transitionDuration
    }
    reenterTransition = MaterialElevationScale(true).apply {
        duration = transitionDuration
    }

    val detailTransitionName = getString(R.string.detail_transition_name)

    return FragmentNavigatorExtras(itemView to detailTransitionName)
}

fun Fragment.setupOverviewContainerTransformTransition(postponedView: View) {
    postponeEnterTransition()
    postponedView.doOnPreDraw {
        startPostponedEnterTransition()
    }
}

fun Fragment.setupDetailContainerTransformTransition() {
    view?.transitionName = getString(R.string.detail_transition_name)

    sharedElementEnterTransition = createContainerTransformTransition(true)
    sharedElementReturnTransition = createContainerTransformTransition(false)
}

private fun Fragment.createContainerTransformTransition(isEnterTransition: Boolean) =
    MaterialContainerTransform().apply {
        drawingViewId = R.id.navHost
        duration = getLong(requireContext(), R.integer.transition_duration)
        scrimColor = Color.TRANSPARENT
        setAllContainerColors(requireContext().getThemeColor(R.attr.colorSurface))

        val elevation = resources.getDimension(R.dimen.card_elevation)
        if (isEnterTransition) {
            endElevation = elevation
        } else {
            startElevation = elevation
        }
    }
