package de.janniskilian.basket.core

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.dialog_menu_bottom_sheet.*

abstract class MenuBottomSheetDialog : BaseBottomSheetDialogFragment() {

    @get:MenuRes
    abstract val menuRes: Int

    override val layoutRes get() = R.layout.dialog_menu_bottom_sheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(navigationView) {
            inflateMenu(menuRes)

            setNavigationItemSelectedListener {
                onMenuItemSelected(it.itemId)
                findNavController().popBackStack()

                true
            }
        }
    }

    abstract fun onMenuItemSelected(@IdRes itemId: Int)
}
