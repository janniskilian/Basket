package de.janniskilian.basket.feature.lists.createlist

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.android.maybe
import de.janniskilian.basket.core.util.android.view.doOnTextChanged
import de.janniskilian.basket.core.util.android.view.onDone
import de.janniskilian.basket.core.util.android.view.toggleSoftKeyboard
import de.janniskilian.basket.core.util.android.view.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.core.util.android.view.recyclerview.ItemSpacingDecoration
import de.janniskilian.basket.feature.lists.R

class CreateListFragmentSetup(
    private val fragment: CreateListFragment,
    args: CreateListFragmentArgs,
    private val viewModel: CreateListViewModel
) {

    private val shoppingListId = args.shoppingListId
        .maybe()
        ?.let(::ShoppingListId)

    private val viewModelObserver = CreateListViewModelObserver(fragment, viewModel)

    fun run() {
        shoppingListId?.let(viewModel::setShoppingListId)

        setupNameEditText()
        setupButton()
        setupRecyclerView()
        setClickListeners()

        viewModelObserver.observe()
    }

    private fun setupNameEditText() = with(fragment.binding) {
        nameEditText.doOnTextChanged(viewModel::setName)
        nameEditText.onDone(viewModel::submitButtonClicked)

        if (shoppingListId == null) {
            nameEditText.toggleSoftKeyboard(true)
        }
    }

    private fun setupButton() = with(fragment.binding) {
        val textRes = if (shoppingListId == null) {
            R.string.create_list_button
        } else {
            R.string.save_list_button
        }
        createButton.setText(textRes)
    }

    private fun setupRecyclerView() = with(fragment.binding.colorsRecyclerView) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        adapter = ColorsAdapter()
        (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false

        val spacing = resources.getDimensionPixelSize(R.dimen.one)
        addItemDecoration(
            ItemSpacingDecoration(
                spacing,
                RecyclerView.HORIZONTAL
            )
        )
        addItemDecoration(
            EndSpacingDecoration(
                spacing,
                spacing,
                RecyclerView.HORIZONTAL
            )
        )
    }

    private fun setClickListeners() = with(fragment.binding) {
        createButton.setOnClickListener { viewModel.submitButtonClicked() }
        nameEditText.onDone(viewModel::submitButtonClicked)
        (colorsRecyclerView.adapter as? ColorsAdapter)?.itemClickListener =
            viewModel::setSelectedColor
    }
}
