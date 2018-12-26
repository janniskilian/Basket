package de.janniskilian.basket.feature.categories.category

import androidx.lifecycle.observe
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.categories.R
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryViewModelObserver(
    viewModel: CategoryViewModel,
    private val fragment: CategoryFragment
) : ViewModelObserver<CategoryViewModel>(viewModel) {

    override fun observe() {
        with(viewModel) {
            name.observe(fragment, ::renderName)
            error.observe(fragment, ::renderError)
            dismiss.observe(fragment) { fragment.dismiss() }
        }
    }

    private fun renderName(name: String) {
        if (name != fragment.nameEditText.text.toString()) {
            fragment.nameEditText.setText(name)
        }
    }

    private fun renderError(error: Boolean) {
        fragment.nameLayout.error = if (error) {
            fragment.getString(R.string.category_name_error)
        } else {
            null
        }
    }
}
