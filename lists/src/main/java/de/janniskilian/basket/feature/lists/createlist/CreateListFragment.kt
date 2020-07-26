package de.janniskilian.basket.feature.lists.createlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.feature.lists.R

@AndroidEntryPoint
class CreateListFragment : BaseFragment() {

    private val args by lazy { CreateListFragmentArgs.fromBundle(requireArguments()) }

    private val viewModel: CreateListViewModel by viewModels()

    private val setup by lazy {
        CreateListFragmentSetup(this, args, viewModel)
    }

    override val layoutRes get() = R.layout.fragment_create_list

    override val titleTextRes
        get() = if (args.shoppingListId.minusOneAsNull() == null) {
            R.string.create_list_title
        } else {
            R.string.edit_list_title
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.shoppingListId.minusOneAsNull()?.let {
            viewModel.setShoppingListId(ShoppingListId(it))
        }
        setup.run()
    }
}
