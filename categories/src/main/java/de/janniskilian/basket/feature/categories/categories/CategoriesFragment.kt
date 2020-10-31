package de.janniskilian.basket.feature.categories.categories

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.type.domain.Category
import de.janniskilian.basket.core.util.extension.extern.createContainerTransformNavigatorExtras
import de.janniskilian.basket.core.util.extension.extern.hasHardwareKeyboard
import de.janniskilian.basket.core.util.function.getSpeechInputResult
import de.janniskilian.basket.feature.categories.R
import de.janniskilian.basket.feature.categories.databinding.CategoriesFragmentBinding

@AndroidEntryPoint
class CategoriesFragment : BaseFragment<CategoriesFragmentBinding>() {

    private val viewModel: CategoriesViewModel by viewModels()

    private val setup by lazy {
        CategoriesFragmentSetup(this, viewModel)
    }

    override val menuRes get() = R.menu.search

    override val titleTextRes get() = R.string.categories_title

    override val fabTextRes get() = R.string.fab_create_category

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        CategoriesFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup.run()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_SPEECH_INPUT
            && resultCode == Activity.RESULT_OK
        ) {
            getSpeechInputResult(data)?.let {
                viewModel.setSearchBarInput(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == R.id.action_search) {
            viewModel.setSearchBarVisible(true)
            true
        } else {
            false
        }

    override fun onNavigateUpAction(): Boolean =
        if (requireActivity().hasHardwareKeyboard
            && viewModel.searchBarVisible.value == true
        ) {
            viewModel.setSearchBarVisible(false)
            true
        } else {
            false
        }

    override fun onFabClicked() {
        navigate(CategoriesFragmentDirections.actionCategoriesFragmentToCategoryFragment(-1L))
    }

    fun navigateToCategory(position: Int, category: Category) {
        binding
            .recyclerView
            .findViewHolderForAdapterPosition(position)
            ?.itemView
            ?.let {
                navigate(
                    CategoriesFragmentDirections
                        .actionCategoriesFragmentToCategoryFragment(category.id.value),
                    createContainerTransformNavigatorExtras(it)
                )
            }
    }
}

