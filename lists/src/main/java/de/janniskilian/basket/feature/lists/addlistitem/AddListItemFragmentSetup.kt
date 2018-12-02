package de.janniskilian.basket.feature.lists.addlistitem

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.util.extension.extern.observe
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.extension.extern.onTextChanged
import de.janniskilian.basket.core.util.extension.extern.setSelectedImageState
import de.janniskilian.basket.core.util.function.createSpeechInputIntent
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_add_list_item.*

class AddListItemFragmentSetup(
	private val fragment: AddListItemFragment,
	private val viewModel: AddListItemViewModel
) {

	private val suggestionsAdapter
		get() = fragment.recyclerView.adapter as? ShoppingListItemSuggestionsAdapter

	fun run() {
		setupRecyclerView()
		setupInputEditText()
		setClickListeners()

		viewModel.input.observe(fragment) { input ->
			if (fragment.inputEditText.text.toString() != input) {
				fragment.inputEditText.setText(input)
			}

			val showSpeechInput = input.isEmpty()
			fragment.searchBarSpeechInputButton.setSelectedImageState(!showSpeechInput)
			fragment.searchBarSpeechInputButton.contentDescription = fragment.getString(
				if (showSpeechInput) {
					R.string.speech_input_button_desc
				} else {
					R.string.clear_input_button_desc
				}
			)
		}

		viewModel.items.observe(fragment) { suggestions ->
			suggestionsAdapter?.submitList(
				suggestions.map {
					ShoppingListItemSuggestionsAdapter.Item(it)
				}
			)
		}
	}

	private fun setupRecyclerView() {
		with(fragment.recyclerView) {
			layoutManager = LinearLayoutManager(
				fragment.requireContext(),
				RecyclerView.VERTICAL,
				true
			).apply {
				(itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
			}
			adapter = ShoppingListItemSuggestionsAdapter()
			addItemDecoration(
				DividerItemDecoration(
					fragment.requireContext(),
					DividerItemDecoration.VERTICAL
				)
			)
		}
	}

	private fun setupInputEditText() {
		with(fragment.inputEditText) {
			onTextChanged {
				viewModel.setInput(it)
			}
			backPressedListener = fragment::dismiss
			onDone { viewModel.inputDoneButtonClicked() }
		}
	}

	private fun setClickListeners() {
		suggestionsAdapter?.clickListener = viewModel::suggestionItemClicked

		fragment.upButton.setOnClickListener { fragment.dismiss() }

		fragment.searchBarSpeechInputButton.setOnClickListener {
			if (viewModel.input.value.isNullOrEmpty()) {
				fragment.startActivityForResult(
					createSpeechInputIntent(),
					REQ_SPEECH_INPUT
				)
			} else {
				viewModel.clearInput()
			}
		}
	}
}
