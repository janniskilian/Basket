package de.janniskilian.basket.feature.lists.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment() {

    private val args by lazy { ListFragmentArgs.fromBundle(requireArguments()) }

    private val module by lazy {
        ListModule(appModule, this, args)
    }

    private val viewModel get() = module.listViewModel

    private val setup get() = module.listSetup

    val shoppingListAdapter get() = recyclerView.adapter as? ShoppingListAdapter

    var recreated = false

    override val menuRes: Int?
        get() = R.menu.list

    override val fabTextRes: Int?
        get() = R.string.fab_add_list_item

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(
            R.layout.fragment_list,
            container,
            false
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recreated = savedInstanceState != null
        setup.run()
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationContainer.setAppBarColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.primary
            ),
            true
        )
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
