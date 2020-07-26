package de.janniskilian.basket.feature.main

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.forEach
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import de.janniskilian.basket.R
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
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_bottom_navigation_drawer,
            container,
            false
        )

        createNavigationItems(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (view.parent as? View)?.background = MaterialShapeDrawable(
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

        setSelectedItem(currentDestinationId)
    }

    private fun createNavigationItems(view: View) {
        val container = view.navigationItemContainer

        MainPage.values().forEach { page ->
            layoutInflater.inflate(R.layout.navigation_item, container, false).run {
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

    private fun itemClicked(navId: Int) {
        val options = NavOptions.Builder()
            .setPopUpTo(currentDestinationId, true)
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            .build()

        (requireActivity() as MainActivity)
            .findNavController(R.id.navHost)
            .navigate(navId, null, options)

        setSelectedItem(navId)
        dismiss()
    }
}
