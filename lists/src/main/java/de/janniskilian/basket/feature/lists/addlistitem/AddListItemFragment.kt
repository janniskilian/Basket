package de.janniskilian.basket.feature.lists.addlistitem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import de.janniskilian.basket.core.ui.fragments.BaseFragment
import de.janniskilian.basket.core.util.android.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.util.android.SpeechInputResultContract
import de.janniskilian.basket.core.util.android.getSpeechInputResult
import de.janniskilian.basket.core.util.android.getThemeColor
import de.janniskilian.basket.core.util.android.viewmodel.DefaultMutableLiveData
import de.janniskilian.basket.feature.lists.R
import de.janniskilian.basket.feature.lists.databinding.AddListItemFragmentBinding

@AndroidEntryPoint
class AddListItemFragment : BaseFragment<AddListItemFragmentBinding>() {

    private val args: AddListItemFragmentArgs by navArgs()

    private val viewModel: AddListItemViewModel by viewModels()

    private val setup by lazy {
        AddListItemFragmentSetup(this, args, viewModel)
    }

    val suggestionsAdapter
        get() = binding.recyclerView.adapter as? ShoppingListItemSuggestionsAdapter

    val speechInputLauncher = registerForActivityResult(SpeechInputResultContract()) {
        if (it != null) {
            viewModel.setInput(it)
        }
    }

    override val appBarColor by lazy {
        DefaultMutableLiveData(requireContext().getThemeColor(R.attr.colorSurface))
    }

    override val isShowAppBar get() = false

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        AddListItemFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup.run()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_SPEECH_INPUT
            && resultCode == Activity.RESULT_OK
        ) {
            getSpeechInputResult(data)?.let(viewModel::setInput)
        }
    }
}

