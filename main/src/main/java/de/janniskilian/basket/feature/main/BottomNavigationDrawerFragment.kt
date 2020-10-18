package de.janniskilian.basket.feature.main

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseBottomSheetDialogFragment
import de.janniskilian.basket.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_bottom_navigation_drawer.*

class BottomNavigationDrawerFragment : BaseBottomSheetDialogFragment() {

    private val currentDestinationId by lazy {
        BottomNavigationDrawerFragmentArgs.fromBundle(requireArguments()).currentDestinationId
    }

    override val layoutRes get() = R.layout.fragment_bottom_navigation_drawer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigationView()
    }


    private fun setupNavigationView() {
        navigationView.setNavigationItemSelectedListener {
            itemClicked(it.itemId)
            true
        }

        setSelectedItem(currentDestinationId)
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

    private fun setSelectedItem(@IdRes navId: Int) {
        navigationView.setCheckedItem(navId)
    }
}
