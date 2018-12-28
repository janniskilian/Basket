package de.janniskilian.basket.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.forEach
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.janniskilian.basket.R
import de.janniskilian.basket.core.bindArgs
import de.janniskilian.basket.core.putArgs
import kotlinx.android.synthetic.main.fragment_bottom_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_bottom_navigation_drawer.view.*
import kotlinx.android.synthetic.main.navigation_item.view.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private val args by bindArgs<BottomNavigationDrawerFragmentArgs>()

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

        setSelectedItem(args.currentNavId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (view?.parent as? View)?.setBackgroundResource(R.drawable.bottom_sheet_dialog_background)
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
            .setPopUpTo(args.currentNavId, true)
            .build()

        (requireActivity() as MainActivity)
            .findNavController(R.id.navHost)
            .navigate(navId, null, options)

        setSelectedItem(navId)
        dismiss()
    }

    companion object {

        fun create(args: BottomNavigationDrawerFragmentArgs): BottomNavigationDrawerFragment =
            BottomNavigationDrawerFragment().putArgs(args)
    }
}
