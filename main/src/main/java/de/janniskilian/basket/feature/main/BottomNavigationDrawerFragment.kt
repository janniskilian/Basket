package de.janniskilian.basket.feature.main

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.util.extension.extern.getThemeColor
import de.janniskilian.basket.core.util.extension.extern.getThemeResource
import kotlinx.android.synthetic.main.fragment_bottom_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_bottom_navigation_drawer.view.*
import kotlinx.android.synthetic.main.navigation_item.view.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private val currentDestinationId by lazy {
        BottomNavigationDrawerFragmentArgs.fromBundle(requireArguments()).currentDestinationId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater
            .inflate(R.layout.fragment_bottom_navigation_drawer, container, false)
            .also {
                setupBackground(it)
                createNavigationItems(it)
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSelectedItem(currentDestinationId)
    }

    private fun createNavigationItems(view: View) {
        val container = view.navigationItemContainer

        MainPage
            .values()
            .forEach { page ->
                layoutInflater
                    .inflate(R.layout.navigation_item, container, false)
                    .run {
                        id = page.navId
                        title.setText(page.titleRes)
                        icon.setImageResource(page.iconRes)
                        setOnClickListener { itemClicked(id) }
                        container.addView(this)
                    }
            }
    }

    private fun setSelectedItem(@IdRes navId: Int) {
        navigationItemContainer.forEach {
            it.isSelected = it.id == navId
        }
    }

    private fun itemClicked(destinationId: Int) {
        with(requireActivity() as MainActivity) {
            // Do not perform a navigation if we are already at the destination.
            if (findNavController().previousBackStackEntry?.destination?.id != destinationId) {
                currentFragment?.enableFadeThroughExitTransition()

                val options = NavOptions
                    .Builder()
                    .setPopUpTo(currentDestinationId, true)
                    .build()

                findNavController(R.id.navHost).navigate(
                    destinationId,
                    bundleOf(BaseFragment.KEY_STARTED_FROM_ROOT_DESTINATION to true),
                    options
                )

                setSelectedItem(destinationId)
            }
        }

        dismiss()
    }

    private fun setupBackground(view: View) {
        view.background = MaterialShapeDrawable(
            ShapeAppearanceModel
                .builder(
                    requireContext(),
                    requireContext().getThemeResource(R.attr.shapeAppearanceLargeComponent),
                    requireContext().getThemeResource(R.attr.shapeAppearanceOverlay)
                )
                .setBottomLeftCornerSize(0f)
                .setBottomRightCornerSize(0f)
                .build()
        ).apply {
            fillColor = ColorStateList.valueOf(
                requireContext().getThemeColor(R.attr.colorSurface)
            )
        }
    }
}
