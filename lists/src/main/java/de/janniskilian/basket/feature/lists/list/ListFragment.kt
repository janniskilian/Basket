package de.janniskilian.basket.feature.lists.list

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.function.createUiListColor
import de.janniskilian.basket.feature.lists.R
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_check_all_list_items -> viewModel.setAllListItemsChecked(true)

            R.id.action_uncheck_all_list_items -> viewModel.setAllListItemsChecked(false)

            R.id.action_remove_all_checked_list_items -> viewModel.removeAllCheckedListItems()

            R.id.action_remove_all_list_items -> viewModel.removeAllListItems()

            else -> return false
        }

        return true
    }

    override fun onFabClicked() {
        navigate(
            ListFragmentDirections.actionListFragmentToAddListItemFragment(args.shoppingListId)
        )
    }

    fun startListItem(listItem: ShoppingListItem) {
        navigate(ListFragmentDirections.actionListFragmentToListItemFragment(listItem.id.value))
    }
}
