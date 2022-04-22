package com.example.kaleido.ui.fragments.main

import android.os.Bundle
import androidx.navigation.fragment.FragmentNavigator
import com.example.kaleido.R
import com.example.kaleido.ui.common.AnimatedViewModel

class MainViewModel: AnimatedViewModel() {
    override fun navigateToNextFragment(extras: FragmentNavigator.Extras) {
        val args = Bundle().apply {
            this.putInt("selected_res", currentMainResId)
        }
        navigate(R.id.action_second_fragment, args, extras)
    }
}