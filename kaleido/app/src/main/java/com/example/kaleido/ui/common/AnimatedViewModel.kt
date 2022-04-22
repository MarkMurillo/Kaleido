package com.example.kaleido.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.FragmentNavigator
import com.example.kaleido.utils.AppUtils

abstract class AnimatedViewModel: BaseViewModel() {
    private val _mainImageResId = MutableLiveData<Int>()
    val mainImageResId: LiveData<Int>
        get() = _mainImageResId

    var currentMainResId = 0

    fun refreshMainImage() {
        val newRes = AppUtils.getNextLottieAnimation()
        currentMainResId = newRes
        _mainImageResId.postValue(newRes)
    }

    abstract fun navigateToNextFragment(extras: FragmentNavigator.Extras)
}