package de.janniskilian.basket.feature.lists.list

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.ActionMenuBottomSheetDialog
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.NavGraphDirections
import de.janniskilian.basket.core.ResultCode
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.extension.extern.keepScreenOn
import de.janniskilian.basket.core.util.function.createUiListColor
import de.janniskilian.basket.feature.lists.R
import de.janniskilian.basket.feature.lists.sendShoppingList
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : BaseFragment() {

    private val args by lazy { ListFragmentArgs.fromBundle(requireArguments()) }

    private val viewModel: ListViewModel by viewModels()

    private val setup by lazy {
        ListFragmentSetup(this, args, viewModel)
    }

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    val shoppingListAdapter get() = recyclerView.adapter as? ShoppingListAdapter

    private var isRecreated = false

    override val layoutRes get() = R.layout.fragment_list

    override val menuRes get() = R.menu.list

    override val fabTextRes get() = R.string.fab_add_list_item

    override val appBarColor by lazy {
        viewModel.shoppingList.map {
            createUiListColor(requireContext(), it.color)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isRecreated = savedInstanceState != null
        setup.run()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity()
            .window
            .keepScreenOn(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_select_item_order -> startItemOrderDialog()

            R.id.action_overflow -> startOverflowDialog()

            else -> return false
        }

        return true
    }

    override fun onNavigationResult(requestCode: Int, resultCode: ResultCode, data: Any?) {
        if (requestCode == REQ_OVERFLOW
            && resultCode == ResultCode.SUCCESS
            && data is ActionMenuBottomSheetDialog.Result
        ) {
            when (data.menuItemId) {
                R.id.action_check_all_list_items -> viewModel.setAllListItemsChecked(true)

                R.id.action_uncheck_all_list_items -> viewModel.setAllListItemsChecked(false)

                R.id.action_remove_all_checked_list_items -> viewModel.removeAllCheckedListItems()

                R.id.action_remove_all_list_items -> viewModel.removeAllListItems()

                R.id.action_send_list -> sendList()
            }
        }
    }

    override fun onFabClicked() {
        navigate(
            ListFragmentDirections.actionListFragmentToAddListItemFragment(args.shoppingListId)
        )
    }

    fun startListItem(listItem: ShoppingListItem) {
        navigate(ListFragmentDirections.actionListFragmentToListItemFragment(listItem.id.value))
    }

    private fun startItemOrderDialog() {
        viewModel
            .shoppingList
            .value
            ?.let {
                findNavController().navigate(
                    ListFragmentDirections
                        .actionListFragmentToListItemOrderDialog(it.id.value)
                )
            }
    }

    private fun startOverflowDialog() {
        navigateWithResult(
            NavGraphDirections.toActionMenuBottomSheetDialog(R.menu.list_overflow),
            REQ_OVERFLOW
        )
    }

    private fun sendList() {
        viewModel
            .shoppingList
            .value
            ?.let {
                sendShoppingList(requireContext(), it)
            }
    }

    companion object {

        private const val REQ_OVERFLOW = 1
    }
}
