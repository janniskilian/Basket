package de.janniskilian.basket.feature.categories.category

import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import de.janniskilian.basket.core.util.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.categories.R
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryViewModelObserver(
    private val fragment: CategoryFragment,
    viewModel: CategoryViewModel
) : ViewModelObserver<CategoryViewModel>(viewModel) {

    override fun observe() {
        with(viewModel) {
            name.observe(fragment.viewLifecycleOwner, ::renderName)
            error.observe(fragment.viewLifecycleOwner, ::renderError)
            dismiss.observe(fragment.viewLifecycleOwner) {
                fragment
                    .findNavController()
                    .navigateUp()
            }
        }
    }

    private fun renderName(name: String) {
        if (name != fragment.nameEditText.text.toString()) {
            fragment.nameEditText.setText(name)
        }
    }

    private fun renderError(isError: Boolean) {
        fragment.nameLayout.error = if (isError) {
            fragment.getString(R.string.category_name_error)
        } else {
            null
        }
    }
}
