package de.janniskilian.basket.feature.lists.addlistitem

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.android.view.doOnTextChanged
import de.janniskilian.basket.core.util.android.view.onDone
import de.janniskilian.basket.core.util.android.view.toggleSoftKeyboard

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

    private fun setupRecyclerView() = with(fragment.binding.recyclerView) {
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

    private fun setupInputEditText() = with(fragment.binding.inputEditText) {
        doOnTextChanged(viewModel::setInput)
        onDone { viewModel.inputDoneButtonClicked() }
        toggleSoftKeyboard(true)
    }

    private fun setClickListeners() = with(fragment) {
        suggestionsAdapter?.clickListener = viewModel::suggestionItemClicked

        binding.upButton.setOnClickListener {
            fragment
                .findNavController()
                .popBackStack()
        }

        binding.searchBarSpeechInputButton.setOnClickListener {
            if (viewModel.input.value.isNullOrEmpty()) {
                fragment.speechInputLauncher.launch(Unit)
            } else {
                viewModel.clearInput()
            }
        }
    }
}
