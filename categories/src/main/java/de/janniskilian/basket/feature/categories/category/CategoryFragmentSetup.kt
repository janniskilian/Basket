package de.janniskilian.basket.feature.categories.category

import androidx.core.view.isVisible
import de.janniskilian.basket.core.type.datapassing.CategoryFragmentArgs
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.core.util.extension.extern.onTextChanged
import de.janniskilian.basket.feature.categories.R
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragmentSetup(
    private val fragment: CategoryFragment,
    private val args: CategoryFragmentArgs,
    private val viewModel: CategoryViewModel,
    private val viewModelObserver: CategoryViewModelObserver
) {

    fun run() {
        setupButtons()
        setClickListeners()
        setupNameEditText()

        viewModelObserver.observe()
    }

    private fun setupButtons() {
        fragment.deleteButton.isVisible = args.categoryId != null

        val buttonTextRes = if (args.categoryId == null) {
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
