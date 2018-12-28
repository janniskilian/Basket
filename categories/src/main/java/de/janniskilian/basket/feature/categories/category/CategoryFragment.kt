package de.janniskilian.basket.feature.categories.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.util.extension.extern.hideKeyboard
import de.janniskilian.basket.feature.categories.R

class CategoryFragment : BaseFragment() {

    private val args by lazy { CategoryFragmentArgs.fromBundle(arguments) }

    private val module by lazy {
        CategoryModule(appModule, this, args)
    }

    private val setup get() = module.categorySetup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(
            R.layout.fragment_category,
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
