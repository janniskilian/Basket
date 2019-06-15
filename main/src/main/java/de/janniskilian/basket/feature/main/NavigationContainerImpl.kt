package de.janniskilian.basket.feature.main

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import de.janniskilian.basket.R
import de.janniskilian.basket.core.ANIMATION_DURATION_S
import de.janniskilian.basket.core.REQ_SPEECH_INPUT
import de.janniskilian.basket.core.navigationcontainer.NavigationContainer
import de.janniskilian.basket.core.navigationcontainer.SearchBarViewModel
import de.janniskilian.basket.core.util.WeakRef
import de.janniskilian.basket.core.util.extension.extern.*
import de.janniskilian.basket.core.util.function.createSpeechInputIntent
import de.janniskilian.basket.core.util.function.getLong
import de.janniskilian.basket.core.util.weakRef
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class NavigationContainerImpl(private val activity: MainActivity) : NavigationContainer {

    private var snackbar: WeakRef<Snackbar>? = null

    override fun setAppBarColor(color: Int, animate: Boolean) {
        if (animate) {
            val initialColor = activity.appBar.backgroundTint?.defaultColor
                ?: ContextCompat.getColor(activity, R.color.primary)

            if (color == initialColor) return

            val evaluator = ArgbEvaluator()

            with(ValueAnimator.ofFloat(0f, 1f)) {
                duration = getLong(activity, R.integer.transition_duration)
                interpolator = LinearInterpolator()
                addUpdateListener {
                    activity.appBar.backgroundTint = ColorStateList.valueOf(
                        evaluator.evaluate(animatedFraction, initialColor, color) as Int
                    )
                }
                start()
            }
        } else {
            activity.appBar.backgroundTint = ColorStateList.valueOf(color)
        }
    }

    override fun showSnackbar(resId: Int, duration: Int, configure: Snackbar.() -> Unit) {
        snackbar = Snackbar
            .make(activity.contentView, resId, duration)
            .apply {
                configure()

                view.post {
                    view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        val appBarHeight = activity.appBar.height + activity.fab.height / 2f
                        val margin = activity.resources.getDimension(R.dimen.half)
                        updateMargins(bottom = (appBarHeight + margin).roundToInt())
                    }
                }

                show()
            }
            .weakRef()
    }

    override fun dismissSnackbar() {
        snackbar?.invoke()?.dismiss()
    }

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

        activity.searchBarEditText.onTextChanged {
            viewModelWeakRef()?.setSearchBarInput(it)
        }

        if (!activity.hasHardwareKeyboard) {
            activity.searchBarEditText.backPressedListener = {
                viewModelWeakRef()?.setSearchBarVisible(false)
            }
        }
    }

    private fun toggleSearchBar(visible: Boolean) {
        if (activity.searchBarContainer.isVisible == visible) return

        val fabEndAlpha: Float
        val searchBarEndAlpha: Float
        if (visible) {
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
            .doOnEnd {
                if (visible) {
                    activity.fab.isVisible = false
                }
            }
            .start()

        activity.searchBarContainer
            .animate()
            .alpha(searchBarEndAlpha)
            .setDuration(ANIMATION_DURATION_S)
            .doOnEnd {
                if (!visible) {
                    activity.searchBarContainer.isVisible = false
                }
            }
            .start()

        if (visible) {
            activity.searchBarEditText.requestFocus()
            activity.showKeyboard(activity.searchBarEditText)
        } else {
            activity.searchBarEditText.clearFocus()
            activity.hideKeyboard()
        }
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

