package de.janniskilian.basket.feature.lists.list

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.ui.fragments.ActionMenuBottomSheetDialog
import de.janniskilian.basket.core.ui.fragments.BaseFragment
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.ui.NavGraphDirections
import de.janniskilian.basket.core.ui.navigation.ResultCode
import de.janniskilian.basket.core.util.android.createContainerTransformNavigatorExtras
import de.janniskilian.basket.core.util.android.keepScreenOn
import de.janniskilian.basket.core.util.function.createUiListColor
import de.janniskilian.basket.feature.lists.R
import de.janniskilian.basket.feature.lists.databinding.ListFragmentBinding
import de.janniskilian.basket.feature.lists.ShortcutController
import de.janniskilian.basket.feature.lists.sendShoppingList
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : BaseFragment<ListFragmentBinding>() {

    private val args by lazy { ListFragmentArgs.fromBundle(requireArguments()) }

    private val viewModel: ListViewModel by viewModels()

    private val setup by lazy {
        ListFragmentSetup(this, args, viewModel, shortcutController)
    }

    private val shortcutController by lazy {
        ShortcutController(requireContext())
    }

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    val shoppingListAdapter get() = binding.recyclerView.adapter as? ShoppingListAdapter

    private var isRecreated = false

    override val menuRes get() = R.menu.list

    override val fabTextRes get() = R.string.fab_add_list_item

    override val appBarColor by lazy {
        viewModel.shoppingList.map {
            createUiListColor(requireContext(), it.color)
        }
    }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ListFragmentBinding.inflate(inflater, container, false)

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
            R.id.action_select_item_order -> navigateToItemOrderDialog()

            R.id.action_overflow -> navigateToOverflowDialog()

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

    fun navigateToListItem(position: Int, listItem: ShoppingListItem) {
        binding
            .recyclerView
            .findViewHolderForAdapterPosition(position)
            ?.itemView
            ?.let {
                navigate(
                    ListFragmentDirections.actionListFragmentToListItemFragment(listItem.id.value),
                    createContainerTransformNavigatorExtras(it)
                )
            }
    }

    private fun navigateToItemOrderDialog() {
        viewModel
            .shoppingList
            .value
            ?.let {
                navigate(
                    ListFragmentDirections.actionListFragmentToListItemOrderDialog(it.id.value)
                )
            }
    }

    private fun navigateToOverflowDialog() {
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
