package com.example.kaleido.utils

import com.example.kaleido.R
import kotlin.random.Random

object AppUtils {
    private val seed = Random(System.currentTimeMillis())

    private var counter = 0

    private val resArray = arrayOf(
        R.raw.kaleidoscope,
        R.raw.best_heart_button,
        R.raw.abstract_blue_and_yellow,
        R.raw.office_365_automation_abstract_animation,
        R.raw.kaleidoscope_loading_animation,
        R.raw.abstract_shapes_loader,
        R.raw.abstraction,
        R.raw.multiple_spinning_dotted_rings_loading,
    )

    fun getNextLottieAnimation(): Int {
        // Make sure they are different.
        counter++
        if(counter >= resArray.size) counter = 0
        return resArray[counter]
    }
}