package com.example.kaleido.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.airbnb.lottie.LottieAnimationView
import com.example.kaleido.databinding.MainFragmentBinding
import com.example.kaleido.ui.common.AnimatedFragment
import com.example.kaleido.ui.common.AnimatedViewModel
import com.example.kaleido.utils.setAndPlayLoopedAnimation

class MainFragment : AnimatedFragment() {

    /**
     * use viewModels() to scope it to this fragment
     * use activityViewModels() to scope it to its activity
     */
    private val viewModel by viewModels<MainViewModel>()

    override fun getViewModel(): AnimatedViewModel = viewModel

    @Volatile
    private var mainImage: LottieAnimationView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(inflater, container, false)

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
                binding.mainImage to "second_background_anim", // Move our main animation to our background animation
                binding.refreshButton to "second_refresh_button" // Translate our refresh button to our next view's position
            )
            viewModel.navigateToNextFragment(extras)
        }

        binding.mainImage.setOnLongClickListener {
            hideUI = !hideUI
            hideOrShowUI(binding.refreshButton)
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
            if (!hideUI) fadeInView(binding.refreshButton)
        }

        return binding.root
    }
}