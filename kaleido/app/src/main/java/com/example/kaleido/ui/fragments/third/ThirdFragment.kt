package com.example.kaleido.ui.fragments.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.airbnb.lottie.LottieAnimationView
import com.example.kaleido.databinding.SecondFragmentBinding
import com.example.kaleido.databinding.ThirdFragmentBinding
import com.example.kaleido.ui.common.AnimatedFragment
import com.example.kaleido.ui.common.AnimatedViewModel
import com.example.kaleido.utils.setAndPlayLoopedAnimation
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragments and activities MUST have the @AndroidEntryPoint annotation.
 */
@AndroidEntryPoint
class ThirdFragment : AnimatedFragment() {

    /**
     * use viewModels() to scope it to this fragment
     * use activityViewModels() to scope it to its activity
     */
    private val viewModel by viewModels<ThirdViewModel>()

    override fun getViewModel(): AnimatedViewModel = viewModel

    @Volatile
    private var mainImage: LottieAnimationView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ThirdFragmentBinding.inflate(inflater, container, false)

        mainImage = binding.mainImage

        arguments?.let {
            val selectedRes = it.getInt("selected_res", 0)
            if (selectedRes != 0) {
                binding.backgroundAnimation.setAndPlayLoopedAnimation(selectedRes)
            }
        }

        binding.mainImage.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.mainImage to "background_anim",
                binding.refreshButton to "refresh_button"
            )
            viewModel.navigateToNextFragment(extras)
        }

        binding.refreshButton.setOnClickListener {
            viewModel.refreshMainImage()
        }

        viewModel.mainImageResId.observe(viewLifecycleOwner) {
            binding.mainImage.setAndPlayLoopedAnimation(it)
        }

        returnEndWork = {
            fadeInView(binding.mainImage)
            fadeInView(binding.refreshButton)
        }

        return binding.root
    }
}