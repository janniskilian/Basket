package de.janniskilian.basket.core.util.extension.extern

import android.animation.Animator
import android.view.ViewPropertyAnimator

fun ViewPropertyAnimator.doOnEnd(block: () -> Unit): ViewPropertyAnimator {
	setListener(object : Animator.AnimatorListener {

		override fun onAnimationRepeat(animation: Animator?) {
		}

		override fun onAnimationEnd(animation: Animator?) {
			setListener(null)
			block()
		}

		override fun onAnimationCancel(animation: Animator?) {
		}

		override fun onAnimationStart(animation: Animator?) {
		}
	})

	return this
}
