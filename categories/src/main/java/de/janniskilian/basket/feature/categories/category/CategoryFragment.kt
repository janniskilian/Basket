package de.janniskilian.basket.feature.categories.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.feature.categories.R
import de.janniskilian.basket.feature.categories.databinding.CategoryFragmentBinding

@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryFragmentBinding>() {

    private val args by lazy { CategoryFragmentArgs.fromBundle(requireArguments()) }

    private val viewModel: CategoryViewModel by viewModels()

    private val setup by lazy {
        CategoryFragmentSetup(this, args, viewModel)
    }

    override val useDefaultTransitions get() = false

    override val titleTextRes
        get() = if (args.categoryId.minusOneAsNull() == null) {
            R.string.create_category_title
        } else {
            R.string.edit_category_title
        }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        CategoryFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup.run()
    }
}
