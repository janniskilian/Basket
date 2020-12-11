package de.janniskilian.basket.core.util.android

import androidx.transition.Transition

inline fun Transition.addListener(
    crossinline onStart: (() -> Unit) = {},
    crossinline onEnd: (() -> Unit) = {},
    crossinline onCancel: (() -> Unit) = {},
    crossinline onPause: (() -> Unit) = {},
    crossinline onResume: (() -> Unit) = {}
) {
    addListener(
        object : Transition.TransitionListener {

            override fun onTransitionStart(transition: Transition) {
                onStart()
            }

            override fun onTransitionEnd(transition: Transition) {
                onEnd()
            }

            override fun onTransitionCancel(transition: Transition) {
                onCancel()
            }

            override fun onTransitionPause(transition: Transition) {
                onPause()
            }

            override fun onTransitionResume(transition: Transition) {
                onResume()
            }
        }

    )
}
