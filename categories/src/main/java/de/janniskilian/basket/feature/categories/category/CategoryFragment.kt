package de.janniskilian.basket.feature.categories.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.janniskilian.basket.core.BaseBottomSheetDialogFragment
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.getArgs
import de.janniskilian.basket.core.putArgs
import de.janniskilian.basket.core.type.datapassing.CategoryFragmentArgs
import de.janniskilian.basket.feature.categories.R

class CategoryFragment : BaseBottomSheetDialogFragment() {

    private val module by lazy {
        CategoryModule(appModule, this, getArgs())
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

    companion object {

        fun create(args: CategoryFragmentArgs): CategoryFragment =
            CategoryFragment().putArgs(args)
    }
}
