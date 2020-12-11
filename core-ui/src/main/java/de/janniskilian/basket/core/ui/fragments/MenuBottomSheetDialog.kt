package de.janniskilian.basket.core.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.navigation.fragment.findNavController
import de.janniskilian.basket.core.ui.databinding.MenuBottomSheetDialogBinding

abstract class MenuBottomSheetDialog :
    BaseBottomSheetDialogFragment<MenuBottomSheetDialogBinding>() {

    @get:MenuRes
    abstract val menuRes: Int

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        MenuBottomSheetDialogBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.navigationView) {
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
