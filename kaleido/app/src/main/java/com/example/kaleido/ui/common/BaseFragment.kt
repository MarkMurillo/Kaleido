package com.example.kaleido.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.kaleido.R
import com.example.kaleido.utils.NavigationCommand

/**
 * This base fragment will handle the basic navigation
 * options available
 */
abstract class BaseFragment: Fragment() {
    abstract fun getViewModel(): BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation()
    }

    private fun observeNavigation() {
        getViewModel().navigationCommand.observe(viewLifecycleOwner
        ) {
            val navController = NavHostFragment.findNavController(this@BaseFragment)

            when (it) {
                is NavigationCommand.ToRoot -> {
                    navController.navigate(R.id.fragment_main, it.args, null, it.extras)
                }
                is NavigationCommand.To -> {
                    navController.navigate(it.direction, it.args, null, it.extras)
                }
                is NavigationCommand.Back -> {
                    // If our backstack is empty (navigateUp() is false), just finish our activity.
                    // This is the same behaviour when pressing the system
                    // back button.
                    if(!navController.navigateUp()) activity?.finish()
                }
                else -> {
                    // Ignore.
                }
            }
        }
    }

    protected fun isFirstFragment(): Boolean {
        return parentFragmentManager.backStackEntryCount == 0
    }
}