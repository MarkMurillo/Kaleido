package com.example.kaleido.utils

import android.animation.Animator
import com.airbnb.lottie.LottieAnimationView

fun LottieAnimationView.playBlockingAnimation(onAnimationEnd: (() -> Unit)? = null) {
    this.progress = 0f
    this.removeAllAnimatorListeners()
    this.addAnimatorListener(object: Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {
            // Do nothing
        }

        override fun onAnimationEnd(p0: Animator?) {
            this@playBlockingAnimation.removeAnimatorListener(this)
            // Start the view transition
            onAnimationEnd?.invoke()
        }

        override fun onAnimationCancel(p0: Animator?) {
            // Do nothing
        }

        override fun onAnimationRepeat(p0: Animator?) {
            // Do nothing
        }
    })
    this.playAnimation()
}