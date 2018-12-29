package de.janniskilian.basket.feature.lists.createlist

import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.extension.extern.onTextChanged
import de.janniskilian.basket.core.util.extension.extern.requireView
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
        fragment.nameEditText.onTextChanged {
            viewModel.setName(it)
        }
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
            addItemDecoration(
                ItemSpacingDecoration(
                    resources.getDimensionPixelSize(R.dimen.one),
                    RecyclerView.HORIZONTAL
                )
            )
            addItemDecoration(
                EndSpacingDecoration(
                    resources.getDimensionPixelSize(de.janniskilian.basket.feature.lists.R.dimen.one),
                    RecyclerView.HORIZONTAL,
                    EndSpacingDecoration.Position.START_AND_END
                )
            )
        }
    }

    private fun setClickListeners() {
        with(fragment) {
            createButton.setOnClickListener { viewModel.submitButtonClicked() }
            nameEditText.onDone(viewModel::submitButtonClicked)
            (recyclerView.adapter as? ColorsAdapter)?.clickListener = viewModel::setSelectedColor
        }
    }
}