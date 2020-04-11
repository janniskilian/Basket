package de.janniskilian.basket.feature.lists.createlist

import android.os.Bundle
import android.view.View
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.feature.lists.R

class CreateListFragment : BaseFragment() {

    private val args by lazy { CreateListFragmentArgs.fromBundle(requireArguments()) }

    private val module by lazy {
        CreateListModule(appModule, this, args)
    }

    private val setup get() = module.createListFragmentSetup

    override val layoutRes get() = R.layout.fragment_create_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup.run()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }
}
