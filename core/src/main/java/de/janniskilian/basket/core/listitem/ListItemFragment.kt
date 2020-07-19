package de.janniskilian.basket.core.listitem

import android.os.Bundle
import android.view.View
import androidx.lifecycle.map
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.R
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.function.createUiListColor

class ListItemFragment : BaseFragment() {

    private val args by lazy { ListItemFragmentArgs.fromBundle(requireArguments()) }

    private val module by lazy {
        ListItemModule(appModule, this, args)
    }

    private val setup get() = module.setup

    private val viewModel get() = module.viewModel

    override val layoutRes get() = R.layout.fragment_list_item

    override val appBarColor by lazy {
        viewModel.shoppingList.map {
            createUiListColor(requireContext(), it.color)
        }
    }

    override val titleTextRes get() = R.string.list_item_title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup.run()
    }
}
