package de.janniskilian.basket.feature.lists.createlist

import de.janniskilian.basket.core.util.extension.extern.observe
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_create_list.*

class CreateListViewModelObserver(
	viewModel: CreateListViewModel,
	private val fragment: CreateListFragment
) : ViewModelObserver<CreateListViewModel>(viewModel) {

	override fun observe() {
		with(viewModel) {
			name.observe(fragment, ::renderName)
			selectedColor.observe(fragment) { renderColors() }
			error.observe(fragment, ::renderError)
			startList.observe(fragment, ::startListFragment)
			dismiss.observe(fragment) { fragment.dismiss() }
		}
	}

	private fun renderName(name: String) {
		if (name != fragment.nameEditText.text.toString()) {
			fragment.nameEditText.setText(name)
		}
	}

	private fun renderColors() {
		val selectedColor = viewModel.selectedColor.value

		(fragment.recyclerView.adapter as? ColorsAdapter)?.submitList(
			viewModel.colors.map {
				ColorsAdapter.Item(it, it == selectedColor)
			}
		)
	}

	private fun renderError(error: Boolean) {
		fragment.nameLayout.error = if (error) {
			fragment.getString(R.string.shopping_list_name_error)
		} else {
			null
		}
	}

	private fun startListFragment(shoppingListId: Long) {
		/*fragment.findNavController().navigate(
			R.id.listFragment,
			ListFragmentArgs.Builder(shoppingListId).build().toBundle()
		)*/
		fragment.dismiss()
	}
}
