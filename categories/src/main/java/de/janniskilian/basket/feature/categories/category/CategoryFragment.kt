package de.janniskilian.basket.feature.categories.category

import android.os.Bundle
import android.view.View
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.feature.categories.R

class CategoryFragment : BaseFragment() {

    private val args by lazy { CategoryFragmentArgs.fromBundle(requireArguments()) }

    private val module by lazy {
        CategoryModule(appModule, this, args)
    }

    private val setup get() = module.categorySetup

    override val layoutRes get() = R.layout.fragment_category

    override val titleTextRes
        get() = if (args.categoryId.minusOneAsNull() == null) {
            R.string.create_category_title
        } else {
            R.string.edit_category_title
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup.run()
    }
}
