package de.janniskilian.basket.feature.categories.category

import androidx.navigation.fragment.findNavController
import de.janniskilian.basket.core.util.android.viewmodel.ViewModelObserver
import de.janniskilian.basket.feature.categories.R

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

    private fun renderName(name: String) = with(fragment.binding) {
        if (name != nameEditText.text.toString()) {
            nameEditText.setText(name)
        }
    }

    private fun renderError(isError: Boolean) = with(fragment.binding) {
        nameLayout.error = if (isError) {
            fragment.getString(R.string.category_name_error)
        } else {
            null
        }
    }
}
