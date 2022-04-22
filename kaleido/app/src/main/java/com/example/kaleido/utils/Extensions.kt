package com.example.kaleido.utils

import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

fun LottieAnimationView.setAndPlayLoopedAnimation(animationResId: Int) {
    this.removeAllAnimatorListeners()
    this.cancelAnimation()
    this.repeatCount = LottieDrawable.INFINITE
    this.enableMergePathsForKitKatAndAbove(true)
    this.setAnimation(animationResId)
    this.progress = 0f
    this.repeatMode = LottieDrawable.RESTART
    this.playAnimation()
}