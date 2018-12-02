package de.janniskilian.basket.feature.lists.createlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.BaseBottomSheetDialogFragment
import de.janniskilian.basket.core.appModule
import de.janniskilian.basket.core.bindArgs
import de.janniskilian.basket.core.putArgs
import de.janniskilian.basket.core.type.datapassing.CreateListFragmentArgs
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.extension.extern.onTextChanged
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.core.util.recyclerview.ItemSpacingDecoration
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_create_list.*

class CreateListFragment : BaseBottomSheetDialogFragment() {

	private val args: CreateListFragmentArgs by bindArgs()

	private val module by lazy {
		CreateListModule(appModule, this, args)
	}

	private val viewModel get() = module.viewModel

	private val viewModelObserver get() = module.createListViewModelObserver

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View =
		inflater.inflate(
			R.layout.fragment_create_list,
			container,
			false
		)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setupNameEditText()
		setupButton()
		setupRecyclerView()
		setClickListeners()

		viewModelObserver.observe()
	}

	private fun setupNameEditText() {
		nameEditText.onTextChanged {
			viewModel.setName(it)
		}
	}

	private fun setupButton() {
		val textRes = if (args.shoppingListId == null) {
			R.string.create_list_button
		} else {
			R.string.save_list_button
		}
		createButton.setText(textRes)
	}

	private fun setupRecyclerView() {
		with(recyclerView) {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
			adapter = ColorsAdapter()
			(itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
			addItemDecoration(
				ItemSpacingDecoration(
					resources.getDimensionPixelSize(R.dimen.one),
					RecyclerView.HORIZONTAL
				)
			)
			addItemDecoration(
				EndSpacingDecoration(
					resources.getDimensionPixelSize(R.dimen.one),
					RecyclerView.HORIZONTAL,
					EndSpacingDecoration.Position.START_AND_END
				)
			)
		}
	}

	private fun setClickListeners() {
		createButton.setOnClickListener { viewModel.submitButtonClicked() }
		nameEditText.onDone(viewModel::submitButtonClicked)
		(recyclerView.adapter as? ColorsAdapter)?.clickListener = viewModel::setSelectedColor
	}

	companion object {

		fun create(
			args: CreateListFragmentArgs = CreateListFragmentArgs(null)
		): CreateListFragment =
			CreateListFragment().putArgs(args)
	}
}
