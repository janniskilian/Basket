package de.janniskilian.basket.feature.lists.createlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.feature.lists.R
import de.janniskilian.basket.feature.lists.databinding.CreateListFragmentBinding

@AndroidEntryPoint
class CreateListFragment : BaseFragment<CreateListFragmentBinding>() {

    private val args by lazy { CreateListFragmentArgs.fromBundle(requireArguments()) }

    private val viewModel: CreateListViewModel by viewModels()

    private val setup by lazy {
        CreateListFragmentSetup(this, args, viewModel)
    }

    override val titleTextRes
        get() = if (args.shoppingListId.minusOneAsNull() == null) {
            R.string.create_list_title
        } else {
            R.string.edit_list_title
        }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        CreateListFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.shoppingListId
            .minusOneAsNull()
            ?.let {
                viewModel.setShoppingListId(ShoppingListId(it))
            }
        setup.run()
    }
}
