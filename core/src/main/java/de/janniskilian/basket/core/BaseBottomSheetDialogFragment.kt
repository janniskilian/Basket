package de.janniskilian.basket.core

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import de.janniskilian.basket.core.navigationcontainer.NavigationContainerProvider
import de.janniskilian.basket.core.util.extension.extern.getThemeColor
import de.janniskilian.basket.core.util.extension.extern.getThemeResource
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding> : BottomSheetDialogFragment() {

    lateinit var binding: VB
        private set

    val navigationContainer
        get() = (requireActivity() as NavigationContainerProvider).navigationContainer

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        createViewBinding(inflater, container)
            .also {
                binding = it
                setupBackground(it.root)
            }
            .root

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
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
