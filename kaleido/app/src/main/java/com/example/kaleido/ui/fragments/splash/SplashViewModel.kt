package com.example.kaleido.ui.fragments.splash

import com.example.kaleido.data.AppData
import com.example.kaleido.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    appData: AppData
): BaseViewModel() {

}