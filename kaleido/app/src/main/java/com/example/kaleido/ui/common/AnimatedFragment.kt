package com.example.kaleido.ui.common

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import com.example.kaleido.R

abstract class AnimatedFragment: BaseFragment() {
    abstract override fun getViewModel(): AnimatedViewModel

    // Lifecycle callbacks for the shared element transitions
    // This callback happens at the start of the enter shared element transition
    @Volatile
    protected var enterStartWork: (() -> Unit)? = null

    // This callback happens at the end of the enter shared element transition
    @Volatile
    protected var enterEndWork: (() -> Unit)? = null

    // This callback happens at the end of the return shared element transition
    // (when the user press back and this fragment appears after a pop)
    @Volatile
    protected var returnEndWork: (() -> Unit)? = null

    protected open fun getNextImageAtStart(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getViewModel().currentMainResId != 0) return

        if (getNextImageAtStart()) {
            getViewModel().refreshMainImage()
        } else {
            getViewModel().currentMainResId = R.raw.best_heart_button
        }

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

    /**
     * Utility function to fade in views.
     */
    protected fun fadeInView(view: View, dur: Long = 1000, onEnd: (() -> Unit)? = null): Animator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).apply {
            duration = dur
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

    protected fun fadeOutView(view: View, dur: Long = 1000, onEnd: (() -> Unit)? = null): Animator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f).apply {
            duration = dur
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

    protected fun scaleInView(view: View, dur: Long = 1000, onEnd: (() -> Unit)? = null): Pair<Animator, Animator> {
        return ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f, 1f).apply {
            duration = dur
            start()
        } to ObjectAnimator.ofFloat(view, View.SCALE_X, 0f, 1f).apply {
            duration = dur
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