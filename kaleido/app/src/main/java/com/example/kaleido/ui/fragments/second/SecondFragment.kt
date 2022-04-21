package com.example.kaleido.ui.fragments.second

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
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
class SecondFragment : BaseFragment() {

    /**
     * use viewModels() to scope it to this fragment
     * use activityViewModels() to scope it to its activity
     */
    private val viewModel by viewModels<SecondViewModel>()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TransitionInflater.from(requireContext()).let {
            sharedElementEnterTransition = it.inflateTransition(R.transition.shared_image)
            sharedElementReturnTransition = it.inflateTransition(R.transition.shared_image)
        }
        //postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SecondFragmentBinding.inflate(inflater, container, false)

        ViewCompat.setTransitionName(binding.backgroundAnimation, "second_background_image")
        ViewCompat.setTransitionName(binding.mainImage, "second_main_image")

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
                val extras = FragmentNavigatorExtras(binding.mainImage to "background_image")
                val args = Bundle().apply {
                    this.putInt("selected_res", R.drawable.tree1)
                }
                viewModel.navigateToNextFragment(args, extras)
//            }
        }

        binding.refreshButton.setOnClickListener {
            viewModel.refreshMainImage()
        }

        viewModel.mainImageResId.observe(viewLifecycleOwner) {
            binding.mainImage.setImageDrawable(getDrawable(requireContext(), R.drawable.tree1))
//            binding.mainImage.setAnimation(it)
//            binding.mainImage.playBlockingAnimation {
//                binding.mainImage.progress = 0f
//            }
        }


        return binding.root
    }
}