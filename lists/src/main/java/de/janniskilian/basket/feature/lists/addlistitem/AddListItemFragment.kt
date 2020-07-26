package de.janniskilian.basket.feature.lists.addlistitem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.BaseFragment
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.util.extension.extern.getThemeColor
import de.janniskilian.basket.core.util.function.createMutableLiveData
import de.janniskilian.basket.core.util.function.getSpeechInputResult
import de.janniskilian.basket.feature.lists.R
import kotlinx.android.synthetic.main.fragment_lists.*

@AndroidEntryPoint
class AddListItemFragment : BaseFragment() {

    private val args: AddListItemFragmentArgs by navArgs()

    private val viewModel: AddListItemViewModel by viewModels()

    private val setup by lazy {
        AddListItemFragmentSetup(this, args, viewModel)
    }

    override val layoutRes get() = R.layout.fragment_add_list_item

    override val appBarColor by lazy {
        createMutableLiveData(requireContext().getThemeColor(R.attr.colorSurface))
    }

    override val showAppBar get() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

