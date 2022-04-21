package com.example.kaleido.ui.fragments.second

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
@HiltViewModel
class SecondViewModel @Inject constructor(
    @Named("Sentence")
    val appName: String
) : BaseViewModel() {

    private val _backgroundImageResId = MutableLiveData<Int>()
    val backgroundImageResId: LiveData<Int>
    get() = _backgroundImageResId

    private val _mainImageResId = MutableLiveData<Int>()
    val mainImageResId: LiveData<Int>
    get() = _mainImageResId

    var currentMainResId = R.raw.kaleidoscope

    fun refreshMainImage() {
//        val newRes = AppUtils.getRandomLottieResId(arrayOf(currentMainResId))
//        currentMainResId = newRes
        _mainImageResId.postValue(currentMainResId)
    }

    fun navigateToNextFragment(args: Bundle, extras: FragmentNavigator.Extras) {
        navigate(R.id.action_first_fragment, args, extras)
    }
}