package de.janniskilian.basket.feature.categories.categories

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.extension.extern.hasHardwareKeyboard
import de.janniskilian.basket.core.util.function.getSpeechInputResult
import de.janniskilian.basket.feature.categories.R
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : BaseFragment() {

    private val module by lazy {
        CategoriesModule(appModule, this)
    }

    private val setup get() = module.categoriesSetup

    private val viewModel get() = module.categoriesViewModel

    override val layoutRes get() = R.layout.fragment_categories

    override val menuRes get() = R.menu.search

    override val fabTextRes get() = R.string.fab_create_category

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup.run()
    }

    override fun onDestroyView() {
        recyclerView.adapter = null
        super.onDestroyView()
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
}

