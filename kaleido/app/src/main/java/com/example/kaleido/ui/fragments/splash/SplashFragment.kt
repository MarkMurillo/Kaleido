package com.example.kaleido.ui.fragments.splash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.kaleido.R
import com.example.kaleido.databinding.SplashFragmentBinding
import com.example.kaleido.ui.common.AnimatedFragment
import com.example.kaleido.ui.common.AnimatedViewModel

class SplashFragment: AnimatedFragment() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun getViewModel(): AnimatedViewModel = viewModel

    override fun getNextImageAtStart(): Boolean = false

    @Volatile
    private var cancelAnimation: Boolean = false

    @Volatile
    private var curAnimation: Animator? = null

    // We need this to detect if we have actually created this fragment already.
    @Volatile
    private var firstRun = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SplashFragmentBinding.inflate(inflater, container, false)

        // If we have already created this fragment, we can reset the cancelAnimation
        // flag so that if this fragment is shown again by pressing back button
        // the animation wont stop after scaling the gradient background image.
        if (!firstRun) {
            cancelAnimation = false
        }

        binding.introText.text = getString(R.string.intro_text_1)

        curAnimation = fadeInView(binding.backgroundGradient, 2000)
        scaleInView(binding.backgroundGradient, 2000) {
            if (cancelAnimation) return@scaleInView
            curAnimation = fadeInView(binding.introText, 500) {
                if (cancelAnimation) return@fadeInView
                curAnimation = fadeOutView(binding.introText, 3000) {
                    if (cancelAnimation) return@fadeOutView
                    binding.introText.text = getString(R.string.intro_text_2)
                    curAnimation = fadeInView(binding.introText, 3000) {
                        if (cancelAnimation) return@fadeInView
                        curAnimation = fadeOutView(binding.introText, 3000) {
                            if (cancelAnimation) return@fadeOutView
                            binding.introText.text = getString(R.string.intro_text_3)
                            curAnimation = fadeInView(binding.introText, 3000) {
                                if (cancelAnimation) return@fadeInView
                                curAnimation = fadeInView(binding.playButton, 500)
                            }
                        }
                    }
                }
            }
        }

        binding.parentContainer.setOnClickListener {
            // If the user clicks anywhere on the parent container we should cancel any ongoing animations
            // and just display everything in the final form.
            cancelAnimation = true
            curAnimation?.cancel()
            binding.introText.text = getString(R.string.intro_text_3)
            binding.introText.alpha = 1f
            binding.playButton.alpha = 1f
        }

        binding.playButton.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.playButton to "refresh_button"
            )
            viewModel.navigateToNextFragment(extras)
        }

        firstRun = false
        return binding.root
    }
}