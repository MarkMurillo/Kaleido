package com.example.kaleido.ui.fragments.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import com.example.kaleido.R
import com.example.kaleido.ui.common.BaseViewModel
import com.example.kaleido.utils.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

/**
 * View models must have the @HiltViewModel annotation
 * so we can inject dependencies.
 * Dependencies are injected through the constructor only
 * and require the @Inject annotation on the constructor.
 */
class MainViewModel: BaseViewModel() {

    private val _mainImageResId = MutableLiveData<Int>()
    val mainImageResId: LiveData<Int>
        get() = _mainImageResId

    fun refreshMainImage(currentMainResId: Int) {
        val newRes = AppUtils.getRandomLottieResId(arrayOf(currentMainResId))
        _mainImageResId.postValue(newRes)
    }

    fun navigateToNextFragment(args: Bundle, extras: FragmentNavigator.Extras) {
        navigate(R.id.action_second_fragment, args, extras)
    }
}