package de.janniskilian.basket.feature.main

import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import de.janniskilian.basket.R
import de.janniskilian.basket.core.ANIMATION_DURATION_S
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.navigationcontainer.NavigationContainer
import de.janniskilian.basket.core.navigationcontainer.SearchBarViewModel
import de.janniskilian.basket.core.util.WeakRef
import de.janniskilian.basket.core.util.extension.extern.doOnTextChanged
import de.janniskilian.basket.core.util.extension.extern.hasHardwareKeyboard
import de.janniskilian.basket.core.util.extension.extern.setSelectedImageState
import de.janniskilian.basket.core.util.extension.extern.toggleSoftKeyboard
import de.janniskilian.basket.core.util.function.createSpeechInputIntent
import de.janniskilian.basket.core.util.weakRef
import kotlinx.android.synthetic.main.activity_main.*

class NavigationContainerImpl(private val activity: MainActivity) : NavigationContainer {

    override val fab: ExtendedFloatingActionButton
        get() = activity.fab

    override fun attachSearchBar(viewModel: SearchBarViewModel) {
        val fragment = activity.currentFragment ?: return
        val viewModelWeakRef = viewModel.weakRef()

        observeSearchBarViewModel(viewModel, fragment)
        setupSearchBarListeners(fragment.weakRef(), viewModelWeakRef)
    }

    private fun observeSearchBarViewModel(
        viewModel: SearchBarViewModel,
        lifecycleOwner: LifecycleOwner
    ) {
        viewModel.searchBarVisible.observe(lifecycleOwner, ::toggleSearchBar)
        viewModel.searchBarInput.observe(lifecycleOwner, ::searchBarInputObserver)
    }

    private fun setupSearchBarListeners(
        fragmentWeakRef: WeakRef<Fragment>,
        viewModelWeakRef: WeakRef<SearchBarViewModel>
    ) {

        activity.searchBarBackButton.setOnClickListener {
            viewModelWeakRef()?.setSearchBarVisible(false)
        }

        activity.searchBarSpeechInputButton.setOnClickListener {
            if (viewModelWeakRef()?.searchBarInput?.value.isNullOrEmpty()) {
                fragmentWeakRef()?.startActivityForResult(
                    createSpeechInputIntent(),
                    REQ_SPEECH_INPUT
                )
            } else {
                viewModelWeakRef()?.setSearchBarInput("")
            }
        }

        activity.searchBarEditText.doOnTextChanged {
            viewModelWeakRef()?.setSearchBarInput(it)
        }

        if (!activity.hasHardwareKeyboard) {
            activity.searchBarEditText.backPressedListener = {
                viewModelWeakRef()?.setSearchBarVisible(false)
            }
        }
    }

    private fun toggleSearchBar(isVisible: Boolean) {
        if (activity.searchBarContainer.isVisible == isVisible) return

        val fabEndAlpha: Float
        val searchBarEndAlpha: Float
        if (isVisible) {
            fabEndAlpha = 0f
            searchBarEndAlpha = 1f

            activity.searchBarContainer.isVisible = true
        } else {
            fabEndAlpha = 1f
            searchBarEndAlpha = 0f

            activity.fab.isVisible = true
        }

        activity.searchBarContainer.updateLayoutParams {
            height = activity.appBar.height + 2
        }

        activity.fab
            .animate()
            .alpha(fabEndAlpha)
            .setDuration(ANIMATION_DURATION_S)
            .withEndAction {
                if (isVisible) {
                    activity.fab.isVisible = false
                }
            }
            .start()

        activity.searchBarContainer
            .animate()
            .alpha(searchBarEndAlpha)
            .setDuration(ANIMATION_DURATION_S)
            .withEndAction {
                if (!isVisible) {
                    activity.searchBarContainer.isVisible = false
                }
            }
            .start()

        activity.searchBarEditText.toggleSoftKeyboard(isVisible)
    }

    private fun searchBarInputObserver(input: String) {
        if (activity.searchBarEditText.text.toString() != input) {
            activity.searchBarEditText.setText(input)
        }

        val showSpeechInput = input.isEmpty()
        activity.searchBarSpeechInputButton.setSelectedImageState(!showSpeechInput)
        activity.searchBarSpeechInputButton.contentDescription = activity.getString(
            if (showSpeechInput) {
                R.string.speech_input_button_desc
            } else {
                R.string.clear_input_button_desc
            }
        )
    }
}

