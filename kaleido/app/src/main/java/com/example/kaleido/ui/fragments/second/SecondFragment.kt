package com.example.kaleido.ui.fragments.second

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.airbnb.lottie.LottieAnimationView
import com.example.kaleido.R
import com.example.kaleido.databinding.SecondFragmentBinding
import com.example.kaleido.ui.common.BaseFragment
import com.example.kaleido.ui.common.BaseViewModel
import com.example.kaleido.utils.playLoopedAnimation
import com.example.kaleido.utils.setAndPlayLoopedAnimation
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragments and activities MUST have the @AndroidEntryPoint annotation.
 */
@AndroidEntryPoint
class SecondFragment : BaseFragment() {

    /**
     * use viewModels() to scope it to this fragment
     * use activityViewModels() to scope it to its activity
     */
    private val viewModel by viewModels<SecondViewModel>()

    @Volatile
    private var enterStartWork: (() -> Unit)? = null

    @Volatile
    private var enterEndWork: (() -> Unit)? = null

    @Volatile
    private var returnEndWork: (() -> Unit)? = null

    override fun getViewModel(): BaseViewModel = viewModel

    private var currentMainResId = 0

    @Volatile
    private var mainImage: LottieAnimationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (currentMainResId != 0) return

        viewModel.refreshMainImage(R.raw.kaleidoscope)

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SecondFragmentBinding.inflate(inflater, container, false)

        mainImage = binding.mainImage

        ViewCompat.setTransitionName(binding.backgroundAnimation, "second_background_image")
        ViewCompat.setTransitionName(binding.mainImage, "second_main_image")

        arguments?.let {
            val selectedRes = it.getInt("selected_res", 0)
            if (selectedRes != 0) {
                binding.backgroundAnimation.setAndPlayLoopedAnimation(selectedRes)
            }
        }

        binding.mainImage.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.mainImage to "background_image")
            val args = Bundle().apply {
                this.putInt("selected_res", currentMainResId)
            }
            viewModel.navigateToNextFragment(args, extras)
        }

        binding.refreshButton.setOnClickListener {
            viewModel.refreshMainImage(currentMainResId)
        }

        binding.backgroundAnimation.setOnClickListener {
            binding.backgroundAnimation.playLoopedAnimation()
        }

        viewModel.mainImageResId.observe(viewLifecycleOwner) {
            currentMainResId = it
            binding.mainImage.setAndPlayLoopedAnimation(it)
        }

        enterStartWork = {
            // Hide main and refresh views initially at the start of the transition
            mainImage?.alpha = 0f
            binding.refreshButton.alpha = 0f
        }

        enterEndWork = {
            fadeInViews(binding)
            Log.d("Animation", "Is Animating: ${binding.backgroundAnimation.isAnimating}")
            arguments?.let {
                val selectedRes = it.getInt("selected_res", 0)
                if (selectedRes != 0) {
                    binding.backgroundAnimation.setAndPlayLoopedAnimation(selectedRes)
                }
            }
        }

        returnEndWork = {
            fadeInViews(binding)
        }

        return binding.root
    }

    private fun fadeInViews(binding: SecondFragmentBinding) {
        ObjectAnimator.ofFloat(mainImage, View.ALPHA, 0f, 1f).apply {
            duration = 1000
            start()
        }

        ObjectAnimator.ofFloat(binding.refreshButton, View.ALPHA, 0f, 1f).apply {
            duration = 1000
            start()
        }
    }
}