package de.janniskilian.basket.feature.lists.addlistitem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.type.domain.ShoppingListId
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.extension.extern.getThemeColor
import de.janniskilian.basket.core.util.function.createMutableLiveData
import de.janniskilian.basket.core.util.function.getSpeechInputResult
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_lists.*

class AddListItemFragment : BaseFragment() {

    private val shoppingListId by lazy {
        ShoppingListId(AddListItemFragmentArgs.fromBundle(requireArguments()).shoppingListId)
    }

    private val module by lazy {
        AddListItemModule(appModule, this, shoppingListId)
    }

    private val setup get() = module.addListItemSetup

    private val viewModel get() = module.addListItemViewModel

    override val layoutRes get() = R.layout.fragment_add_list_item

    override val appBarColor by lazy {
        createMutableLiveData(requireContext().getThemeColor(R.attr.colorSurface))
    }

    override val showAppBar get() = false

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
            getSpeechInputResult(data)?.let(viewModel::setInput)
        }
    }
}

