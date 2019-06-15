package de.janniskilian.basket.feature.categories.category

import android.widget.TextView
import androidx.core.view.isVisible
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.extension.extern.onTextChanged
import de.janniskilian.basket.feature.categories.R
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragmentSetup(
    private val fragment: CategoryFragment,
    args: CategoryFragmentArgs,
    private val viewModel: CategoryViewModel,
    private val viewModelObserver: CategoryViewModelObserver
) {

    private val categoryId = args.categoryId.minusOneAsNull()

    fun run() {
        setupHeadline()
        setupButtons()
        setClickListeners()
        setupNameEditText()

        viewModelObserver.observe()
    }

    private fun setupHeadline() {
        val headlineTextRes = if (categoryId == null) {
            R.string.create_category_headline
        } else {
            R.string.edit_category_headline
        }

        fragment
            .requireView()
            .findViewById<TextView>(R.id.headline)
            .setText(headlineTextRes)
    }

    private fun setupButtons() {
        fragment.deleteButton.isVisible = categoryId != null

        val buttonTextRes = if (categoryId == null) {
            R.string.create_category_button
        } else {
            R.string.save_category_button
        }
        fragment.submitButton.setText(buttonTextRes)
    }

    private fun setClickListeners() {
        fragment.nameEditText.onDone(viewModel::submitButtonClicked)
        fragment.submitButton.setOnClickListener { viewModel.submitButtonClicked() }
        fragment.deleteButton.setOnClickListener { viewModel.deleteButtonClicked() }
        fragment.submitButton.setOnClickListener { viewModel.submitButtonClicked() }
    }

    private fun setupNameEditText() {
        fragment.nameEditText.onTextChanged(viewModel::setName)
    }
}
