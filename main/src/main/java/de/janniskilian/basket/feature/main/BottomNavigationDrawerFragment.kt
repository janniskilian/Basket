package de.janniskilian.basket.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import de.janniskilian.basket.R
import de.janniskilian.basket.core.BaseBottomSheetDialogFragment
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.databinding.BottomNavigationDrawerDialogBinding

class BottomNavigationDrawerFragment :
    BaseBottomSheetDialogFragment<BottomNavigationDrawerDialogBinding>() {

    private val currentDestinationId by lazy {
        BottomNavigationDrawerFragmentArgs.fromBundle(requireArguments()).currentDestinationId
    }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        BottomNavigationDrawerDialogBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigationView()
    }


    private fun setupNavigationView() {
        binding.navigationView.setNavigationItemSelectedListener {
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
        binding.navigationView.setCheckedItem(navId)
    }
}
