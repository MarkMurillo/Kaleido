package com.example.kaleido.utils

import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator

sealed class NavigationCommand {
    var args: Bundle? = null
    var extras: FragmentNavigator.Extras? = null

    data class To(val direction: Int) : NavigationCommand()
    object Back : NavigationCommand()
    object ToRoot : NavigationCommand()
}