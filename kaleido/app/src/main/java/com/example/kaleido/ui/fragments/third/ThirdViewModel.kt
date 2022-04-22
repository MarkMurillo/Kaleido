package com.example.kaleido.ui.fragments.third

import android.os.Bundle
import androidx.navigation.fragment.FragmentNavigator
import com.example.kaleido.R
import com.example.kaleido.ui.common.AnimatedViewModel

class ThirdViewModel: AnimatedViewModel() {
    override fun navigateToNextFragment(extras: FragmentNavigator.Extras) {
        val args = Bundle().apply {
            this.putInt("selected_res", currentMainResId)
        }
        navigate(R.id.action_first_fragment, args, extras)
    }
}