package de.janniskilian.basket.feature.main

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import de.janniskilian.basket.R
import de.janniskilian.basket.core.ui.navigation.NavigationContainer
import de.janniskilian.basket.core.ui.navigation.SearchBarViewModel
import de.janniskilian.basket.core.util.android.view.doOnTextChanged
import de.janniskilian.basket.core.util.android.hasHardwareKeyboard
import de.janniskilian.basket.core.util.android.view.setSelectedImageState
import de.janniskilian.basket.core.util.android.view.toggleSoftKeyboard
import de.janniskilian.basket.core.util.android.createSpeechInputIntent
import de.janniskilian.basket.core.util.android.getLong
import java.lang.ref.WeakReference

class NavigationContainerImpl(private val activity: MainActivity) : NavigationContainer {

    override val fab: ExtendedFloatingActionButton
        get() = activity.binding.fab

    override fun attachSearchBar(viewModel: SearchBarViewModel) {
        val fragment = activity.currentFragment ?: return
        val viewModelWeakRef = WeakReference(viewModel)

        observeSearchBarViewModel(viewModel, fragment)
        setupSearchBarListeners(WeakReference(fragment), viewModelWeakRef)
    }

    private fun observeSearchBarViewModel(
        viewModel: SearchBarViewModel,
        lifecycleOwner: LifecycleOwner
    ) {
        viewModel.searchBarVisible.observe(lifecycleOwner, ::toggleSearchBar)
        viewModel.searchBarInput.observe(lifecycleOwner, ::searchBarInputObserver)
    }

    private fun setupSearchBarListeners(
        fragmentWeakRef: WeakReference<Fragment>,
        viewModelWeakRef: WeakReference<SearchBarViewModel>
    ) = with(activity.binding) {
        searchBarBackButton.setOnClickListener {
            viewModelWeakRef
                .get()
                ?.setSearchBarVisible(false)
        }

        searchBarSpeechInputButton.setOnClickListener {
            if (viewModelWeakRef.get()?.searchBarInput?.value.isNullOrEmpty()) {
                fragmentWeakRef
                    .get()
                    ?.registerForActivityResult(
                        object : ActivityResultContract<Unit, String?>() {
                            override fun createIntent(context: Context, input: Unit?): Intent =
                                createSpeechInputIntent()

                            override fun parseResult(
                                resultCode: Int,
                                intent: Intent?
                            ): String? =
                                intent
                                    ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                                    ?.firstOrNull()
                        }
                    ) {
                        if (it != null) {
                            viewModelWeakRef
                                .get()
                                ?.setSearchBarInput(it)
                        }
                    }
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

