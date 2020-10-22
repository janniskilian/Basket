package de.janniskilian.basket.feature.categories.category

import androidx.core.view.isVisible
import de.janniskilian.basket.core.type.domain.CategoryId
import de.janniskilian.basket.core.util.extension.extern.doOnTextChanged
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.core.util.extension.extern.onDone
import de.janniskilian.basket.feature.categories.R

class CategoryFragmentSetup(
    private val fragment: CategoryFragment,
    args: CategoryFragmentArgs,
    private val viewModel: CategoryViewModel
) {

    private val categoryId = args.categoryId
        .minusOneAsNull()
        ?.let(::CategoryId)

    private val viewModelObserver = CategoryViewModelObserver(fragment, viewModel)

    fun run() {
        categoryId?.let(viewModel::setCategoryId)

        setupButtons()
        setClickListeners()
        setupNameEditText()

        viewModelObserver.observe()
    }

    private fun setupButtons() = with(fragment.binding) {
        deleteButton.isVisible = categoryId != null

        val buttonTextRes = if (categoryId == null) {
            R.string.create_category_button
        } else {
            R.string.save_category_button
        }
        submitButton.setText(buttonTextRes)
    }

    private fun setClickListeners() = with(fragment.binding) {
        nameEditText.onDone(viewModel::submitButtonClicked)
        submitButton.setOnClickListener { viewModel.submitButtonClicked() }
        deleteButton.setOnClickListener { viewModel.deleteButtonClicked() }
        submitButton.setOnClickListener { viewModel.submitButtonClicked() }
    }

    private fun setupNameEditText() = with(fragment.binding) {
        nameEditText.doOnTextChanged(viewModel::setName)
    }
}
