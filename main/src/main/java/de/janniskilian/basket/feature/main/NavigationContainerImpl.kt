package de.janniskilian.basket.feature.main

import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import de.janniskilian.basket.R
import de.janniskilian.basket.core.ui.navigation.NavigationContainer
import de.janniskilian.basket.core.ui.navigation.SearchBarViewModel
import de.janniskilian.basket.core.util.android.SpeechInputResultContract
import de.janniskilian.basket.core.util.android.getLong
import de.janniskilian.basket.core.util.android.hasHardwareKeyboard
import de.janniskilian.basket.core.util.android.view.doOnTextChanged
import de.janniskilian.basket.core.util.android.view.setSelectedImageState
import de.janniskilian.basket.core.util.android.view.toggleSoftKeyboard
import java.lang.ref.WeakReference

class NavigationContainerImpl(private val activity: MainActivity) : NavigationContainer {

    private var searchBarViewModel: WeakReference<SearchBarViewModel>? = null

    private val speechInputLauncher =
        activity.registerForActivityResult(SpeechInputResultContract()) {
            if (it != null) {
                searchBarViewModel
                    ?.get()
                    ?.setSearchBarInput(it)
            }
        }

    override val fab: ExtendedFloatingActionButton
        get() = activity.binding.fab

    override fun attachSearchBar(viewModel: SearchBarViewModel) {
        val fragment = activity.currentFragment ?: return
        val viewModelWeakRef = WeakReference(viewModel)

        observeSearchBarViewModel(viewModel, fragment)
        setupSearchBarListeners(viewModelWeakRef)
    }

    private fun observeSearchBarViewModel(
        viewModel: SearchBarViewModel,
        lifecycleOwner: LifecycleOwner
    ) {
        viewModel.searchBarVisible.observe(lifecycleOwner, ::toggleSearchBar)
        viewModel.searchBarInput.observe(lifecycleOwner, ::searchBarInputObserver)
    }

    private fun setupSearchBarListeners(viewModelWeakRef: WeakReference<SearchBarViewModel>) =
        with(activity.binding) {
            searchBarViewModel = viewModelWeakRef

            searchBarBackButton.setOnClickListener {
                viewModelWeakRef
                    .get()
                    ?.setSearchBarVisible(false)
            }

            searchBarSpeechInputButton.setOnClickListener {
                if (viewModelWeakRef.get()?.searchBarInput?.value.isNullOrEmpty()) {
                    speechInputLauncher.launch(Unit)
                } else {
                    viewModelWeakRef
                        .get()
                        ?.setSearchBarInput("")
                }
            }

            searchBarEditText.doOnTextChanged {
                viewModelWeakRef
                    .get()
                    ?.setSearchBarInput(it)
            }

            if (!activity.hasHardwareKeyboard) {
                searchBarEditText.backPressedListener = {
                    viewModelWeakRef
                        .get()
                        ?.setSearchBarVisible(false)
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

