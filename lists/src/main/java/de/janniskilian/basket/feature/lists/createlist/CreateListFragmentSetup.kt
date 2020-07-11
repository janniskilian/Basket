package de.janniskilian.basket.feature.lists.createlist

import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.extension.extern.doOnTextChanged
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.recyclerview.EndSpacingDecoration
import de.janniskilian.basket.core.util.recyclerview.ItemSpacingDecoration
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_create_list.*

class CreateListFragmentSetup(
    private val fragment: CreateListFragment,
    args: CreateListFragmentArgs,
    private val viewModel: CreateListViewModel,
    private val viewModelObserver: CreateListViewModelObserver
) {

    private val shoppingListId = args.shoppingListId.minusOneAsNull()

    fun run() {
        setupHeadline()
        setupNameEditText()
        setupButton()
        setupRecyclerView()
        setClickListeners()

        viewModelObserver.observe()
    }

    private fun setupHeadline() {
        val headlineTextRes = if (shoppingListId == null) {
            R.string.create_list_headline
        } else {
            R.string.edit_list_headline
        }

        fragment
            .requireView()
            .findViewById<TextView>(R.id.headline)
            .setText(headlineTextRes)
    }

    private fun setupNameEditText() {
        fragment.nameEditText.doOnTextChanged(viewModel::setName)
    }

    private fun setupButton() {
        val textRes = if (shoppingListId == null) {
            R.string.create_list_button
        } else {
            R.string.save_list_button
        }
        fragment.createButton.setText(textRes)
    }

    private fun setupRecyclerView() {
        with(fragment.recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                fragment.requireContext(),
                RecyclerView.HORIZONTAL,
                false
            )
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
    }

    private fun setClickListeners() {
        with(fragment) {
            createButton.setOnClickListener { viewModel.submitButtonClicked() }
            nameEditText.onDone(viewModel::submitButtonClicked)
            (recyclerView.adapter as? ColorsAdapter)?.itemClickListener = viewModel::setSelectedColor
        }
    }
}
