package com.example.kaleido.ui.common

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import com.example.kaleido.R

abstract class AnimatedFragment: BaseFragment() {
    abstract override fun getViewModel(): AnimatedViewModel

    @Volatile
    protected var enterStartWork: (() -> Unit)? = null

    @Volatile
    protected var enterEndWork: (() -> Unit)? = null

    @Volatile
    protected var returnEndWork: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getViewModel().currentMainResId != 0) return

        getViewModel().refreshMainImage()

        TransitionInflater.from(requireContext()).let {
            val enterSharedTransition = it.inflateTransition(R.transition.shared_image)
            val exitSharedTransition = it.inflateTransition(R.transition.shared_image)
            val fadeExitTransition = it.inflateTransition(R.transition.fade)
            sharedElementEnterTransition = enterSharedTransition
            sharedElementReturnTransition = exitSharedTransition
            exitTransition = fadeExitTransition

            enterSharedTransition.addListener(object: android.transition.Transition.TransitionListener {
                override fun onTransitionStart(p0: android.transition.Transition?) {
                    enterStartWork?.invoke()
                }

                override fun onTransitionCancel(p0: android.transition.Transition?) {}

                override fun onTransitionPause(p0: android.transition.Transition?) {}

                override fun onTransitionResume(p0: android.transition.Transition?) {}

                override fun onTransitionEnd(p0: android.transition.Transition?) {
                    enterSharedTransition.removeListener(this)
                    enterEndWork?.invoke()
                }
            })

            exitSharedTransition.addListener(object: android.transition.Transition.TransitionListener {
                override fun onTransitionStart(p0: android.transition.Transition?) {}

                override fun onTransitionCancel(p0: android.transition.Transition?) {}

                override fun onTransitionPause(p0: android.transition.Transition?) {}

                override fun onTransitionResume(p0: android.transition.Transition?) {}

                override fun onTransitionEnd(p0: android.transition.Transition?) {
                    exitSharedTransition.removeListener(this)
                    returnEndWork?.invoke()
                }
            })
        }
    }

    protected fun fadeInView(view: View, onEnd: (() -> Unit)? = null) {
        ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).apply {
            duration = 1000
            addListener(object: Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {}

                override fun onAnimationEnd(p0: Animator?) {
                    removeListener(this)
                    onEnd?.invoke()
                }

                override fun onAnimationCancel(p0: Animator?) {
                    removeListener(this)
                    onEnd?.invoke()
                }

                override fun onAnimationRepeat(p0: Animator?) {}
            })
            start()
        }
    }
}