package com.example.kaleido.ui.fragments.second

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.fadeIn
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.airbnb.lottie.LottieAnimationView
import com.example.kaleido.R
import com.example.kaleido.databinding.SecondFragmentBinding
import com.example.kaleido.ui.common.AnimatedFragment
import com.example.kaleido.ui.common.AnimatedViewModel
import com.example.kaleido.ui.common.BaseViewModel
import com.example.kaleido.utils.playLoopedAnimation
import com.example.kaleido.utils.setAndPlayLoopedAnimation
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragments and activities MUST have the @AndroidEntryPoint annotation.
 */
@AndroidEntryPoint
class SecondFragment : AnimatedFragment() {

    /**
     * use viewModels() to scope it to this fragment
     * use activityViewModels() to scope it to its activity
     */
    private val viewModel by viewModels<SecondViewModel>()

    override fun getViewModel(): AnimatedViewModel = viewModel

    @Volatile
    private var mainImage: LottieAnimationView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SecondFragmentBinding.inflate(inflater, container, false)

        mainImage = binding.mainImage

        arguments?.let {
            val selectedRes = it.getInt("selected_res", 0)
            if (selectedRes != 0) {
                binding.backgroundAnimation.setAndPlayLoopedAnimation(selectedRes)
            }
        }

        binding.mainImage.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.mainImage to "background_image")
            viewModel.navigateToNextFragment(extras)
        }

        binding.refreshButton.setOnClickListener {
            viewModel.refreshMainImage()
        }

        viewModel.mainImageResId.observe(viewLifecycleOwner) {
            binding.mainImage.setAndPlayLoopedAnimation(it)
        }

        enterStartWork = {
            // Hide main and refresh views initially at the start of the transition
            mainImage?.alpha = 0f
            binding.refreshButton.alpha = 0f
        }

        enterEndWork = {
            fadeInView(binding.mainImage)
            fadeInView(binding.refreshButton)
            Log.d("Animation", "Is Animating: ${binding.backgroundAnimation.isAnimating}")
            arguments?.let {
                val selectedRes = it.getInt("selected_res", 0)
                if (selectedRes != 0) {
                    binding.backgroundAnimation.setAndPlayLoopedAnimation(selectedRes)
                }
            }
        }

        returnEndWork = {
            fadeInView(binding.mainImage)
            fadeInView(binding.refreshButton)
        }

        return binding.root
    }
}