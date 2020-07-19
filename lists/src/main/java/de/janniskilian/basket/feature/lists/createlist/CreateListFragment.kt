package de.janniskilian.basket.feature.lists.createlist

import android.os.Bundle
import android.view.View
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.extension.extern.minusOneAsNull
import de.janniskilian.basket.feature.lists.R

class CreateListFragment : BaseFragment() {

    private val args by lazy { CreateListFragmentArgs.fromBundle(requireArguments()) }

    private val module by lazy {
        CreateListModule(appModule, this, args)
    }

    private val setup get() = module.createListFragmentSetup

    override val layoutRes get() = R.layout.fragment_create_list

    override val titleTextRes
        get() = if (args.shoppingListId.minusOneAsNull() == null) {
            R.string.create_list_title
        } else {
            R.string.edit_list_title
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup.run()
    }
}
