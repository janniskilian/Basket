package de.janniskilian.basket.feature.lists.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.type.datapassing.CreateListFragmentArgs
import de.janniskilian.basket.core.type.domain.ShoppingList
import de.janniskilian.basket.core.util.extension.extern.observe
import de.janniskilian.basket.core.util.extension.extern.requireView
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.core.util.recyclerview.ItemSpacingDecoration
import de.janniskilian.basket.feature.lists.R
import de.janniskilian.basket.feature.lists.createlist.CreateListFragment
import kotlinx.android.synthetic.main.fragment_lists.*

class ListsFragment : BaseFragment() {

	private val module by lazy {
		ListsModule(appModule, this)
	}

	private val viewModel get() = module.listsViewModel

	private val listsAdapter get() = recyclerView.adapter as? ListsAdapter

	override val fabTextRes: Int?
		get() = R.string.fab_create_shopping_list

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? =
		inflater.inflate(
			R.layout.fragment_lists,
			container,
			false
		)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setupRecyclerView()
		setClickListeners()
		viewModel.shoppingLists.observe(this, ::shoppingListsObserver)
		viewModel.shoppingListDeleted.observe(this) { showListDeletedSnackbar() }
	}

	override fun onFabClicked() {
		showBottomSheetFragment(CreateListFragment.create())
	}

	private fun setupRecyclerView() {
		with(recyclerView) {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireContext())
			adapter = ListsAdapter()
			(itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
			addItemDecoration(
				ItemSpacingDecoration(
					resources.getDimensionPixelSize(R.dimen.shopping_list_item_spacing),
					RecyclerView.VERTICAL
				)
			)
			addItemDecoration(
				EndSpacingDecoration(
					resources.getDimensionPixelSize(R.dimen.shopping_list_item_spacing),
					RecyclerView.VERTICAL,
					EndSpacingDecoration.Position.START_AND_END
				)
			)
		}
	}

	private fun setClickListeners() {
		listsAdapter?.itemClickListener = { startList(it) }
		listsAdapter?.moreButtonClickListener = { position ->
			recyclerView.findViewHolderForAdapterPosition(position)?.let { viewHolder ->
				with(
					PopupMenu(
						requireContext(),
						viewHolder.itemView.findViewById(R.id.moreButton)
					)
				) {
					inflate(R.menu.list_item)
					setOnMenuItemClickListener {
						when (it.itemId) {
							R.id.action_edit_list -> editList(position)
							R.id.action_delete_list -> {
								viewModel.deleteList(position)
							}
						}

						true
					}
					show()
				}
			}
		}
	}

	private fun shoppingListsObserver(shoppingLists: List<ShoppingList>) {
		listsAdapter?.submitList(shoppingLists)
		emptyGroup.isVisible = shoppingLists.isEmpty()
	}

	private fun showListDeletedSnackbar() {
		Snackbar
			.make(requireView(), R.string.list_deleted_snackbar, Snackbar.LENGTH_LONG)
			.setAction(R.string.restore_button) { viewModel.restoreShoppingList() }
			.show()
	}

	private fun startList(position: Int) {
		viewModel.shoppingLists.value?.getOrNull(position)?.let {
			findNavController().navigate(
				ListsFragmentDirections.actionListsFragmentToListFragment(it.id)
			)
		}
	}

	private fun editList(position: Int) {
		viewModel.shoppingLists.value?.getOrNull(position)?.let { shoppingList ->
			CreateListFragment
				.create(CreateListFragmentArgs(shoppingList.id))
				.let {
					it.show(fragmentManager, it.tag)
				}
		}
	}
}
