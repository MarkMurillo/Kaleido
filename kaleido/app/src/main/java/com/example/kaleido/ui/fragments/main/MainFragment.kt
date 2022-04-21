package com.example.kaleido.ui.fragments.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.SharedElementCallback
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.Transition
import androidx.transition.TransitionSet
import com.example.kaleido.R
import com.example.kaleido.databinding.MainFragmentBinding
import com.example.kaleido.databinding.SecondFragmentBinding
import com.example.kaleido.ui.common.BaseFragment
import com.example.kaleido.ui.common.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragments and activities MUST have the @AndroidEntryPoint annotation.
 */
@AndroidEntryPoint
class MainFragment : BaseFragment() {

    /**
     * use viewModels() to scope it to this fragment
     * use activityViewModels() to scope it to its activity
     */
    private val viewModel by viewModels<MainViewModel>()

    @Volatile
    private var transitionEndWork: (() -> Unit)? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TransitionInflater.from(requireContext()).let {
            val enterSharedTransition = it.inflateTransition(R.transition.shared_image)
            val exitSharedTransition = it.inflateTransition(R.transition.shared_image)
            val fadeExitTransition = it.inflateTransition(R.transition.fade)
            sharedElementEnterTransition = enterSharedTransition
            sharedElementReturnTransition = exitSharedTransition
            exitTransition = fadeExitTransition

            enterSharedTransition.addListener(object: android.transition.Transition.TransitionListener {
                override fun onTransitionStart(p0: android.transition.Transition?) {}

                override fun onTransitionCancel(p0: android.transition.Transition?) {}

                override fun onTransitionPause(p0: android.transition.Transition?) {}

                override fun onTransitionResume(p0: android.transition.Transition?) {}

                override fun onTransitionEnd(p0: android.transition.Transition?) {
                    //startPostponedEnterTransition()
                    transitionEndWork?.invoke()
                }
            })
        }

        //postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(inflater, container, false)

        ViewCompat.setTransitionName(binding.mainImage, "main_image")
        ViewCompat.setTransitionName(binding.backgroundAnimation, "background_image")

//        binding.backgroundAnimation.viewTreeObserver.addOnPreDrawListener( object: ViewTreeObserver.OnPreDrawListener {
//            override fun onPreDraw(): Boolean {
//                binding.backgroundAnimation.viewTreeObserver.removeOnPreDrawListener(this)
//                this@MainFragment.startPostponedEnterTransition()
//                return true
//            }
//        })

        arguments?.let {
            val selectedRes = it.getInt("selected_res", 0)
            if (selectedRes != 0) {
//                binding.backgroundAnimation.setAnimation(selectedRes)
                binding.backgroundAnimation.setImageDrawable(getDrawable(requireContext(), selectedRes))
            }
        }

        viewModel.refreshMainImage()

        binding.mainImage.setOnClickListener {
//            binding.mainImage.playBlockingAnimation {
                val extras = FragmentNavigatorExtras(binding.mainImage to "second_background_image")
                val args = Bundle().apply {
                    this.putInt("selected_res", R.drawable.tree2)
                }
                viewModel.navigateToNextFragment(args, extras)
//            }
        }

        binding.refreshButton.setOnClickListener {
            viewModel.refreshMainImage()
        }

        viewModel.mainImageResId.observe(viewLifecycleOwner) {
            binding.mainImage.setImageDrawable(getDrawable(requireContext(), R.drawable.tree2))
//            binding.mainImage.setAnimation(it)
//            binding.mainImage.playBlockingAnimation {
//                binding.mainImage.progress = 0f
//            }
        }

        transitionEndWork = {
            fadeInViews(binding)
        }

        if(isFirstFragment()) {
            fadeInViews(binding)
        }

        return binding.root
    }

    private fun fadeInViews(binding: MainFragmentBinding) {
        ObjectAnimator.ofFloat(binding.mainImage, View.ALPHA, 0f, 1f).apply {
            duration = 1000
            start()
        }

        ObjectAnimator.ofFloat(binding.refreshButton, View.ALPHA, 0f, 1f).apply {
            duration = 1000
            start()
        }
    }
}