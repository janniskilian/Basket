package de.janniskilian.basket.feature.lists.list.itemorder

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseBottomSheetDialogFragment
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_list_item_order.*

@AndroidEntryPoint
class ListItemOrderDialog : BaseBottomSheetDialogFragment() {

    private val args by navArgs<ListItemOrderDialogArgs>()

    private val viewModel: ListItemOrderViewModel by viewModels()

    override val layoutRes get() = R.layout.fragment_list_item_order

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setShoppingListId(ShoppingListId(args.shoppingListId))

        viewModel.isGroupedByCategory.observe(viewLifecycleOwner) {
            isGroupedByCategoryCheckBox.isChecked = it
        }

        viewModel.dismiss.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        button.setOnClickListener {
            viewModel.submit(isGroupedByCategoryCheckBox.isChecked)
        }
    }
}
