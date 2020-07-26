package de.janniskilian.basket.core.listitem

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.R
import de.janniskilian.basket.core.util.function.createUiListColor

@AndroidEntryPoint
class ListItemFragment : BaseFragment() {

    private val args by lazy { ListItemFragmentArgs.fromBundle(requireArguments()) }

    private val viewModel: ListItemViewModel by viewModels()

    private val setup by lazy {
        ListItemFragmentSetup(this, args, viewModel)
    }

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
