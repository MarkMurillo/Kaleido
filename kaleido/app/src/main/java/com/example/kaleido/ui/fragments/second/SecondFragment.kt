package com.example.kaleido.ui.fragments.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.airbnb.lottie.LottieAnimationView
import com.example.kaleido.databinding.SecondFragmentBinding
import com.example.kaleido.ui.common.AnimatedFragment
import com.example.kaleido.ui.common.AnimatedViewModel
import com.example.kaleido.utils.setAndPlayLoopedAnimation

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

        hideOrShowUI(binding.refreshButton)

        binding.mainImage.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.mainImage to "third_background_anim",
                binding.refreshButton to "third_refresh_button"
            )
            viewModel.navigateToNextFragment(extras)
        }

        binding.mainImage.setOnLongClickListener {
            hideUI?.let {
                hideUI = !it

                if (hideUI!!) {
                    fadeOutView(binding.refreshButton) {
                        hideOrShowUI(binding.refreshButton)
                    }
                } else {
                    binding.refreshButton.alpha = 0f
                    hideOrShowUI(binding.refreshButton)
                    fadeInView(binding.refreshButton)
                }
            }
            true
        }

        binding.refreshButton.setOnClickListener {
            viewModel.refreshMainImage()
        }

        viewModel.mainImageResId.observe(viewLifecycleOwner) {
            binding.mainImage.setAndPlayLoopedAnimation(it)
        }

        returnEndWork = {
            fadeInView(binding.mainImage)
            hideUI?.let {
                if (!it) fadeInView(binding.refreshButton)
            }
        }

        return binding.root
    }
}