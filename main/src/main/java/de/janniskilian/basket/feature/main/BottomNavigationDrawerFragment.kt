package de.janniskilian.basket.feature.main

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import de.janniskilian.basket.R
import de.janniskilian.basket.core.ui.fragments.BaseFragment
import de.janniskilian.basket.core.ui.fragments.MenuBottomSheetDialog

class BottomNavigationDrawerFragment : MenuBottomSheetDialog() {

    private val currentDestinationId by lazy {
        BottomNavigationDrawerFragmentArgs.fromBundle(requireArguments()).currentDestinationId
    }

    override val menuRes get() = R.menu.navigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSelectedItem(currentDestinationId)
    }

    override fun onMenuItemSelected(itemId: Int) {
        with(requireActivity() as MainActivity) {
            // Do not perform a navigation if we are already at the destination.
            if (findNavController().previousBackStackEntry?.destination?.id != itemId) {
                currentFragment?.enableFadeThroughExitTransition()

                val options = NavOptions
                    .Builder()
                    .setPopUpTo(currentDestinationId, true)
                    .build()

                findNavController(R.id.navHost).navigate(
                    itemId,
                    bundleOf(BaseFragment.KEY_STARTED_FROM_ROOT_DESTINATION to true),
                    options
                )

                setSelectedItem(itemId)
            }
        }
    }

    private fun setSelectedItem(@IdRes navId: Int) {
        binding.navigationView.setCheckedItem(navId)
    }
}
