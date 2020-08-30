package de.janniskilian.basket.feature.lists.createlist

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.doOnTextChanged
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.extension.extern.toggleSoftKeyboard
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.core.util.recyclerview.ItemSpacingDecoration
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_create_list.*

class CreateListFragmentSetup(
    private val fragment: CreateListFragment,
    args: CreateListFragmentArgs,
    private val viewModel: CreateListViewModel
) {

    private val shoppingListId = args.shoppingListId
        .minusOneAsNull()
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

    private fun setupNameEditText() = with(fragment) {
        nameEditText.doOnTextChanged(viewModel::setName)
        nameEditText.onDone(viewModel::submitButtonClicked)

        if (shoppingListId == null) {
            nameEditText.toggleSoftKeyboard(true)
        }
    }

    private fun setupButton() = with(fragment) {
        val textRes = if (shoppingListId == null) {
            R.string.create_list_button
        } else {
            R.string.save_list_button
        }
        createButton.setText(textRes)
    }

    private fun setupRecyclerView() = with(fragment.recyclerView) {
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

    private fun setClickListeners() = with(fragment) {
        createButton.setOnClickListener { viewModel.submitButtonClicked() }
        nameEditText.onDone(viewModel::submitButtonClicked)
        (recyclerView.adapter as? ColorsAdapter)?.itemClickListener =
            viewModel::setSelectedColor
    }
}
