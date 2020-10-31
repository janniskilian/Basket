package de.janniskilian.basket.feature.main

import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import de.janniskilian.basket.R
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.navigationcontainer.NavigationContainer
import de.janniskilian.basket.core.navigationcontainer.SearchBarViewModel
import de.janniskilian.basket.core.util.WeakRef
import de.janniskilian.basket.core.util.extension.extern.doOnTextChanged
import de.janniskilian.basket.core.util.extension.extern.hasHardwareKeyboard
import de.janniskilian.basket.core.util.extension.extern.setSelectedImageState
import de.janniskilian.basket.core.util.extension.extern.toggleSoftKeyboard
import de.janniskilian.basket.core.util.function.createSpeechInputIntent
import de.janniskilian.basket.core.util.function.getLong
import de.janniskilian.basket.core.util.weakRef

class NavigationContainerImpl(private val activity: MainActivity) : NavigationContainer {

    override val fab: ExtendedFloatingActionButton
        get() = activity.binding.fab

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
    ) = with(activity.binding) {
        searchBarBackButton.setOnClickListener {
            viewModelWeakRef()?.setSearchBarVisible(false)
        }

        searchBarSpeechInputButton.setOnClickListener {
            if (viewModelWeakRef()?.searchBarInput?.value.isNullOrEmpty()) {
                fragmentWeakRef()?.startActivityForResult(
                    createSpeechInputIntent(),
                    REQ_SPEECH_INPUT
                )
            } else {
                viewModelWeakRef()?.setSearchBarInput("")
            }
        }

        searchBarEditText.doOnTextChanged {
            viewModelWeakRef()?.setSearchBarInput(it)
        }

        if (!activity.hasHardwareKeyboard) {
            searchBarEditText.backPressedListener = {
                viewModelWeakRef()?.setSearchBarVisible(false)
            }
        }
    }

    private fun toggleSearchBar(isVisible: Boolean) = with(activity.binding) {
        if (searchBarContainer.isVisible == isVisible) return@with

        val fabEndAlpha: Float
        val searchBarEndAlpha: Float
        if (isVisible) {
            fabEndAlpha = 0f
            searchBarEndAlpha = 1f

            searchBarContainer.isVisible = true
        } else {
            fabEndAlpha = 1f
            searchBarEndAlpha = 0f

            fab.isVisible = true
        }

        searchBarContainer.updateLayoutParams {
            height = appBar.height + 2
        }

        val duration = getLong(activity, R.integer.animation_duration_s)

        fab
            .animate()
            .alpha(fabEndAlpha)
            .setDuration(duration)
            .withEndAction {
                if (isVisible) {
                    fab.isVisible = false
                }
            }
            .start()

        searchBarContainer
            .animate()
            .alpha(searchBarEndAlpha)
            .setDuration(duration)
            .withEndAction {
                if (!isVisible) {
                    searchBarContainer.isVisible = false
                }
            }
            .start()

        searchBarEditText.toggleSoftKeyboard(isVisible)
    }

    private fun searchBarInputObserver(input: String) = with(activity.binding) {
        if (searchBarEditText.text.toString() != input) {
            searchBarEditText.setText(input)
        }

        val showSpeechInput = input.isEmpty()
        searchBarSpeechInputButton.setSelectedImageState(!showSpeechInput)
        searchBarSpeechInputButton.contentDescription = activity.getString(
            if (showSpeechInput) {
                R.string.speech_input_button_desc
            } else {
                R.string.clear_input_button_desc
            }
        )
    }
}

