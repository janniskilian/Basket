package de.janniskilian.basket.feature.lists.addlistitem

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.doOnTextChanged
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.extension.extern.toggleSoftKeyboard
import de.janniskilian.basket.core.util.function.createSpeechInputIntent
import kotlinx.android.synthetic.main.fragment_add_list_item.*

class AddListItemFragmentSetup(
    private val fragment: AddListItemFragment,
    private val args: AddListItemFragmentArgs,
    private val viewModel: AddListItemViewModel
) {

    private val viewModelObserver = AddListItemViewModelObserver(fragment, viewModel)

    fun run() {
        viewModel.setShoppingListId(ShoppingListId(args.shoppingListId))

        setupRecyclerView()
        setupInputEditText()
        setClickListeners()

        viewModelObserver.observe()
    }

    private fun setupRecyclerView() = with(fragment.colorsRecyclerView) {
        layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            true
        ).apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        adapter = ShoppingListItemSuggestionsAdapter()
        addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun setupInputEditText() = with(fragment.inputEditText) {
        doOnTextChanged(viewModel::setInput)
        onDone { viewModel.inputDoneButtonClicked() }
        toggleSoftKeyboard(true)
    }

    private fun setClickListeners() = with(fragment) {
        suggestionsAdapter?.clickListener = viewModel::suggestionItemClicked

        upButton.setOnClickListener {
            fragment
                .findNavController()
                .popBackStack()
        }

        searchBarSpeechInputButton.setOnClickListener {
            if (viewModel.input.value.isNullOrEmpty()) {
                startActivityForResult(createSpeechInputIntent(), REQ_SPEECH_INPUT)
            } else {
                viewModel.clearInput()
            }
        }
    }
}
