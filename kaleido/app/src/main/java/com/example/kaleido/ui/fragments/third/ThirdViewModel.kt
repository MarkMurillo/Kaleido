package com.example.kaleido.ui.fragments.third

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import com.example.kaleido.R
import com.example.kaleido.ui.common.AnimatedViewModel
import com.example.kaleido.ui.common.BaseViewModel
import com.example.kaleido.utils.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

class ThirdViewModel: AnimatedViewModel() {
    override fun navigateToNextFragment(extras: FragmentNavigator.Extras) {
        val args = Bundle().apply {
            this.putInt("selected_res", currentMainResId)
        }
        navigate(R.id.action_first_fragment, args, extras)
    }
}