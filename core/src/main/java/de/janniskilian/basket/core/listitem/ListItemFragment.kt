package de.janniskilian.basket.core.listitem

import android.os.Bundle
import android.view.View
import androidx.lifecycle.map
import androidx.lifecycle.observe
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.R
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.function.createUiListColor
import kotlinx.android.synthetic.main.fragment_list_item.*

class ListItemFragment : BaseFragment() {

    private val args by lazy { ListItemFragmentArgs.fromBundle(requireArguments()) }

    private val module by lazy {
        ListItemModule(appModule, this, args)
    }

    private val viewModel get() = module.listItemViewModel

    override val layoutRes get() = R.layout.fragment_list_item

    override val appBarColor by lazy {
        viewModel.shoppingList.map {
            createUiListColor(requireContext(), it.color)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.shoppingListItem.observe(viewLifecycleOwner) {
            articleNameEditText.setText(it.article.name)
        }
    }
}
