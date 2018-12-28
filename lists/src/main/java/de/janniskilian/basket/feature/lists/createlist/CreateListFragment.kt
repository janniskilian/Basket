package de.janniskilian.basket.feature.lists.createlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.feature.lists.R

class CreateListFragment : BaseFragment() {

    private val args by lazy { CreateListFragmentArgs.fromBundle(arguments) }

    private val module by lazy {
        CreateListModule(appModule, this, args)
    }

    private val setup get() = module.createListFragmentSetup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(
            R.layout.fragment_create_list,
            container,
            false
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup.run()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }
}
