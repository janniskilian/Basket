package de.janniskilian.basket.feature.main

import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.R)
class WindowInsetsHandler :
    WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP),
    View.OnApplyWindowInsetsListener {

    private var view: View? = null

    private var isAnimating = false

    private val persistentInsetTypes = WindowInsets.Type.systemBars()
    private val deferredInsetTypes = WindowInsets.Type.ime()

    override fun onApplyWindowInsets(v: View, windowInsets: WindowInsets): WindowInsets {
        view = v

        if (!isAnimating) {
            updatePadding(windowInsets, persistentInsetTypes)
        }

        return WindowInsets.CONSUMED
    }

    override fun onPrepare(animation: WindowInsetsAnimation) {
        isAnimating = true
    }

    override fun onProgress(
        windowInsets: WindowInsets,
        runningAnimations: List<WindowInsetsAnimation>
    ): WindowInsets {
        updatePadding(windowInsets, persistentInsetTypes or deferredInsetTypes)
        return windowInsets
    }

    private fun updatePadding(windowInsets: WindowInsets, insetTypes: Int) {
        val insets = windowInsets.getInsets(insetTypes)
        view?.setPadding(
            insets.left,
            insets.top,
            insets.right,
            insets.bottom
        )
    }

    override fun onEnd(animation: WindowInsetsAnimation) {
        isAnimating = false
    }
}

