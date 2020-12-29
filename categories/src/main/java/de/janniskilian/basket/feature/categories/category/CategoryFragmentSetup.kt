package de.janniskilian.basket.feature.categories.category

import androidx.core.view.isVisible
import de.janniskilian.basket.core.type.domain.CategoryId
import de.janniskilian.basket.core.util.android.maybe
import de.janniskilian.basket.core.util.android.setupDetailContainerTransformTransition
import de.janniskilian.basket.core.util.android.view.doOnTextChanged
import de.janniskilian.basket.core.util.android.view.onDone
import de.janniskilian.basket.feature.categories.R
import de.janniskilian.basket.feature.categories.databinding.CategoryFragmentBinding

class CategoryFragmentSetup(
    private val fragment: CategoryFragment,
    args: CategoryFragmentArgs,
    private val viewModel: CategoryViewModel
) {

    private val categoryId = args
        .categoryId
        .maybe()
        ?.let(::CategoryId)

    private val viewModelObserver = CategoryViewModelObserver(fragment, viewModel)

    fun run() {
        categoryId?.let(viewModel::setCategoryId)

        fragment.setupDetailContainerTransformTransition()

        with(fragment.binding) {
            setupButtons()
            setClickListeners()
            setupNameEditText()
        }

        viewModelObserver.observe()
    }

    private fun CategoryFragmentBinding.setupButtons() {
        deleteButton.isVisible = categoryId != null

        val buttonTextRes = if (categoryId == null) {
            R.string.create_category_button
        } else {
            R.string.save_category_button
        }
        submitButton.setText(buttonTextRes)
    }

    private fun CategoryFragmentBinding.setClickListeners() {
        nameEditText.onDone(viewModel::submitButtonClicked)
        submitButton.setOnClickListener { viewModel.submitButtonClicked() }
        deleteButton.setOnClickListener { viewModel.deleteButtonClicked() }
        submitButton.setOnClickListener { viewModel.submitButtonClicked() }
    }

    private fun CategoryFragmentBinding.setupNameEditText() {
        nameEditText.doOnTextChanged(viewModel::setName)
    }
}
