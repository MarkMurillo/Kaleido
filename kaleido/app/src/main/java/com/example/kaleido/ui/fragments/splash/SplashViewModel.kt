package com.example.kaleido.ui.fragments.splash

import androidx.navigation.fragment.FragmentNavigator
import com.example.kaleido.R
import com.example.kaleido.ui.common.AnimatedViewModel

class SplashViewModel: AnimatedViewModel() {
    override fun navigateToNextFragment(extras: FragmentNavigator.Extras) {
        navigate(R.id.action_first_fragment, null, extras)
    }
}