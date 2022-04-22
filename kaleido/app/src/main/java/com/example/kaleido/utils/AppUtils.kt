package com.example.kaleido.utils

import com.example.kaleido.R
import kotlin.random.Random

object AppUtils {
    private val seed = Random(System.currentTimeMillis())

    private val resArray = arrayOf(
        R.raw.abstract_blue_and_yellow,
        R.raw.abstract_shapes_loader,
        R.raw.abstraction,
        R.raw.best_heart_button,
        R.raw.kaleidoscope,
        R.raw.kaleidoscope_loading_animation,
        R.raw.multiple_spinning_dotted_rings_loading,
        R.raw.office_365_automation_abstract_animation
    )

    fun getRandomLottieResId(currentRes: Array<Int> = emptyArray()): Int {
        val resPool = resArray.toList().minus(currentRes.toList())

        // Make sure they are different.
        return resPool[seed.nextInt(resPool.size)]
    }
}