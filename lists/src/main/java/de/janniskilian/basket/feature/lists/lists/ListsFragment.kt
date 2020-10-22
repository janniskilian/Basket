package de.janniskilian.basket.feature.lists.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.ResultCode
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.feature.lists.R
import de.janniskilian.basket.feature.lists.databinding.ListsFragmentBinding
import de.janniskilian.basket.feature.lists.sendShoppingList

@AndroidEntryPoint
class ListsFragment : BaseFragment<ListsFragmentBinding>() {

    private val viewModel: ListsViewModel by viewModels()

    val listsAdapter get() = binding.recyclerView.adapter as? ListsAdapter

    private val setup by lazy {
        ListsFragmentSetup(this, viewModel)
    }

    override val titleTextRes get() = R.string.shopping_lists_title

    override val fabTextRes get() = R.string.fab_create_shopping_list

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ListsFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup.run()
    }

    override fun onNavigationResult(requestCode: Int, resultCode: ResultCode, data: Any?) {
        if (requestCode == REQ_LIST_MENU
            && resultCode == ResultCode.SUCCESS
            && data is ListsItemMenuDialog.Result
        ) {
            handleListMenuResult(data)
        }
    }

    override fun onFabClicked() {
        navigate(ListsFragmentDirections.actionListsFragmentToCreateListFragment())
    }

    private fun handleListMenuResult(data: ListsItemMenuDialog.Result) {
        val shoppingListId = ShoppingListId(data.shoppingListId)

        when (data.menuItemId) {
            R.id.action_edit_list -> editList(shoppingListId)
            R.id.action_delete_list -> viewModel.deleteList(shoppingListId)
            R.id.action_send_list -> sendList(shoppingListId)
        }
    }

    fun showListMenu(shoppingListId: ShoppingListId) {
        findShoppingList(shoppingListId)?.let {
            navigateWithResult(
                ListsFragmentDirections.actionListsFragmentToListsItemMenuDialog(it.id.value),
                REQ_LIST_MENU
            )
        }
    }

    fun startList(shoppingListId: ShoppingListId) {
        findShoppingList(shoppingListId)?.let {
            navigate(ListsFragmentDirections.actionListsFragmentToListFragment(it.id.value))
        }
    }

    private fun editList(shoppingListId: ShoppingListId) {
        findShoppingList(shoppingListId)?.let {
            navigate(ListsFragmentDirections.actionListsFragmentToCreateListFragment(it.id.value))
        }
    }

    private fun sendList(shoppingListId: ShoppingListId) {
        findShoppingList(shoppingListId)?.let {
            sendShoppingList(requireContext(), it)
        }
    }

    private fun findShoppingList(shoppingListId: ShoppingListId): ShoppingList? =
        viewModel
            .shoppingLists
            .value
            ?.find { it.id == shoppingListId }

    companion object {

        private const val REQ_LIST_MENU = 1
    }
}
