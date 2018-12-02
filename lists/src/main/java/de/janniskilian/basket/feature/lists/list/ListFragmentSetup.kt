package de.janniskilian.basket.feature.lists.list

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.core.type.domain.ShoppingListItem
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragmentSetup(
	private val fragment: ListFragment,
	private val viewModelObserver: ListViewModelObserver,
	private val dataClient: DataClient
) {

	fun run() {
		setupRecyclerView()
		viewModelObserver.observe()
	}

	private fun setupRecyclerView() {
		with(fragment.recyclerView) {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(
				fragment.requireContext(),
				RecyclerView.VERTICAL,
				false
			)
			adapter = ShoppingListAdapter(fragment.requireContext())
			(itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
			addItemDecoration(
				EndSpacingDecoration(
					fragment.resources.getDimensionPixelSize(R.dimen.five),
					RecyclerView.VERTICAL,
					EndSpacingDecoration.Position.END
				)
			)
		}

		fragment.shoppingListAdapter?.listItemClickListener = ::listItemClicked
	}

	private fun listItemClicked(shoppingListItem: ShoppingListItem) {
		dataClient.shoppingListItem.update(
			shoppingListItem.copy(checked = !shoppingListItem.checked)
		)
	}
}
