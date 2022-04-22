package com.example.kaleido.ui.fragments.second

import android.os.Bundle
import androidx.navigation.fragment.FragmentNavigator
import com.example.kaleido.R
import com.example.kaleido.ui.common.AnimatedViewModel

class SecondViewModel: AnimatedViewModel() {
    override fun navigateToNextFragment(extras: FragmentNavigator.Extras) {
        val args = Bundle().apply {
            this.putInt("selected_res", currentMainResId)
        }
        navigate(R.id.action_third_fragment, args, extras)
    }
}